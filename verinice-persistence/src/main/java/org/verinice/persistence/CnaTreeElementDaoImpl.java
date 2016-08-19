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

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.verinice.model.Account;
import org.verinice.persistence.entities.CnaTreeElement;
import org.verinice.persistence.entities.Entity;
import org.verinice.persistence.entities.Property;
import org.verinice.persistence.entities.PropertyList;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Data access object (DAO) implementation for entity CnaTreeElement.
 * This DAO uses JPA API to interact with the database.
 *
 * @author Ruth Motza {@literal <rm[at]sernet[dot]de>}
 */
@Service
@EnableWebSecurity
public class CnaTreeElementDaoImpl extends Dao implements CnaTreeElementDao {

    private static final Logger logger = LoggerFactory.getLogger(CnaTreeElementDaoImpl.class);

    @Autowired
    CnaTreeElementRepository elementRepository;

    @Override
    public CnaTreeElement findByUuid(String uuid) {
        enableAccessControlFilters();
        return elementRepository.findByUuid(uuid);
    }
    
     @Override
    public CnaTreeElement findBySourceIdAndExtId(String sourceId, String extId) {
        enableAccessControlFilters();
        return elementRepository.findBySourceIdAndExtId(sourceId, extId);
    }

    @Override
    public List<CnaTreeElement> findByScopeKeyValue(Integer scopeId, String key, String value, Integer size, Integer firstResult) {
        enableAccessControlFilters();

        TypedQuery<CnaTreeElement> query = createQueryForScopeKeyValue(scopeId, key, value);
        configureResultSize(query, size, firstResult);

        List<CnaTreeElement> dbElements = query.getResultList();
        logger.debug("Result size: " + dbElements.size());

        return dbElements;
    }
    
    private TypedQuery<CnaTreeElement> createQueryForScopeKeyValue(Integer scopeId, String key, String value) {
        CriteriaQuery<CnaTreeElement> query = getCriteriaBuilder().createQuery(
                CnaTreeElement.class);
        Root<CnaTreeElement> rootelement = query.from(CnaTreeElement.class);
        query.select(rootelement);
        
        Join<CnaTreeElement, Entity> entityJoin = rootelement.join("entity", JoinType.LEFT);
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
        
        query.distinct(true);
        return entityManager.createQuery(query);
    }

}
