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
package org.verinice.interfaces;

import org.verinice.exceptions.LinkValidationException;
import org.verinice.model.Vlink;

import java.util.Map;
import java.util.Set;

/**
 * This service provides methods to manage links.
 */
public interface LinkService {

    /**
     * Load links by query.
     *
     * @param queryParams arbitrary String, Integer or Long properties of Vlink. Returns all if
     *                    the map is null or empty
     */
    Set<Vlink> loadLinks(Map<String, String> queryParams);

    /**
     * Insert a new link.
     *
     * The link has to be valid, i.e. source and target have to exist and
     * the link type has to be defined in the hitro config.
     */
    Vlink insertLinks(Vlink link) throws LinkValidationException;
}
