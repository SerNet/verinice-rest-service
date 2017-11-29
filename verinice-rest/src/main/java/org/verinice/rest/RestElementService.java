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
 *     Alexander Ben Nasrallah <an@sernet.de> - contributor
 ******************************************************************************/
package org.verinice.rest;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.verinice.interfaces.ElementService;
import org.verinice.model.Velement;

/**
 * REST implementation of {@link ElementService}.
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 * @author Alexander Ben Nasrallah <an{a}sernet{dot}de>
 */
@RestController
public class RestElementService {

    @Autowired
    ElementService elementService;

    /**
     * This mapping matches requests by UUID, i.e. string of
     * - a block of 8 HEX chars followed by
     * - 3 blocks of 4 HEX chars followed by
     * - a block of 12 HEX chars.
     * This is the normalized UUID representation.
     *
     * This is the only API which uses the UUID of an element. Every other API
     * requires the database id which can be obtained by this API.
     */
    @RequestMapping("/element/{uuid:[a-fA-F\\d]{8}(?:-[a-fA-F\\d]{4}){3}-[a-fA-F\\d]{12}}")
    public Velement loadElement(@PathVariable("uuid")  String uuid) {
        return elementService.loadElement(uuid);
    }

    /**
     * @see org.verinice.interfaces.ElementService#loadElement(
     *  java.lang.Integer)
     */
    @RequestMapping("/element/{dbid:\\d+}")
    public Velement loadElement(@PathVariable("dbid") Long dbid) {
        return elementService.loadElement(dbid);
    }

    /**
     * @see org.verinice.interfaces.ElementService#loadElement(
     *  java.lang.String, java.lang.String)
     */
    @RequestMapping(value = "/elements/source-id/{sourceId}/ext-id/{extId}")
    public Velement loadElement(@PathVariable("sourceId") String sourceId,
            @PathVariable("extId") String extId) {
        return elementService.loadElement(sourceId, extId);
    }

    /**
     * @see org.verinice.interfaces.ElementService#loadElements(
     *  java.lang.Integer, java.lang.Integer, java.lang.String,
     *  java.lang.String)
     */
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
    @RequestMapping(value = "/scope/{scopeId}/elements")
    public Set<Velement> loadElementsOfScope(
            @PathVariable(value = "scopeId") Integer scopeId,
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String value,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer firstResult) {
        return elementService.loadElementsOfScope(scopeId, key, value, size, firstResult);
    }

    @RequestMapping(value = "/element/{parentId:\\d+}/children")
    public Set<Velement> loadChildren(
            @PathVariable(value = "parentId") Long parentId,
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String value,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer firstResult) {
        return elementService.loadChildren(parentId, key, value, size, firstResult);
    }

    @RequestMapping(value = "/elements", method = RequestMethod.POST)
    public void insertOrUpdateElement(@RequestBody Velement element, HttpServletRequest request,
            HttpServletResponse response) {
        String requestedHostAddress = request.getRequestURL().toString()
                .replace(request.getRequestURI(), "");
        long savedDbid = elementService.insertOrUpdateElement(element);
        if (element.getDbid() != savedDbid) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        }

        String savedElementLocation = String.format("%s/element/%d", requestedHostAddress, element.getDbid());
        response.setHeader("Location", savedElementLocation);
    }
}
