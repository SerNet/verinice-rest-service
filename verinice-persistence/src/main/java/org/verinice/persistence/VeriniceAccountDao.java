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
import javax.persistence.Query;
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

        boolean scoped = isAccountScoped(loginName);
        int scopeId = getScopeIdForLoginName(loginName);

        TypedQuery<Entity> propertiesQuery = buildQueryForProperties(loginName);

        try {
            Entity entity = propertiesQuery.getSingleResult();
            CnaTreeElement cnaTreeElement = new CnaTreeElement();
            cnaTreeElement.setEntity(entity);
            Velement element = ElementConverter.elementForEntity(cnaTreeElement);

            String password = element.getProperties().get("configuration_passwort").get(0);

            List<String> accountGroups = element.getProperties().get("configuration_rolle");

            Account account = new Account(loginName, password);
            account.setScoped(scoped);
            account.setScopeId(scopeId);
            account.setAccountGroups(accountGroups);

            return account;

        } catch (NoResultException ex) {
            logger.debug("Login name '" + loginName + "' not found", ex);
        }

        return null;
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

        String jpql = "\n"
                + "SELECT cte.scopeId FROM CnaTreeElement cte \n"
                + "WHERE cte.dbid IN ( \n"
                + "  SELECT c.personId FROM Configuration c \n"
                + "  WHERE c.entityId IN ( \n"
                + "    SELECT e.dbid FROM Entity e \n"
                + "    WHERE e.dbid IN ( \n"
                + "      SELECT pl.typedlistId FROM PropertyList pl \n"
                + "      WHERE pl.dbid IN ( \n"
                + "        SELECT p.propertiesId FROM Property p \n"
                + "        WHERE p.propertytype = 'configuration_benutzername' \n"
                + "        AND p.propertyvalue = :login))))";

        Query query = entityManager.createQuery(jpql).setParameter("login", loginName);

        int scopeId = -1;
        try {
            scopeId = (Integer) query.getSingleResult();
        } catch (Exception ex) {
            logger.error("Could not retrieve scope id for user '" + loginName + "'", ex);
        }

        return scopeId;
    }

    private boolean isAccountScoped(String loginName) {

        String jpql = "\n"
                + "SELECT"
                + "  p1.propertyvalue \n"
                + "FROM Property p1 \n"
                + "WHERE p1.propertiesId IN ( \n"
                + "  SELECT \n"
                + "    pl1.dbid \n"
                + "  FROM PropertyList pl1 \n"
                + "  WHERE pl1.typedlistId IN ( \n"
                + "    SELECT \n"
                + "      pl2.typedlistId \n"
                + "    FROM PropertyList pl2 \n"
                + "    WHERE pl2.dbid IN ( \n"
                + "      SELECT \n"
                + "        p2.propertiesId \n"
                + "      FROM Property p2 \n"
                + "      WHERE p2.propertytype = 'configuration_benutzername' \n"
                + "      AND p2.propertyvalue = :loginName \n"
                + "    ) \n"
                + "  ) \n"
                + "  AND pl1.listIdx = 'configuration_scope' \n"
                + ")";

        Query query = entityManager.createQuery(jpql).setParameter("loginName", loginName);

        boolean scoped = true;
        try {
            if ("configuration_scope_no".equals(query.getSingleResult().toString())) {
                scoped = false;
            }
        } catch (Exception ex) {
            logger.error("Could not retrieve scope status for user '" + loginName + "'."
                    + "Treating account as if being scoped for security reasons.", ex);
        }

        return scoped;
    }
}
