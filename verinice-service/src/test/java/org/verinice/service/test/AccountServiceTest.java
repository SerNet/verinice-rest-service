package org.verinice.service.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.verinice.interfaces.AccountService;
import org.verinice.model.Account;
import org.verinice.persistence.VeriniceAccountDao;
import org.verinice.persistence.entities.MockBuilder;
import org.verinice.service.AccountServiceImpl;

import static org.mockito.Mockito.when;


/**
 *
 * @author Daniel Murygin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApplicationTest.class)
public class AccountServiceTest {

    @Mock
    VeriniceAccountDao dao;

    @InjectMocks
    AccountService accountService = new AccountServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createAccount() {

        Account accountResult = MockBuilder.createTestAccount();
        when(dao.findByLoginName("test")).thenReturn(accountResult);

        Account account = accountService.findByLogin("test");

        Assert.assertNotNull(account);
        Assert.assertEquals("test", account.getLogin());
        Assert.assertEquals("test", account.getPassword());
    }
}
