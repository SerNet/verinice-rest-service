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

package org.verinice.model;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * TODO.
 *
 * @author Ruth Motza {@literal <rm[at]sernet[dot]de>}
 */
@SuppressWarnings("serial")
public class Account extends User {

    private long id;
    private String login;
    private String password;
    private int scopeId;
    private List<String> accountGroups;

    /**
     * Creates an Account with a login and a password.
     *
     * @param login the login name for the new Account
     * @param password the password for the new Account
     */
    public Account(String login, String password) {
        super(login, password, AuthorityUtils.createAuthorityList("USER"));
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScopeId() {
        return scopeId;
    }

    public void setScopeId(int scopeId) {
        this.scopeId = scopeId;
    }

    public List<String> getAccountGroups() {
        return accountGroups;
    }

    public void setAccountGroups(List<String> accountGroups) {
        this.accountGroups = accountGroups;
    }
}
