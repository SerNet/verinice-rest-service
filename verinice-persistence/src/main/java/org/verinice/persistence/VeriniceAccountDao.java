/*******************************************************************************
 * Copyright (c) 2016  Daniel Murygin.
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
 *     Daniel Murygin <dm[at]sernet[dot]de> - Initial API and implementation
 ******************************************************************************/
package org.verinice.persistence;

import org.verinice.model.Account;

/**
 * Data access object (DAO) for  {@link Account}s
 * which provides methods to create, read, update or delete accounts.
 * 
 * @author Daniel Murygin
 */
public interface VeriniceAccountDao {

    /**
     * Returns an {@link Account} with the given login name.
     *
     * @param loginName the login name for which the according Account
     *  should be returned
     * @return - the Account with the given user name or null if doesn't
     *  exist.
     */
    Account findByLoginName(String loginName);
    
}