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


import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.verinice.interfaces.ElementService;
import org.verinice.model.Velement;
import org.verinice.persistence.ElementRepository;
import org.verinice.persistence.entities.CnATreeElement;

/**
 *
 * @author Daniel Murygin
 */
@Service
public class ElementServiceImpl implements ElementService {

    private static final int LIMIT_MAX = 1000;
    private static final int LIMIT_DEFAULT = 500;
    private static final int LIMIT_MIN = 1;

    private static final Logger LOG = LoggerFactory.getLogger(ElementServiceImpl.class);

    @Autowired
    ElementRepository elementRepository;
    
    @Override
    public Velement getElement(String uuid) {
        CnATreeElement entityElement = elementRepository.findByUuid(uuid);
        return ElementConverter.elementForEntity(entityElement);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.verinice.interfaces.ElementService#getAllElements()
     */
    @Override
    public Set<Velement> getAllElements(Integer limit) {


        LOG.debug("limit: " + limit);
        int requestLimit;
        if (limit == null){
            requestLimit = LIMIT_DEFAULT;
        }else if(limit <= LIMIT_MIN){
            requestLimit = LIMIT_MIN;
        } else if (limit >= LIMIT_MAX) {
            requestLimit = LIMIT_MAX;
        } else {
            requestLimit = limit.intValue();
        }
        Pageable limitation = new PageRequest(0, requestLimit);
        Page<CnATreeElement> dbElements = elementRepository.findAll(limitation);
        Set<Velement> elements = new HashSet<>();
        if (dbElements == null) {
            LOG.error("db connection returns null");
        } else {
            dbElements.forEach(element -> {
                Velement convertedElement = ElementConverter.elementForEntity(element);
                elements.add(convertedElement);
            });
        }
        return elements;
    }
    
}
