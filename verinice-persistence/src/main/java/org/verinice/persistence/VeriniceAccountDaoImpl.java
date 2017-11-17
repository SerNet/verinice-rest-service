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
import org.verinice.persistence.entities.*;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object (DAO) implementation for Accounts.
 * This DAO uses JPA API to interact with the database.
 *
 * Since there is no special entity for an account the "virtual" entity account
 * is created in this class after loading account data from the entity
 * {@link Entity} and its properties.
 * 
 * @author Ruth Motza {@literal <rm[at]sernet[dot]de>}
 */
@Service
public class VeriniceAccountDaoImpl extends Dao implements VeriniceAccountDao {

    private static final Logger LOG = LoggerFactory.getLogger(VeriniceAccountDaoImpl.class);

    @Override
    public Account findByLoginName(String loginName) {
        Entity entity = findEntityByLoginName(loginName);
        if(entity==null) {
            return null;
        }
        return convertToAccount(entity);
    }

    private Account convertToAccount(Entity entity) {
        CnaTreeElement cnaTreeElement = new CnaTreeElement();
        cnaTreeElement.setEntity(entity);
        Velement element = ElementConverter.elementForEntity(cnaTreeElement);
        String loginName = element.getProperties().get("configuration_benutzername").get(0);
        String password = element.getProperties().get("configuration_passwort").get(0);
        String adminProperty = element.getProperties().get("configuration_isadmin").get(0);
        boolean admin = false;
        if ("configuration_isadmin_yes".equals(adminProperty)) {
            admin = true;
        }
        String scopedProperty = element.getProperties().get("configuration_scope").get(0);
        boolean scoped = false;
        if ("configuration_scoped_yes".equals(scopedProperty)) {
            scoped = true;
        }
        List<String> accountGroups = element.getProperties().get("configuration_rolle");
        // User level access control is accomplished by means of a virtual group
        // with the user's name.
        accountGroups.add(loginName);
        
        Account account = new Account(loginName, password);
        account.setScoped(scoped);
        account.setScopeId(getScopeIdForLoginName(loginName));
        account.setAdmin(admin);
        account.setAccountGroups(accountGroups);
        return account;
    }
    
    private Entity findEntityByLoginName(String loginName) {
        TypedQuery<Entity> propertiesQuery = buildQueryForProperties(loginName);
        if (LOG.isDebugEnabled()) {
            LOG.debug(propertiesQuery.toString());
        }
        Entity entity = null;
        try {
            entity = propertiesQuery.getSingleResult();
        } catch (NoResultException ex) {
            LOG.info("Account with login name: '" + loginName + "' not found");
            LOG.debug("Stacktrace: ", ex);
        }
        return entity;
    }

    private TypedQuery<Entity> buildQueryForProperties(String loginName) {

        CriteriaBuilder builder = getCriteriaBuilder();

        CriteriaQuery<Entity> query = builder.createQuery(Entity.class);
        Root<Entity> entity = query.from(Entity.class);
        Join<PropertyList, Entity> propertyListJoin = entity.join("propertyLists");
        Join<PropertyList, Property> propertiesJoin = propertyListJoin.join("properties");
        List<Predicate> conditions = new ArrayList<>();
        Path<String> propertytypePath = propertiesJoin.get("propertytype");
        conditions.add(builder.like(propertytypePath, "configuration_benutzername"));
        Path<String> propertyvaluePath = propertiesJoin.get("propertyvalue");
        conditions.add(builder.like(propertyvaluePath, loginName));
        query.where(conditions.toArray(new Predicate[conditions.size()]));

        return entityManager.createQuery(query);
    }

    private int getScopeIdForLoginName(String loginName) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Configuration> query = builder.createQuery(Configuration.class);

        Root<Configuration> rootelement = query.from(Configuration.class);

        query.select(rootelement);
        Join<CnaTreeElement, Entity> entityJoin = rootelement.join("entity", JoinType.LEFT);
        Join<PropertyList, Entity> propertyListJoin = entityJoin.join("propertyLists",
                JoinType.LEFT);
        Join<PropertyList, Property> propertyJoin = propertyListJoin.join("properties",
                JoinType.LEFT);

        List<Predicate> conditions = new ArrayList<>();
            conditions.add(getCriteriaBuilder().like(propertyJoin.get("propertytype"), "configuration_benutzername"));
            conditions.add(getCriteriaBuilder().like(propertyJoin.get("propertyvalue"), loginName));
        query.where(conditions.toArray(new Predicate[conditions.size()]));

        query.distinct(true);

        TypedQuery<Configuration> typedQuery = entityManager.createQuery(query);
        Configuration result = typedQuery.getSingleResult();
        return result.getCnaTreeElement().getScopeId();
    }
}
