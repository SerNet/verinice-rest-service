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
 *     Ruth Motza <rm[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package org.verinice.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.verinice.persistence.entities.CnATreeElement;
import org.verinice.persistence.entities.Entity;
import org.verinice.persistence.entities.Property;
import org.verinice.persistence.entities.PropertyList;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@Service
public class VeriniceElementDAO extends VeriniceDAO {


    private static final Logger LOG = LoggerFactory.getLogger(VeriniceElementDAO.class);

    @Autowired
    ElementRepository elementRepository;

    private static final String PROPERTY_MAX_SIZE = "org.verinice.rest.jpa.maxsize";
    private static final String PROPERTY_DEFAULT_SIZE = "org.verinice.rest.jpa.defaultsize";
    private static final String PROPERTY_MIN_SIZE = "org.verinice.rest.jpa.minsize";

    private static final int BACKUP_SIZE_MAX = 1000;
    private static final int BACKUP_SIZE_DEFAULT = 500;
    private static final int BACKUP_SIZE_MIN = 1;

    private int sizeMax = -1;
    private int sizeDefault = -1;
    private int sizeMin = -1;


    public CnATreeElement findByUuid(String uuid) {
        return elementRepository.findByUuid(uuid);
    }

    public List<CnATreeElement> fintByCriteria(Integer firstResult, Integer size, String key,
            String value, Integer scopeId) {

        CriteriaQuery<CnATreeElement> query = getCriteriaBuilder()
                .createQuery(CnATreeElement.class);
        Root<CnATreeElement> rootelement = query.from(CnATreeElement.class);
        query.select(rootelement);
        Join<CnATreeElement, Entity> entityJoin = rootelement.join("entity", JoinType.LEFT);
        Join<PropertyList, Entity> propertyListJoin = entityJoin.join("propertyLists",
                JoinType.LEFT);
        Join<PropertyList, Property> propertyJoin = propertyListJoin.join("properties",
                JoinType.LEFT);

        List<Predicate> conditions = new ArrayList<>();
        if (key != null) {
            conditions.add(getCriteriaBuilder().like(propertyJoin.get("propertytype"), key));
        }
        if (value != null) {
            conditions.add(getCriteriaBuilder().like(propertyJoin.get("propertyvalue"), value));
        }
        if (scopeId != null) {
            conditions.add(getCriteriaBuilder().equal(rootelement.get("scopeId"), scopeId));
        }
        query.where(conditions.toArray(new Predicate[conditions.size()]));
        TypedQuery<CnATreeElement> typedQuery = entityManager.createQuery(query);
        int firstIndex = firstResult == null || firstResult.intValue() < 0 ? 0 : firstResult;
        typedQuery.setFirstResult(firstIndex);
        int requestLimit = getLimit(size);
        LOG.debug("resultset-maxSize:\t" + requestLimit);
        typedQuery.setMaxResults(requestLimit);

        List<CnATreeElement> dbElements = typedQuery.getResultList();
        LOG.debug("resultset-size:\t" + dbElements.size());
        return dbElements;
    }

    private int getLimit(Integer limit) {
        initBorders();
        if (limit == null) {
            LOG.info("no size entered --> default size used [" + sizeDefault + "]");
            return sizeDefault;
        } else if (limit <= sizeMin) {
            LOG.warn("Entered size [" + limit + "] is smaller than minSize [" + sizeMin + "]");
            return sizeMin;
        } else if (limit >= sizeMax) {
            LOG.warn("Entered size [" + limit + "] is higher than maxSize [" + sizeMax + "]");
            return sizeMax;
        }
        return limit.intValue();
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
        LOG.debug("env_variable[" + property + "]=" + propertyValue);
        if (propertyValue == null) {
            return null;
        }
        try {
            int parseInt = Integer.parseInt(propertyValue);
            return new Integer(parseInt);
        } catch (NumberFormatException e) {
            LOG.error("property " + property + " not of type integer");
            return null;
        }
    }

}
