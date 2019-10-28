/*
 * Copyright (c) 2016 Daniel Murygin.
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
 *  Daniel Murygin - initial API and implementation
 */
package org.verinice.service.test;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.verinice.interfaces.LinkService;
import org.verinice.model.Vlink;
import org.verinice.persistence.CnaLinkDao;
import org.verinice.persistence.entities.CnaLink;
import org.verinice.persistence.entities.MockBuilder;
import org.verinice.service.LinkServiceImpl;

/**
 *
 * @author Daniel Murygin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApplicationTest.class)
public class LinkServiceTest {

    @Mock
    CnaLinkDao dao;

    @InjectMocks
    @Autowired
    LinkService linkService = new LinkServiceImpl("");

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
         MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void loadLink() {
        String uuid = "123";
        CnaLink dbEntity = MockBuilder.createLink(uuid);
        when(dao.find(null)).thenReturn(Collections.singletonList(dbEntity));

        Set<Vlink> links = linkService.loadLinks(null);
        Assert.assertNotNull(links);
    }
}
