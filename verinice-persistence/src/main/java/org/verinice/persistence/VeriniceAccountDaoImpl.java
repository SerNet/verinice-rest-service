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

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
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

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
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

    private static final String SCOPEID_FOR_ACCOUNT_JPQL = "scopeid-for-account.sql";

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

        String fileName = SCOPEID_FOR_ACCOUNT_JPQL;

        String jpql = null;
        try {
            jpql = Resources.toString(Resources.getResource(fileName), Charsets.UTF_8);
        } catch (IOException ex) {
            LOG.error("Error reading JPQL query from file: " + fileName, ex);
        }

        Query query = entityManager.createQuery(jpql).setParameter("loginName", loginName);

        int scopeId = -1;
        try {
            scopeId = (Integer) query.getSingleResult();
        } catch (Exception ex) {
            LOG.error("Could not retrieve scope id for user: '" + loginName + "'", ex);
        }

        return scopeId;
    }
}
