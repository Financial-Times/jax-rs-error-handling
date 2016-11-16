package com.ft.api.jaxrs.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * ErrorEntity
 *
 * @author Simon.Gibbs
 */
public class ErrorEntity {

    private final String message;

    public ErrorEntity(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    protected Objects.ToStringHelper toStringHelper() {
        return Objects
                .toStringHelper(this)
                .add("message", message);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
