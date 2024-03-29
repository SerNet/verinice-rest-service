/*******************************************************************************
 * Copyright (c) 2018 Alexander Ben Nasrallah.
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
 ******************************************************************************/
package org.verinice.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.verinice.exceptions.LinkValidationException;
import org.verinice.interfaces.LinkService;
import org.verinice.model.Vlink;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

/**
 * REST implementation of {@link LinkService}.
 */
@RestController
public class RestLinkService {

    @Autowired
    private LinkService linkService;

    /**
     * Load links by query.
     *
     * @param queryParams arbitrary String, Integer or Long properties of Vlink. Returns all if
     *                    the map is null or empty
     */
    @GetMapping("/links")
    @Operation(description = "Load links by query", parameters = {@Parameter(in = ParameterIn.QUERY, required = false, name="comment", description = "Key-Value-pairs of properties to filter links for. In this examples the key is set to 'comment'.", example = "mycomment")})
    public Set<Vlink> loadLinks(@RequestParam(required = false) Map<String, String> queryParams) {
        return linkService.loadLinks(queryParams);
    }

    /**
     * Inserts a new link.
     *
     * The link has to be valid, source and target have to exist and
     * the link type has to be defined in the hitro config, aka. SNCA.xml.
     */
    @PostMapping("/links")
    @Operation(description = "create links.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "return the created link"))
    public Vlink insertLink(@Valid @RequestBody Vlink link, HttpServletRequest request,
                             HttpServletResponse response) throws LinkValidationException {
        Vlink persistedLink = linkService.insertLinks(link);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return persistedLink;
    }
}
