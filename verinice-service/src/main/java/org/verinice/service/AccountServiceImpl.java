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
package org.verinice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.verinice.interfaces.AccountService;
import org.verinice.model.Account;
import org.verinice.persistence.VeriniceAccountDAO;

/**
 * 
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    VeriniceAccountDAO dao;

    public AccountServiceImpl() {
    }

    @Override
    public Account findByLogin(String login) {
        return dao.findAccount(login);
    }

}
