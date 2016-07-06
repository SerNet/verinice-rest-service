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
package org.verinice.interfaces;

import java.util.Set;

import org.verinice.model.Velement;

/**
 * This service provides methods to manage verinice elements.
 * A verinice element is a generic element such as
 * an asset from ISO2700x. See class {@link Velement} for details.
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
public interface ElementService {

    /**
     * @param uuid The UUID of an element
     * @return The element with the given UUID or null if UUID is no found
     */
    public Velement getElement(String uuid);

    /**
     * @param limit
     *            - the limit of objects to be retrieved from database. If null
     *            a default limit is used.
     * @return All elements in database
     */
    public Set<Velement> getAllElements(Integer limit);
}
