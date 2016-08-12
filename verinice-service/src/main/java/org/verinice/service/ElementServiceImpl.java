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
 * TODO.
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
        CnaTreeElement entityElement = dao.findByUuid(uuid);
        return ElementConverter.elementForEntity(entityElement);
    }

    @Override
    public Set<Velement> loadElements(Integer firstResult, Integer limit, String key,
            String value) {

        LOG.debug("variables:\n\tfirst result: " + firstResult
                + "\n\tlimit: " + limit
                + "\n\tpropertytype: " + key
                + "\n\tpropertyvalue: " + value);

        List<CnaTreeElement> dbElements = dao.findByScopeKeyValue(null, key, value, limit, firstResult);

        return ElementConverter.elementsForEntitys(dbElements);
    }

    @Override
    public Set<Velement> loadElementsOfScope(Integer scopeId, Integer firstResult, Integer size,
            String key, String value) {

        LOG.debug("variables:\n\tfirst result: " + firstResult + "\n\tlimit: " + size
                + "\n\tpropertytype: " + key + "\n\tpropertyvalue: " + value);
        List<CnaTreeElement> dbElements = dao.findByScopeKeyValue(scopeId, key, value,
                size, firstResult);
        return ElementConverter.elementsForEntitys(dbElements);
    }
}
