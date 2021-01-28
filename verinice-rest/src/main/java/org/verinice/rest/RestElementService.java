/*******************************************************************************
 * Copyright (c) "201"6 Daniel Murygin <dm{a}sernet{dot}de>.
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.verinice.interfaces.ElementService;
import org.verinice.model.Velement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Set;

/**
 * REST implementation of {@link ElementService}.
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 * @author Alexander Ben Nasrallah <an@sernet.de>
 */
@RestController
public class RestElementService {

    @Autowired
    private ElementService elementService;

    /**
     * This mapping matches requests by UUID, i.e. string of
     * - a block of 8 HEX chars followed by
     * - 3 blocks of 4 HEX chars followed by
     * - a block of 12 HEX chars.
     * This is the normalized UUID representation.
     * <p>
     * This is the only API which uses the UUID of an element. Every other API
     * requires the database id which can be obtained by this API.
     */
    @GetMapping("/element/{uuid:[a-fA-F\\d]{8}(?:-[a-fA-F\\d]{4}){3}-[a-fA-F\\d]{12}}")
    @Operation(description = "load element by UUID")
    public Velement loadElement(@Parameter(description = "UUID of the requested element\n"
            + "\n"
            + "The regular expression matches a string of\n"
            + "* a block of 8 HEX chars followed by\n"
            + "* 3 blocks of 4 HEX chars followed by\n"
            + "* a block of 12 HEX chars.\n"
            + "\n"
            + "This is the normalized UUID representation.\n"
            + "\n"
            + "This is the only API which uses the UUID of an element. Every other API\n"
            + "requires the database id which can be obtained by this API.",
            example = "f35b982c-8ad4-4515-96ee-df5fdd4247b9",
            required = true) @PathVariable("uuid") String uuid) {
        return elementService.loadElement(uuid);
    }

    /**
     * @see org.verinice.interfaces.ElementService#loadElement(java.lang.Integer)
     */
    @GetMapping("/element/{dbid:\\d+}")
    @Operation(description = "load element by database ID")
    public Velement loadElement(@PathVariable("dbid") Long dbid) {
        return elementService.loadElement(dbid);
    }

    /**
     * @see org.verinice.interfaces.ElementService#loadElement(
     *java.lang.String, java.lang.String)
     */
    @GetMapping("/elements/source-id/{sourceId}/ext-id/{extId}")
    @Operation(description = "load element by source-id and ext-id")
    public Velement loadElement(@PathVariable("sourceId") String sourceId,
                                @PathVariable("extId") String extId) {
        return elementService.loadElement(sourceId, extId);
    }

    /**
     * @see org.verinice.interfaces.ElementService#loadElements(
     *java.lang.Integer, java.lang.Integer, java.lang.String,
     * java.lang.String)
     */
    @GetMapping(value = "/elements")
    @Operation(description = "get/search all elements by property key and description")
    public Set<Velement> loadElements(
            @Parameter(description = "key of the properties object to filter", example = "asset_description_method_availabilit") @RequestParam(required = false) String key,
            @Parameter(description = "description to the key of the properties object to filter", example = "1") @RequestParam(required = false) String value,
            @Parameter(description = "limit the number of returned elements, defaults to -Dorg.verinice.rest.jpa.defaultsize", example = "3") @RequestParam(required = false) Integer size,
            @Parameter(description = "index of the first the result, useful for paging") @RequestParam(required = false) Integer firstResult) {
        return elementService.loadElements(key, value, size, firstResult);
    }

    /**
     * @see org.verinice.interfaces.ElementService#loadElementsOfScope(
     *java.lang.Integer, java.lang.Integer, java.lang.Integer,
     * java.lang.String, java.lang.String)
     */
    @RequestMapping(value = "/scope/{scopeId}/elements", method = RequestMethod.GET)
    @Operation(description = "load all elements of one scope")
    public Set<Velement> loadElementsOfScope(
            @PathVariable(value = "scopeId") Integer scopeId,
            @Parameter(description = "key of the properties object to filter", example = "asset_value_method_availabilit") @RequestParam(required = false) String key,
            @Parameter(description = "description to the key of the properties object to filter", example = "1") @RequestParam(required = false) String value,
            @Parameter(description = "limit the number of returned elements, defaults to -Dorg.verinice.rest.jpa.defaultsize", example = "3") @RequestParam(required = false) Integer size,
            @Parameter(description = "index of the first the result, useful for paging") @RequestParam(required = false) Integer firstResult) {
        return elementService.loadElementsOfScope(scopeId, key, value, size, firstResult);
    }

    @GetMapping("/element/{parentId:\\d+}/children")
    @Operation(description = "load all (up to paging maximum) children of an element.")
    public Set<Velement> loadChildren(
            @PathVariable(value = "parentId") Long parentId,
            @Parameter(description = "key of the properties object to filter", example = "asset_value_method_availabilit") @RequestParam(required = false) String key,
            @Parameter(description = "description to the key of the properties object to filter", example = "1") @RequestParam(required = false) String value,
            @Parameter(description = "limit the number of returned elements, defaults to -Dorg.verinice.rest.jpa.defaultsize", example = "3") @RequestParam(required = false) Integer size,
            @Parameter(description = "index of the first the result, useful for paging") @RequestParam(required = false) Integer firstResult) {
        return elementService.loadChildren(parentId, key, value, size, firstResult);
    }

    @PostMapping("/elements")
    @Operation(description = "create an element.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "returns the created element"))
    public Velement insertElement(@Valid @RequestBody Velement element, HttpServletRequest request,
                                  HttpServletResponse response) {
        // Make sure the element is treated as new
        element.setDbid(0);
        Velement persistedElement = elementService.insertOrUpdateElement(element);

        response.setStatus(HttpServletResponse.SC_CREATED);
        String hostAddress = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String savedElementLocation = String.format("%s/element/%d", hostAddress, persistedElement.getDbid());
        response.setHeader("Location", savedElementLocation);
        return persistedElement;
    }

    @PutMapping("/element/{dbid:\\d+}")
    @Operation(description = "updated the element with the dbid specified in the URL. The dbid in the content of the request is ignored.")
    public void updateElement(
            @PathVariable(value = "dbid") Long dbid,
            @Valid @RequestBody Velement element,
            HttpServletResponse response) {
        if (dbid == 0) {
            throw new IllegalArgumentException("The dbid of an existing element cannot be 0.");
        }
        element.setDbid(dbid);
        elementService.insertOrUpdateElement(element);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
