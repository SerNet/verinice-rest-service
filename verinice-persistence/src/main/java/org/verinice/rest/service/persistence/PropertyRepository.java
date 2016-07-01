/*******************************************************************************
 * Copyright (c) 2016 Ruth Motza <rm{a}sernet{dot}de>.
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
 *     Ruth Motza <rm{a}sernet{dot}de> - initial API and implementation
 ******************************************************************************/
package org.verinice.rest.service.persistence;

import org.springframework.data.repository.CrudRepository;
import org.verinice.rest.service.persistence.entities.Property;

/**
 * 
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public interface PropertyRepository extends CrudRepository<Property, Long> {

    // CnATreeElement findByDbid(@Param("dbid") String uuid);
	
    // CnATreeElement findById(@Param("dbid") long dbid);
	
}
