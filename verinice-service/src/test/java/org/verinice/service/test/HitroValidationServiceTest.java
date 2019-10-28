package org.verinice.service.test;

import de.sernet.hitro.Huientities;
import org.junit.Before;
import org.junit.Test;
import org.verinice.exceptions.LinkValidationException;
import org.verinice.service.HitroValidationServiceImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Optional;

public class HitroValidationServiceTest {
    private HitroValidationServiceImpl underTest;

    @Before
    public void setup() throws JAXBException {
        var jaxbContext = JAXBContext.newInstance(Huientities.class);
        var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        var config = (Huientities) jaxbUnmarshaller.unmarshal(getClass().getClassLoader().getResourceAsStream("SNCA.xml"));
        underTest = new HitroValidationServiceImpl(() -> Optional.of(config));
    }

    @Test
    public void testValidLinks() throws LinkValidationException {
        underTest.validateLink("control","incident_scenario","rel_control_incscen");
    }

    @Test(expected = LinkValidationException.class)
    public void testInvalidLinks() throws LinkValidationException {
        underTest.validateLink("control","person","rel_control_incscen");
    }
}
