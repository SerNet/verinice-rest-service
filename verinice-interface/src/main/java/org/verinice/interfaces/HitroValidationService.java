package org.verinice.interfaces;

import org.verinice.exceptions.LinkValidationException;

public interface HitroValidationService {

    /**
     * Checks that a link of type linkId between dependantType and dependencyType
     * is defined in the hitro config, aka. SNCA.xml.
     */
    void validateLink(String dependantType, String dependencyType, String linkId) throws LinkValidationException;
}
