package org.verinice.service.test;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.verinice.interfaces.AccountService;
import org.verinice.model.Account;
import org.verinice.persistence.VeriniceAccountDao;
import org.verinice.persistence.entities.MockBuilder;


/**
 *
 * @author Daniel Murygin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ServiceApplicationTest.class)
public class AccountServiceTest {

    @Mock
    VeriniceAccountDao dao;

    @InjectMocks
    @Autowired
    AccountService accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createAccount() {

        Account accountResult = MockBuilder.createTestAccount();
        when(dao.findAccount("test")).thenReturn(accountResult);

        Account account = accountService.findByLogin("test");

        Assert.assertNotNull(account);
        Assert.assertEquals("test", account.getLogin());
        Assert.assertEquals("test", account.getPassword());
    }
}
