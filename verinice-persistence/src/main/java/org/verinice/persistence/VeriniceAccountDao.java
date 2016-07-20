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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.verinice.model.Account;
import org.verinice.model.Velement;
import org.verinice.persistence.entities.CnaTreeElement;
import org.verinice.persistence.entities.ElementConverter;
import org.verinice.persistence.entities.Entity;
import org.verinice.persistence.entities.Property;
import org.verinice.persistence.entities.PropertyList;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * TODO.
 *
 * @author Ruth Motza {@literal <rm[at]sernet[dot]de>}
 */
@Service
public class VeriniceAccountDao extends VeriniceDao {

    private static final Logger logger = LoggerFactory.getLogger(VeriniceAccountDao.class);

    /**
     * Returns an {@link Account} with the given login name.
     * 
     * @param loginName the login name for which the according {@link Account} should be returned
     * @return - the {@link Account} with the given user name or null if doesn't exist.
     */
    public Account findAccount(String loginName) {

        TypedQuery<Entity> query = buildQuery(loginName);

        try {
            Entity entity = query.getSingleResult();
            CnaTreeElement cnaTreeElement = new CnaTreeElement();
            cnaTreeElement.setEntity(entity);
            Velement element = ElementConverter.elementForEntity(cnaTreeElement);
            ArrayList<String> passwordProperty = new ArrayList<>(
                    element.getProperties().get("configuration_passwort"));

            return new Account(loginName, passwordProperty.get(0));

        } catch (NoResultException ex) {
            logger.debug("Login name '" + loginName + "' not found", ex);
        }

        return null;
    }

    private TypedQuery<Entity> buildQuery(String loginName) {

        CriteriaBuilder builder = getCriteriaBuilder();

        CriteriaQuery<Entity> query = builder.createQuery(Entity.class);
        Root<Entity> entity = query.from(Entity.class);
        Join<PropertyList, Entity> propertyListJoin = entity.join("propertyLists");
        Join<PropertyList, Property> propertiesJoin = propertyListJoin.join("properties");

        List<Predicate> conditions = new ArrayList<>();
        Path<String> propertyTypePath = propertiesJoin.get("propertytype");
        conditions.add(builder.like(propertyTypePath, "configuration_benutzername"));
        Path<String> propertyvaluePath = propertiesJoin.get("propertyvalue");
        conditions.add(builder.like(propertyvaluePath, loginName));
        query.where(conditions.toArray(new Predicate[conditions.size()]));

        return entityManager.createQuery(query);
    }
}
