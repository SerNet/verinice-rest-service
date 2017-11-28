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
    Velement loadElement(String uuid);

    /**
     * @param dbid The dbid of an element
     * @return The element with the given dbid or null if dbid is no found
     */
    Velement loadElement(Long dbid);

    /**
     * @param sourceId A source id
     * @param extId An external id
     * @return The element with the source and the ext id or null if there is no element
     *  with the source and the ext id
     */
    Velement loadElement(String sourceId, String extId);

    /**
     *
     * @param key A property key / id from SNCA.xml that need to exist. % can be
     *  used as a placeholder
     * @param value A propertyValue that need to exist. % can be used as a
     *  placeholder.
     * @param size The number of objects to be retrieved from database. If null
     *  or < 1 a default limit is used.
     * @param firstResult The index of the first result to be retrieved if null
     * or < 0 firstResult is 0
     * @return All elements in database based on the restricting parameters
     */
    Set<Velement> loadElements(String key, String value, Integer size, Integer firstResult);

    /**
     * @param parentId the database ID of the parent.
     * @param key A property key / id from SNCA.xml that need to exist. % can be
     *  used as a placeholder
     * @param value A propertyValue that need to exist. % can be used as a
     *  placeholder.
     * @param size The number of objects to be retrieved from database. If null
     *  or < 1 a default limit is used.
     * @param firstResult The index of the first result to be retrieved if null
     * or < 0 firstResult is 0
     * @return All elements in database based on the restricting parameters
     */
    Set<Velement> loadChildren(Long parentId, String key, String value, Integer size,
            Integer firstResult);

    /**
     * @param scopeId The database id of a scope. May be null.
     * @param key A property key / id from SNCA.xml that need to exist. % can be
     *  used as a placeholder
     * @param value A propertyValue that need to exist. % can be used as a
     *  placeholder.
     * @param size The number of objects to be retrieved from database. If null
     *  or < 1 a default limit is used.
     * @param firstResult The index of the first result to be retrieved if null
     * or < 0 firstResult is 0
     * @return All elements in database based on the restricting parameters
     */
    Set<Velement> loadElementsOfScope(Integer scopeId, String key, String value, Integer size,
            Integer firstResult);

    /**
     * Inserts the given element to the database if Velemtent.dbid is {@code 0}
     * updates otherwise.
     *
     * @param element
     *            The element to insert or update.
     * @return The database id of the inserted or updated element.
     */
    long insertOrUpdateElement(Velement element);
}
