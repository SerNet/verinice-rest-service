/*
 * Copyright (c) 2016 Daniel Murygin.
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
 *  Daniel Murygin - initial API and implementation
 */
package org.verinice.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.verinice.interfaces.ElementService;
import org.verinice.model.Velement;
import org.verinice.persistence.ElementRepository;
import org.verinice.persistence.entities.CnATreeElement;
import org.verinice.persistence.entities.Entity;
import org.verinice.persistence.entities.Property;
import org.verinice.persistence.entities.PropertyList;

/**
 *
 * @author Daniel Murygin
 */
@Service
public class ElementServiceImpl implements ElementService {

    private static final int LIMIT_MAX = 1000;
    private static final int LIMIT_DEFAULT = 500;
    private static final int LIMIT_MIN = 1;

    private static final Logger LOG = LoggerFactory.getLogger(ElementServiceImpl.class);

    @Autowired
    ElementRepository elementRepository;
    
    @PersistenceContext
    EntityManager em;

    @Override
    public Velement getElement(String uuid) {
        CnATreeElement entityElement = elementRepository.findByUuid(uuid);
        return ElementConverter.elementForEntity(entityElement);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.verinice.interfaces.ElementService#getAllElements()
     */
    @Override
    public Set<Velement> getAllElements(Integer limit, String propertyType,
            String propertyValue, Integer scopeId) {


        LOG.debug("variables:\n\tlimit: " + limit + "\n\tpropertytype: " + propertyType
                + "\n\tpropertyvalue: " + propertyValue + "\n\tscopeId: " + scopeId);
        int requestLimit;
        if (limit == null){
            requestLimit = LIMIT_DEFAULT;
        }else if(limit <= LIMIT_MIN){
            requestLimit = LIMIT_MIN;
        } else if (limit >= LIMIT_MAX) {
            requestLimit = LIMIT_MAX;
        } else {
            requestLimit = limit.intValue();
        }
        LOG.debug("resultset-limit: " + requestLimit);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CnATreeElement> q = cb.createQuery(CnATreeElement.class);
        Root<CnATreeElement> c = q.from(CnATreeElement.class);
        q.select(c);
        ParameterExpression<String> type = cb.parameter(String.class);
        ParameterExpression<String> value = cb.parameter(String.class);
        Join<CnATreeElement, Entity> join = c.join("entity", JoinType.LEFT);
        Join<PropertyList, Entity> join2 = join.join("propertyLists", JoinType.LEFT);
        Join<PropertyList, Property> join3 = join2.join("properties", JoinType.LEFT);

        List<Predicate> conditions = new ArrayList<>();
        if (propertyType != null) {
            conditions.add(cb.like(join3.get("propertytype"), type));
        }
        if (propertyValue != null) {
            conditions.add(cb.like(join3.get("propertyvalue"), value));
        }
        if (scopeId != null) {
            conditions.add(cb.equal(c.get("scopeId"), scopeId));
        }
        q.where(conditions.toArray(new Predicate[conditions.size()]));

        TypedQuery<CnATreeElement> query = em.createQuery(q);
        if (propertyType != null)
            query.setParameter(type, propertyType);
        if (propertyValue != null)
            query.setParameter(value, propertyValue);
        query.setMaxResults(requestLimit);
        List<CnATreeElement> dbElements = query.getResultList();

        LOG.debug("result size: " + dbElements.size());
        Set<Velement> elements = new HashSet<>();
        dbElements.forEach(element -> {
            Velement convertedElement = ElementConverter.elementForEntity(element);
            elements.add(convertedElement);
        });
        return elements;
    }

    
}
