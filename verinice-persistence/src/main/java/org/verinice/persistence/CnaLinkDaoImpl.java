/*******************************************************************************
 * Copyright (c) 2018 Alexander Ben Nasrallah
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
 ******************************************************************************/

package org.verinice.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;
import org.verinice.model.Vlink;
import org.verinice.persistence.entities.CnaLink;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Data access object (DAO) implementation for entity CnaLink. This DAO
 * uses JPA API to interact with the database.
 */
@Service
@EnableWebSecurity
public class CnaLinkDaoImpl extends Dao implements CnaLinkDao {

    private static final Logger log = LoggerFactory.getLogger(CnaLinkDaoImpl.class);

    @Autowired
    private CnaLinkRepository linkRepository;

    @Override
    public List<CnaLink> find(Map<String, String> queryParams) {
        log.debug("list links {}", queryParams);
        CriteriaQuery<CnaLink> query = getCriteriaBuilder().createQuery(CnaLink.class);
        Root<CnaLink> rootelement = query.from(CnaLink.class);
        query.select(rootelement);
        if (queryParams != null) {
            List<Predicate> conditions = collectConditions(queryParams, rootelement);
            query.where(conditions.toArray(new Predicate[conditions.size()]));
        }
        query.distinct(true);
        return entityManager.createQuery(query).getResultList();
    }

    private List<Predicate> collectConditions(Map<String, String> queryParams,
            Root<CnaLink> rootelement) {
        List<Predicate> conditions = new ArrayList<>();
        for (Map.Entry<String, String> queryParam : queryParams.entrySet()) {
            String fieldName = queryParam.getKey();
            log.debug("filter by {} == {}", fieldName, queryParam.getValue());
            try {
                Field field = Vlink.class.getDeclaredField(fieldName);
                switch (field.getType().getName()) {
                case "java.lang.String":
                    conditions.add(getCriteriaBuilder()
                            .like(rootelement.get(queryParam.getKey()), queryParam.getValue()));
                    break;
                case "int":
                case "java.lang.Integer":
                    conditions.add(getCriteriaBuilder()
                            .equal(rootelement.get(queryParam.getKey()), Integer.parseInt(queryParam.getValue())));
                    break;
                case "java.lang.Long":
                case "long":
                    conditions.add(getCriteriaBuilder()
                            .equal(rootelement.get(queryParam.getKey()), Long.parseLong(queryParam.getValue())));
                    break;
                default:
                    log.info("properties of class {} can't be used for filtered queries", field.getType().getName());
                }
            } catch (NoSuchFieldException e) {
                log.info(e.getMessage());
            }
        }
        return conditions;
    }

    @Override
    public CnaLink insert(CnaLink link) {
        log.debug("inserting link {}", link);
        return this.linkRepository.save(link);
    }
}
