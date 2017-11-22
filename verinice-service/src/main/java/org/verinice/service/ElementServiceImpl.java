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
import org.verinice.persistence.entities.CnaTreeElement;
import org.verinice.persistence.entities.ElementConverter;

import java.util.List;
import java.util.Set;
import org.verinice.persistence.CnaTreeElementDao;

/**
 * Implementation of the element service which uses a {@link CnaTreeElementDao}.
 *
 * @author Daniel Murygin
 */
@Service
public class ElementServiceImpl implements ElementService {

    private static final Logger LOG = LoggerFactory.getLogger(ElementServiceImpl.class);

    @Autowired
    CnaTreeElementDao dao;

    @Override
    public Velement loadElement(String uuid) {
        if(uuid==null) {
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
        if(sourceId==null) {
            throw new IllegalArgumentException("Source id must not be null");
        }
        if(extId==null) {
            throw new IllegalArgumentException("Ext id must not be null");
        }
        CnaTreeElement entityElement = dao.findBySourceIdAndExtId(sourceId, extId);
        return ElementConverter.elementForEntity(entityElement);
    }

    @Override
    public Set<Velement> loadElements(String key, String value, Integer size, Integer firstResult) {
        LOG.debug("Key: " + key + ", value: " + value + ", size: " + size + ", first result: " + firstResult);
        List<CnaTreeElement> dbElements = dao.findByScopeKeyValue(null, key, value, size, firstResult);
        return ElementConverter.elementsForEntitys(dbElements);
    }

    @Override
    public Set<Velement> loadElementsOfScope(Integer scopeId, String key, String value, Integer size, Integer firstResult) {
        LOG.debug("Scope id: " + scopeId + "Key: " + key + ", value: " + value + ", size: " + size + ", first result: " + firstResult);
        List<CnaTreeElement> dbElements = dao.findByScopeKeyValue(scopeId, key, 
                value, size, firstResult);
        return ElementConverter.elementsForEntitys(dbElements);
    }
}
