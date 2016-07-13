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

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.verinice.model.Account;
import org.verinice.model.Velement;
import org.verinice.persistence.entities.CnATreeElement;
import org.verinice.persistence.entities.ElementConverter;
import org.verinice.persistence.entities.Entity;
import org.verinice.persistence.entities.Property;
import org.verinice.persistence.entities.PropertyList;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@Service
public class VeriniceAccountDAO extends VeriniceDAO {

    private static final Logger LOG = LoggerFactory.getLogger(VeriniceAccountDAO.class);
    private static final String TEST_ACC_ACTIVE = "org.verinice.testAccountActive";
    private static final String TEST_ACCOUNT_ACTIVE = "true";
    private static final String TEST_USERNAME = "test";
    private static final String TEST_PASSWORD = "test";

    /**
     * 
     * @param loginName
     * @return - An {@link Account} to the given Username or null if there is no
     *         account with this loginName
     */
    public Account findAccount(String loginName) {

        String testAccountActive = environment.getProperty(TEST_ACC_ACTIVE);
        LOG.debug("test account active:" + testAccountActive);
        if (TEST_ACCOUNT_ACTIVE.equals(testAccountActive) && loginName.equals(TEST_USERNAME)) {
            return new Account(TEST_USERNAME, TEST_PASSWORD);
        }
        CriteriaQuery<Entity> query = getCriteriaBuilder().createQuery(Entity.class);
        Root<Entity> rootelement = query.from(Entity.class);
        query.select(rootelement);
        Join<PropertyList, Entity> propertyListJoin = rootelement.join("propertyLists",
                JoinType.LEFT);
        Join<PropertyList, Property> propertyJoin = propertyListJoin.join("properties",
                JoinType.LEFT);

        List<Predicate> conditions = new ArrayList<>();
        conditions.add(getCriteriaBuilder().like(propertyJoin.get("propertytype"),
                "configuration_benutzername"));
        conditions.add(getCriteriaBuilder().like(propertyJoin.get("propertyvalue"), loginName));
        query.where(conditions.toArray(new Predicate[conditions.size()]));
        TypedQuery<Entity> typedQuery = entityManager.createQuery(query);

        try {
            Entity dbEntity = typedQuery.getSingleResult();
            CnATreeElement cnATreeElement = new CnATreeElement();
            cnATreeElement.setEntity(dbEntity);

            Velement v = ElementConverter
                    .elementForEntity(cnATreeElement);

            ArrayList<String> passwordProperty = new ArrayList<>(
                    v.getProperties().get("configuration_passwort"));
            return new Account(loginName, passwordProperty.get(0));
        } catch (NoResultException e) {
            LOG.debug("user " + loginName + " not found");
            return null;
        }

    }

}