/*******************************************************************************
 * Copyright (c) 2016 Daniel Murygin <dm{a}sernet{dot}de>.
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
 *     Daniel Murygin <dm{a}sernet{dot}de> - initial API and implementation
 ******************************************************************************/
package org.verinice.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.verinice.interfaces.ElementService;
import org.verinice.model.Velement;

/**
 * REST implementation of {@link ElementService}.
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@RestController
public class RestElementService implements ElementService {

    @Autowired
    ElementService elementService;

    /**
     * @see org.verinice.interfaces.ElementService#loadElement(
     *  java.lang.String)
     */
    @RequestMapping("/element/{uuid}")
    @Override
    public Velement loadElement(@PathVariable("uuid")  String uuid) {
        return elementService.loadElement(uuid);     
    }
    
    /**
     * @see org.verinice.interfaces.ElementService#loadElement(
     *  java.lang.String, java.lang.String)
     */
    @RequestMapping(value = "/elements/source-id/{sourceId}/ext-id/{extId}")
    @Override
    public Velement loadElement(@PathVariable("sourceId") String sourceId, 
            @PathVariable("extId") String extId) {
        return elementService.loadElement(sourceId, extId);
    }

    /**
     * @see org.verinice.interfaces.ElementService#loadElements(
     *  java.lang.Integer, java.lang.Integer, java.lang.String, 
     *  java.lang.String)
     */
    @Override
    @RequestMapping(value = "/elements")
    public Set<Velement> loadElements(
            @RequestParam(required = false) String key, 
            @RequestParam(required = false) String value, 
            @RequestParam(required = false) Integer size, 
            @RequestParam(required = false) Integer firstResult) {
        return elementService.loadElements(key, value, size, firstResult);
    }

    /**
     * @see org.verinice.interfaces.ElementService#loadElementsOfScope(
     *  java.lang.Integer, java.lang.Integer, java.lang.Integer, 
     *  java.lang.String, java.lang.String)
     */
    @Override
    @RequestMapping(value = "/scope/{scopeId}/elements")
    public Set<Velement> loadElementsOfScope(
            @PathVariable(value = "scopeId") Integer scopeId, 
            @RequestParam(required = false) String key, 
            @RequestParam(required = false) String value, 
            @RequestParam(required = false) Integer size, 
            @RequestParam(required = false) Integer firstResult) {
        return elementService.loadElementsOfScope(scopeId, key, value, size, firstResult);
    }
}
