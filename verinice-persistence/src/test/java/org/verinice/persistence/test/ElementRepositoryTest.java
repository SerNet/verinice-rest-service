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
package org.verinice.persistence.test;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.verinice.persistence.PersistenceApplication;
import org.verinice.persistence.entities.CnaTreeElement;
import org.verinice.persistence.CnaTreeElementRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * 
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PersistenceApplication.class)
@ActiveProfiles("persistenceTest")
public class ElementRepositoryTest {

    @Autowired
    private CnaTreeElementRepository elementRepository;

    private CnaTreeElement element;
    private CnaTreeElement element2;

    @Before
    public void init() {
        element = new CnaTreeElement();
        element.setUuid("abdb719c-e7b7-4c93-977c-55f3fb789e03");
        element.setType("bsimodel");
        element.setSourceId("ElementRepositoryTest");
        element.setExtId("ENTITY_54075212");
        element = elementRepository.save(element);
        
        element2 = new CnaTreeElement();
        element2.setUuid("eae74c67-b57b-4541-8de7-35cc43e93396");
        element2.setType("incident_scenario");
        element2.setSourceId("ElementRepositoryTest");
        element2.setExtId("ENTITY_08634056");
        element2 = elementRepository.save(element2);
    }
    
    @After
    public void cleanUp() {
        elementRepository.delete(element);
        elementRepository.delete(element2);
    }

    @Test
    public void testFindOne() {
        CnaTreeElement findByUuid = elementRepository.findByUuid(element.getUuid());
        check(findByUuid, element);
        findByUuid = elementRepository.findByUuid(element2.getUuid());
        check(findByUuid, element2);
    }
    
    @Test
    public void testFindBySourceIdAndExtId() {
        CnaTreeElement foundElement = elementRepository.findBySourceIdAndExtId(element.getSourceId(), element.getExtId());
        check(foundElement, element);
        CnaTreeElement foundElement2 = elementRepository.findBySourceIdAndExtId(element2.getSourceId(), element2.getExtId());
        check(foundElement2, element2);
    }

    private void check(CnaTreeElement expectedElement, CnaTreeElement element) {
        assertTrue("Element is null", element != null);
        assertEquals("Element has wrong uuid", expectedElement.getUuid(), element.getUuid());
        assertEquals("Element has wrong type", expectedElement.getType(), element.getType());
        assertEquals("Element has wrong source id", expectedElement.getSourceId(), element.getSourceId());
        assertEquals("Element has wrong ext id", expectedElement.getExtId(), element.getExtId());

    }

}
