/*******************************************************************************
 * Copyright (c) 2017 Alexander Ben Nasrallah.
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
 *     Alexander Ben Nasrallah <an@sernet.de> - initial API and implementation
 ******************************************************************************/
package org.verinice.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;
import org.verinice.persistence.entities.Entity;

@Service
@EnableWebSecurity
public class EntityDaoImp extends Dao implements EntityDao {

    private Logger logger = LoggerFactory.getLogger(EntityDaoImp.class);

    @Autowired
    private EntityRepository elementRepository;

    @Override
    public Entity findByElementId(long dbid) {
        enableAccessControlFilters();
        try {
            long entityId = selectEntityIdWithElement(dbid);
            return findByDbid(entityId);
        } catch (NullPointerException e) {
            logger.info("No entity for CnaTreeElement with id {} found", dbid);
            return null;
        }
    }

    @Override
    public Entity findByDbid(long dbid) {
        enableAccessControlFilters();
        return elementRepository.findById(dbid).get();
    }

    private long selectEntityIdWithElement(long elementDbid) {
        return entityManager.createQuery("SELECT c.entity.dbid FROM CnaTreeElement c WHERE c.dbid = :dbid", Long.class)
                .setParameter("dbid", elementDbid)
                .getSingleResult();
    }
}
