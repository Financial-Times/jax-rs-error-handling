package com.ft.api.jaxrs.errors.entities;

import com.ft.api.jaxrs.errors.ErrorEntity;

/**
 * DefaultErrorEntityFactory
 *
 * @author Simon.Gibbs
 */
public class DefaultErrorEntityFactory implements ErrorEntityFactory {
  @Override
  public ErrorEntity entity(String message, Object context) {
    return new ErrorEntity(message);
  }
}
