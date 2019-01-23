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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.verinice.model.Account;
import org.verinice.persistence.entities.CnaTreeElement;
import org.verinice.persistence.entities.Entity;
import org.verinice.persistence.entities.Permission;
import org.verinice.persistence.entities.Property;
import org.verinice.persistence.entities.PropertyList;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data access object (DAO) implementation for entity CnaTreeElement. This DAO
 * uses JPA API to interact with the database.
 *
 * @author Ruth Motza {@literal <rm[at]sernet[dot]de>}
 * @author Alexander Ben Nasrallah {@literal <an@sernet.de>}
 */
@Service
@EnableWebSecurity
public class CnaTreeElementDaoImpl extends Dao implements CnaTreeElementDao {

    @Autowired
    private CnaTreeElementRepository elementRepository;

    @Override
    public CnaTreeElement findByUuid(String uuid) {
        enableAccessControlFilters();
        return elementRepository.findByUuid(uuid);
    }

    @Override
    public CnaTreeElement findByDbid(Long dbid) {
        enableAccessControlFilters();
        return elementRepository.findOne(dbid);
    }

     @Override
    public CnaTreeElement findBySourceIdAndExtId(String sourceId, String extId) {
        enableAccessControlFilters();
        return elementRepository.findBySourceIdAndExtId(sourceId, extId);
    }

    @Override
    public List<CnaTreeElement> findByScopeKeyValue(Integer scopeId, String key, String value, Integer size, Integer firstResult) {
        enableAccessControlFilters();
        Criteria criteria = new Criteria().setScopeId(scopeId).setKey(key).setValue(value);
        TypedQuery<CnaTreeElement> query = createQueryWithCriteria(criteria);
        configureResultSize(query, size, firstResult);

        List<CnaTreeElement> dbElements = query.getResultList();
        return dbElements;
    }

    @Override
    public List<CnaTreeElement> findByParentId(Long parentId, String key, String value,
            Integer size, Integer firstResult) {
        enableAccessControlFilters();
        Criteria criteria = new Criteria().setParentId(parentId).setKey(key).setValue(value);
        TypedQuery<CnaTreeElement> query = createQueryWithCriteria(criteria);
        configureResultSize(query, size, firstResult);
        return query.getResultList();
    }

    private TypedQuery<CnaTreeElement> createQueryWithCriteria(Criteria criteria) {
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
        if (criteria.getKey() != null) {
            conditions.add(getCriteriaBuilder().like(propertyJoin.get("propertytype"), criteria.getKey()));
        }
        if (criteria.getValue() != null) {
            conditions.add(getCriteriaBuilder().like(propertyJoin.get("propertyvalue"), criteria.getValue()));
        }
        if (criteria.getParentId() != null) {
            conditions.add(getCriteriaBuilder().equal(rootelement.get("parentId"), criteria.getParentId()));
        }
        if (criteria.getScopeId() != null) {
            conditions.add(getCriteriaBuilder().equal(rootelement.get("scopeId"), criteria.getScopeId()));
        }
        query.where(conditions.toArray(new Predicate[conditions.size()]));

        query.distinct(true);
        return entityManager.createQuery(query);
    }

    @Override
    public CnaTreeElement insertOrUpdate(CnaTreeElement element) {
        if (element.getDbid() == 0) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Account account = (Account) securityContext.getAuthentication().getPrincipal();

            Permission defaultPermission = new Permission();
            defaultPermission.setElement(element);
            defaultPermission.setWrite(true);
            defaultPermission.setRead(true);
            defaultPermission.setRole(account.getLogin());
            element.setPermissions(Collections.singleton(defaultPermission));
        }
        return elementRepository.save(element);
    }

    public class Criteria {
        private Integer scopeId;
        private Long parentId;
        private String key;
        private String value;

        public Criteria setParentId(Long parentId) {
            this.parentId = parentId;
            return this;
        }
        public Criteria setKey(String key) {
            this.key = key;
            return this;
        }
        public Criteria setValue(String value) {
            this.value = value;
            return this;
        }
        public Criteria setScopeId(Integer scopeId) {
            this.scopeId = scopeId;
            return this;
        }
        public Integer getScopeId() {
            return scopeId;
        }
        public Long getParentId() {
            return parentId;
        }
        public String getKey() {
            return key;
        }
        public String getValue() {
            return value;
        }
    }
}
