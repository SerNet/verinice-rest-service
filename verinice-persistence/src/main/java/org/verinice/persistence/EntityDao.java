/*******************************************************************************
 * Copyright (c) 2017 Alexander Ben Nasrallah.
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
 *     Alexander Ben Nasrallah <an@sernet.de> - initial API and implementation
 ******************************************************************************/
package org.verinice.persistence;

import org.verinice.persistence.entities.Entity;

/**
 * Data access object (DAO) for database entity {@link org.verinice.persistence.entities.Entity}
 * which provides methods to read entities.
 *
 * @author Alexander Ben Nasrallah
 */
public interface EntityDao {
    Entity findByElementId(long elementId);
    Entity findByDbid(long dbid);
}
