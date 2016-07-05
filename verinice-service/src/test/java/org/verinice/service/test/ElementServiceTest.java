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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.verinice.interfaces.ElementService;
import org.verinice.model.Velement;
import org.verinice.persistence.ElementRepository;
import org.verinice.persistence.entities.CnATreeElement;
import org.verinice.persistence.entities.MockBuilder;
import org.verinice.service.ElementServiceImpl;
/**
 *
 * @author Daniel Murygin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ServiceApplicationTest.class)
public class ElementServiceTest {
    
    @Mock 
    ElementRepository elementRepository;
    
    @InjectMocks
    @Autowired
    ElementService elementService = new ElementServiceImpl();
    
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
    public void loadElement() {  
        String uuid = "123";
        CnATreeElement dbEntity = MockBuilder.createAsset(uuid);
        when(elementRepository.findByUuid(dbEntity.getUuid())).thenReturn(dbEntity);
       
        Velement element = elementService.getElement(uuid);
        Assert.assertNotNull(element);
        Assert.assertEquals(uuid, element.getUuid());
    }
}