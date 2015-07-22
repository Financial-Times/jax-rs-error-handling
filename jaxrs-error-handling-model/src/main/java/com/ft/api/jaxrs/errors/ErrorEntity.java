package com.ft.api.jaxrs.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * ErrorEntity
 *
 * @author Simon.Gibbs
 */
@EqualsAndHashCode
public class ErrorEntity {

    @Getter
    private final String message;

    public ErrorEntity(@JsonProperty("message") String message) {
        this.message = message;
    }

    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects
                .toStringHelper(this)
                .add("message", message);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
