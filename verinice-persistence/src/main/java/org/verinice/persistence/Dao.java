/*******************************************************************************
 * Copyright (c) 2016 Ruth Motza.
 *
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,    
 * but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Ruth Motza <rm[at]sernet[dot]de> - Initial API and implementation
 *     Daniel Murygin <dm[at]sernet[dot]de> - Result size configuration, refactoring
 ******************************************************************************/
package org.verinice.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.verinice.model.Account;

/**
 * Base class for data access objects (DAO). This DAO uses JPA API to interact 
 * with the database. Extend this class to write a DAO for a database entity.
 * 
 * @author Ruth Motza <rm[at]sernet[dot]de>
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
@Service
public abstract class Dao {

    private static final Logger logger = LoggerFactory.getLogger(Dao.class);
    
    private static final String PROPERTY_MAX_SIZE = "org.verinice.rest.jpa.maxsize";
    private static final String PROPERTY_DEFAULT_SIZE = "org.verinice.rest.jpa.defaultsize";
    private static final String PROPERTY_MIN_SIZE = "org.verinice.rest.jpa.minsize";

    private static final int BACKUP_SIZE_MAX = 1000;
    private static final int BACKUP_SIZE_DEFAULT = 500;
    private static final int BACKUP_SIZE_MIN = 1;

    private int sizeMax = -1;
    private int sizeDefault = -1;
    private int sizeMin = -1;
    
    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected Environment environment;

    private CriteriaBuilder cb = null;

    protected CriteriaBuilder getCriteriaBuilder(){
        if(cb == null){
            cb = entityManager.getCriteriaBuilder();
        }
        return cb;
    }
    
    protected void enableAccessControlFilters() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Account account = (Account) securityContext.getAuthentication().getPrincipal();

        Session session = entityManager.unwrap(Session.class);

        if (account.isScoped()) {
            session.enableFilter("scope").setParameter("scopeId", account.getScopeId());
        }

        if (!account.isAdmin()) {
            session.enableFilter("userReadAccess").setParameterList("accountGroups",
                    account.getAccountGroups());
        }
    }
    
    /**
     * Configures the query and sets the maximum number and the first element 
     * which are returned from the result.
     * 
     * @param query The JPA query
     * @param size The maximum number of elements which are returned from the
     *    result of the query.
     * @param firstResult The index of the first element which is returned from 
     *    the result of the query
     */
    protected void configureResultSize(TypedQuery<?> query, Integer size, Integer firstResult) {
        int firstIndex;
        if (firstResult == null || firstResult < 0) {
            firstIndex = 0;
        } else {
            firstIndex = firstResult;
        }
        query.setFirstResult(firstIndex);
        int limit = getLimit(size);
        logger.debug("Result limit: " + limit);
        query.setMaxResults(limit);
    }

    /**
     * Returns the given limit if its higher or equal than the minimum limit
     * and lower or equal than the maximum limit. If the limit is lower than
     * the minimum limit the minimum limit is returned. If it is higher than the
     * maximum limit the maximum limit is returned.
     * 
     * @param limit The limit of the result size
     * @return The given limit if its higher or equal than the minimum limit
     *     and lower or equal than the maximum limit
     */
    private int getLimit(Integer limit) {
        initBorders();
        if (limit == null) {
            logger.info("No size found, default size is used: " + sizeDefault);
            return sizeDefault;
        } else if (limit <= sizeMin) {
            logger.warn("Size: " + limit + " is too small. Min. size is used: " + sizeMin);
            return sizeMin;
        } else if (limit >= sizeMax) {
            logger.warn("Size: " + limit + " is too high. Max. size is used:" + sizeMax);
            return sizeMax;
        }
        return limit;
    }

    private void initBorders() {
        if (sizeDefault == -1) {
            Integer def = getPropertyAsInteger(PROPERTY_DEFAULT_SIZE);
            sizeDefault = def == null ? BACKUP_SIZE_DEFAULT : def;
        }
        if (sizeMin == -1) {
            Integer min = getPropertyAsInteger(PROPERTY_MIN_SIZE);
            sizeMin = min == null ? BACKUP_SIZE_MIN : min;
        }
        if (sizeMax == -1) {
            Integer max = getPropertyAsInteger(PROPERTY_MAX_SIZE);
            sizeMax = max == null ? BACKUP_SIZE_MAX : max;
        }
    }
    
    private Integer getPropertyAsInteger(String property) {
        String propertyValue = environment.getProperty(property);
        logger.debug("env_variable[" + property + "]=" + propertyValue);
        if (propertyValue == null) {
            return null;
        }
        try {
            return Integer.parseInt(propertyValue);
        } catch (NumberFormatException ex) {
            logger.error("property " + property + " not of type integer");
            return null;
        }
    }
}
