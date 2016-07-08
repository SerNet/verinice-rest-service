package org.verinice.service.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.verinice.interfaces.AccountService;
import org.verinice.model.Account;
import org.verinice.security.PasswordEncoderFactory;


/**
 *
 * @author Daniel Murygin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ServiceApplicationTest.class)
public class AccountServiceTest {

    // @Mock
    // AccountRe elementRepository;

    @InjectMocks
    @Autowired
    AccountService accountService;
    
    private final PasswordEncoder encoder;

    public AccountServiceTest() {
        this.encoder = PasswordEncoderFactory.getInstance();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void createAccount() {
        // String login = "test";
        // String password = "test";
        // Account account = MockBuilder.createAsset(login, password);
        // when(accountRepository.findByLogin().thenReturn(account);

        Account account = accountService.findByLogin("test");

        Assert.assertNotNull(account);
        Assert.assertEquals("test", account.getLogin());
        Assert.assertEquals("test", account.getPassword());
        // final String login = UUID.randomUUID().toString().substring(0,8);
        // final String password = UUID.randomUUID().toString().substring(0,10);
        // final String email = login + "@sernet.de";
        // Account rawAccount = new Account(login, password);
        // accountService.createAccount(rawAccount);
        //
        // Account foundAccount = accountService.findByLogin(login);
        // Assert.assertNotNull("Account with login: " + login + " not found.",
        // foundAccount);
        // Assert.assertEquals(login, foundAccount.getLogin());
        // Assert.assertTrue("Password does nmot match",
        // encoder.matches(password, foundAccount.getPassword()));
        //
        // accountService.delete(foundAccount);
        // foundAccount = accountService.findByLogin(login);
        // Assert.assertNull("Account with login: " + login + " found after
        // deletion.", foundAccount);
    }
}
