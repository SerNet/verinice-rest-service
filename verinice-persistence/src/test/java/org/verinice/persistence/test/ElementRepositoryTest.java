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
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.verinice.persistence.ElementRepository;
import org.verinice.persistence.PersistenceApplication;
import org.verinice.persistence.PropertyRepository;
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

    @Autowired
    private PropertyRepository pRepository;

    private CnATreeElement element;

    @Before
    public void init() {
        element = new CnATreeElement();
        // element.setUuid("eae74c67-b57b-4541-8de7-35cc43e93396");
        // element.setType("incident_scenario");
        // element.setUuid("abdb719c-e7b7-4c93-977c-55f3fb789e03");
        // element.setType("bsimodel");
        element.setUuid("f061407d-a60f-49ed-be3a-0b22ca703864");
        // element = elementRepository.save(element);
    }

    @Test
    public void testFindOne() {

        Pageable topTen = new PageRequest(0, 100);
        Page<CnATreeElement> findAll = elementRepository.findAll(topTen);
        findAll.forEach(elem -> LOG.debug(elem.toString()));
        // Iterable<CnATreeElement> elementResult = elementRepository.findAll();
        CnATreeElement findByUuid = elementRepository.findByUuid(element.getUuid());
        if (findByUuid == null) {
            fail("element null");
        }
        // LOG.debug("result: " + (elementResult != null));
        // if (elementResult != null) {
            // LOG.debug(elementResult.iterator().next().toString());
        // elementResult.forEach((element) -> LOG.debug(element.toString()));
        // }
        // check(findByUuid, element);
        LOG.debug(findByUuid.toString());
        // assertTrue(elementResult != null);

        // Iterable<Property> findAll = pRepository.findAll();
        // if (findAll != null) {
        // Property next = findAll.iterator().next();
        // if (next != null)
        // LOG.debug(next.toString());
        // // findAll.forEach(p -> {
        // // if (p != null)
        // // LOG.debug(p.toString());
        // else
        // LOG.debug("property null");
        // // );
        // } else
        // LOG.debug("no properties found");
    }

    private void check(CnATreeElement elementResult, CnATreeElement element2) {
        assertTrue("element null", elementResult != null);
        assertEquals("wrong uuid", elementResult.getUuid(), element.getUuid());
        // assertEquals("wrong type", elementResult.getType(),
        // element.getType());

    }

}
