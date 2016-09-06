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
 *     Ruth Motza <rm[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package org.verinice.rest;

import org.verinice.model.Velement;

/**
 * Wraps information needed to update an instance of {@link Velement}.
 * This class is used as a POST parameter for the REST service.
 * 
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public class UpdateElementCollection {

    private Velement velement;
    private boolean useUuid;

    public UpdateElementCollection() {
    }

    public Velement getVelement() {
        return velement;
    }

    public boolean isUseUuid() {
        return useUuid;
    }

}
