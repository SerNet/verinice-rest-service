/*******************************************************************************
 * Copyright (c) 2016 Ruth Motza.
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
 *     Ruth Motza <rm[at]sernet[dot]de> - Initial API and implementation
 *     Daniel Murygin <dm[at]sernet[dot]de> - Refactoring, Javadoc
 ******************************************************************************/
package org.verinice.persistence;

import org.verinice.persistence.entities.CnaTreeElement;

import java.util.List;

/**
 * Data access object (DAO) for database entity {@link CnaTreeElement}
 * which provides methods to create, read, update or delete
 * entities.
 * 
 * @author Ruth Motza
 * @author Daniel Murygin
 */
public interface CnaTreeElementDao {

    /**
     * @param uuid A UUID 
     * @return The CnaTreeElement with the UUID or null if there is no element
     *  with the UUID
     */
    CnaTreeElement findByUuid(String uuid);

    /**
     * @param sourceId A source id
     * @param extId An external id
     * @return The CnaTreeElement with the source and the ext id or null if there is no element
     *  with the source and the ext id
     */
    CnaTreeElement findBySourceIdAndExtId(String sourceId, String extId);
    
    /**
     * Finds CnaTreeElements by the given search parameters. Result size is 
     * configurable by size and a first returned element.
     * 
     * @param scopeId The database id of a scope. May be null.
     * @param key The key / id of a property as defined in SNCA.xml. May be null.
     * @param value The value of a property. % can be used as a placeholder. May
     *  be null.
     * @param size The maximum number of elements which are returned from the
     *  result. May be null.
     * @param firstResult The index of the first element which is returned from 
     *  the result. May be null.
     * @return A list of CnaTreeElements. If no element is found an empty list 
     *  is returned (never null).
     */
    List<CnaTreeElement> findByScopeKeyValue(Integer scopeId, String key, String value, Integer size, Integer firstResult);

    /**
     * Saves {@link CnaTreeElement} and returns the saved object
     * 
     * @param element
     *            The element to be saved.
     */
    CnaTreeElement save(CnaTreeElement element);

}