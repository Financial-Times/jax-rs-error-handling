package com.ft.api.jaxrs.errors;

/**
 * ErrorEntity
 *
 * @author Simon.Gibbs
 */
public class ErrorEntity {

    private final String message;

    public ErrorEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
