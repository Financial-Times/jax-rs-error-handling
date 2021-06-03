package com.ft.api.jaxrs.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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

  protected MoreObjects.ToStringHelper toStringHelper() {
    return MoreObjects.toStringHelper(this).add("message", message);
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return toStringHelper().toString();
  }
}
