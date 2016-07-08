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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.verinice.interfaces.AccountService;
import org.verinice.model.Account;


/**
 * 
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class.getName());

    // @Autowired
    // AccountRepository accountRepository;
    
    // private final PasswordEncoder encoder;

    public AccountServiceImpl() {
        // this.encoder = PasswordEncoderFactory.getInstance();
    }
    
    // @Override
    // public Account createAccount(Account rawAccount) {
    // Account securedAccount = encodePassword(rawAccount);
    // return save(securedAccount);
    // }
    //
    // @Override
    // public Account save(Account account) {
    // return accountRepository.save((Account)account);
    // }
    
    // @Override
    // public void delete(Account account) {
    // accountRepository.delete((Account)account);
    // }

    // TODO rmotza adapt
    @Override
    public Account findOne(Long accountId) {
        return new Account("test", "test");// accountRepository.findOne(accountId);
    }

    @Override
    public Account findByLogin(String login) {
        return new Account("test", "test");// accountRepository.findByLogin(login);
    }
    
    // private Account encodePassword(Account account) {
    // try {
    // Account securedAccount = cloneAccount(account);
//            securedAccount.setPassword(encoder.encode(account.getPassword()));
    // return securedAccount;
    // } catch (Exception e) {
    // logger.error("Error while creating password hash", e);
    // throw new RuntimeException("Error while creating account.");
    // }
    // }

    // private Account cloneAccount(Account account) {
    // Account clone = new Account(account.getLogin(), null,
    // account.getEmail());
    // clone.setFirstName(account.getFirstName());
    // clone.setLastName(account.getLastName());
    // return clone;
    // }
}
