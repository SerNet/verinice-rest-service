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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.verinice.interfaces.ElementService;
import org.verinice.model.Velement;
import org.verinice.persistence.CnaTreeElementDao;
import org.verinice.persistence.EntityDao;
import org.verinice.persistence.entities.CnaTreeElement;
import org.verinice.persistence.entities.ElementConverter;
import org.verinice.persistence.entities.Entity;

import java.util.List;
import java.util.Set;

/**
 * Implementation of the element service which uses a {@link CnaTreeElementDao}.
 *
 * @author Daniel Murygin
 */
@Service
public class ElementServiceImpl implements ElementService {

    private static final Logger LOG = LoggerFactory.getLogger(ElementServiceImpl.class);

    @Autowired
    private CnaTreeElementDao dao;
    @Autowired
    private EntityDao entityDao;

    @Override
    public Velement loadElement(String uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("Uuid must not be null");
        }
        CnaTreeElement entityElement = dao.findByUuid(uuid);
        return ElementConverter.elementForEntity(entityElement);
    }

    @Override
    public Velement loadElement(Long dbid) {
        if (dbid == null) {
            throw new IllegalArgumentException("dbid must not be null");
        }
        CnaTreeElement entityElement = dao.findByDbid(dbid);
        return ElementConverter.elementForEntity(entityElement);
    }

    @Override
    public Velement loadElement(String sourceId, String extId) {
        if (sourceId == null) {
            throw new IllegalArgumentException("Source id must not be null");
        }
        if (extId == null) {
            throw new IllegalArgumentException("Ext id must not be null");
        }
        CnaTreeElement entityElement = dao.findBySourceIdAndExtId(sourceId, extId);
        return ElementConverter.elementForEntity(entityElement);
    }

    @Override
    public Set<Velement> loadElements(String key, String value, Integer size, Integer firstResult) {
        LOG.debug("Key: %s, value %s, size %d, first result %d", key, value, size, firstResult);
        List<CnaTreeElement> dbElements = dao.findByScopeKeyValue(null, key, value, size,
                firstResult);
        return ElementConverter.elementsForEntities(dbElements);
    }

    @Override
    public Set<Velement> loadElementsOfScope(Integer scopeId, String key, String value,
            Integer size, Integer firstResult) {
        LOG.debug("Scope id: %d, Key: %s, value %s, size %d, first result %d", key, value, size,
                firstResult);
        List<CnaTreeElement> dbElements = dao.findByScopeKeyValue(scopeId, key, value, size,
                firstResult);
        return ElementConverter.elementsForEntities(dbElements);
    }

    @Override
    public Set<Velement> loadChildren(Long parentId, String key, String value, Integer size,
            Integer firstResult) {
        LOG.debug("Parent dbid: %d, Key: %s, value %s, size %d, first result %d", parentId, key,
                value, size, firstResult);
        List<CnaTreeElement> dbElements = dao.findByParentId(parentId, key, value, size,
                firstResult);
        return ElementConverter.elementsForEntities(dbElements);
    }

    @Override
    public Velement insertOrUpdateElement(Velement element) {
        CnaTreeElement el;
        if (element.getDbid() == 0) {
            el = ElementConverter.elementToEntity(element);
        } else {
            Entity existingEntity = entityDao.findByElementId(element.getDbid());
            el = ElementConverter.elementToEntity(element, existingEntity);
        }
        CnaTreeElement persistedElement = dao.insertOrUpdate(el);
        return ElementConverter.elementForEntity(persistedElement);
    }
}
