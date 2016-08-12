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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.verinice.model.Account;
import org.verinice.service.AccountServiceImpl;

/**
 * Spring security configuration. This class load an account by the given 
 * username. Password check is not handles by this class.
 * 
 * See: http://ryanjbaxter.com/2015/01/06/securing-rest-apis-with-spring-boot/
 * 
 * @author Ruth Motza {@literal <rm[at]sernet[dot]de>}
 */
@Service
public class SpringUserDetailsService implements UserDetailsService {

    @Autowired
    AccountServiceImpl accountService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account account = accountService.findByLogin(login);

        if (account == null) {
            throw new UsernameNotFoundException(login);
        }

        return account;
    }
}
