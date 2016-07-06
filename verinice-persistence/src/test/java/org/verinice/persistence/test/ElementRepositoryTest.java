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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.verinice.persistence.ElementRepository;
import org.verinice.persistence.PersistenceApplication;
import org.verinice.persistence.entities.CnATreeElement;


/**
 * 
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PersistenceApplication.class)
public class ElementRepositoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(ElementRepositoryTest.class);
    @Autowired
    private ElementRepository elementRepository;

    private CnATreeElement element;
    private CnATreeElement element2;

    @Before
    public void init() {
        element = new CnATreeElement();
        element2 = new CnATreeElement();
        element2.setUuid("eae74c67-b57b-4541-8de7-35cc43e93396");
        element2.setType("incident_scenario");
        element.setUuid("abdb719c-e7b7-4c93-977c-55f3fb789e03");
        element.setType("bsimodel");
        element = elementRepository.save(element);
        element = elementRepository.save(element2);
    }

    @Test
    public void testFindOne() {

        CnATreeElement findByUuid = elementRepository.findByUuid(element.getUuid());
        check(findByUuid, element);
        findByUuid = elementRepository.findByUuid(element2.getUuid());
        check(findByUuid, element2);
    }

    private void check(CnATreeElement elementResult, CnATreeElement element2) {
        assertTrue("element null", elementResult != null);
        assertEquals("wrong uuid", elementResult.getUuid(), element.getUuid());
        assertEquals("wrong type", elementResult.getType(), element.getType());

    }

}
