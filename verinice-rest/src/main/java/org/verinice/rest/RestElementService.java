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
 * REST implementation of the element service.
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@RestController
public class RestElementService implements ElementService {

    @Autowired
    ElementService elementService;
    
    @RequestMapping("/element/{uuid}")
    public Velement getElement(@PathVariable("uuid")  String uuid) {
        return elementService.getElement(uuid);     
    }

    @RequestMapping(value = "/elements")
    public Set<Velement> getAllElements(
            @RequestParam(required = false) Integer limit) {
        return elementService.getAllElements(limit);
    }
}
