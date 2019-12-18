package org.verinice.exceptions;

/**
 * This exception should be thrown if a
 * {@link org.verinice.persistence.entities.CnaLink}
 */
public class LinkValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public LinkValidationException(String msg) {
        super(msg);
    }
}
