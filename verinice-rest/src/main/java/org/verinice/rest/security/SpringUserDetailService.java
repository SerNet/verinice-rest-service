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
package org.verinice.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.verinice.model.Account;
import org.verinice.service.AccountServiceImpl;

/**
 *
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@Service
public class SpringUserDetailService implements UserDetailsService{

    @Autowired
    AccountServiceImpl accountService;
    
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account account = accountService.findByLogin(login);
        if (account == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(account.getLogin(), account.getPassword(), true, true, true, true,
                AuthorityUtils.createAuthorityList("USER"));
        // Account account = accountRepository.findByUsername(username);
        // if(account != null) {
        // return new User(account.getUsername(), account.getPassword(), true,
        // true, true, true,
        // AuthorityUtils.createAuthorityList("USER"));
        // } else {
        // throw new UsernameNotFoundException("could not find the user '"
        // + username + "'");
        // }
        // return new User("test", "test",
        // AuthorityUtils.createAuthorityList("USER"));
    }
    
}
