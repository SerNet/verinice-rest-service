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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.verinice.interfaces.LinkService;
import org.verinice.model.Vlink;
import javax.servlet.http.HttpServletRequest;
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
     * @param queryParams arbitraty String, Integer or Long properties of Vlink. Returns all if
     *                    the map is null or empty
     */
    @RequestMapping(value = "/links")
    public Set<Vlink> loadLinks(@RequestParam(required = false) Map<String, String> queryParams) {
        return linkService.loadLinks(queryParams);
    }
}
