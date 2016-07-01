/*
 * Copyright (c) 2016 Daniel Murygin.
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
 *  Daniel Murygin - initial API and implementation
 */
package org.verinice.service;

import org.verinice.model.Velement;
import org.verinice.persistence.entities.CnATreeElement;

/**
 *
 * @author Daniel Murygin
 */
public class ElementConverter {

    static Velement elementForEntity(CnATreeElement dbEntity) {
        if(dbEntity==null) {
            return null;
        }
        Velement element = new Velement();
        element.setUuid(dbEntity.getUuid());   
        element.setType(dbEntity.getType());
        // TODO: convert properties
        return element;
    }
    
}
