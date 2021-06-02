package com.ft.api.jaxrs.errors;

import com.ft.api.jaxrs.errors.entities.DefaultErrorEntityFactory;
import com.ft.api.jaxrs.errors.entities.ErrorEntityFactory;

/**
 * Errors
 *
 * @author Simon.Gibbs
 */
public class Errors {

  private static ErrorEntityFactory entityFactory;

  static {
    resetCustomisation();
  }

  public static void customise(ErrorEntityFactory factory) {
    entityFactory = factory;
  }

  public static void resetCustomisation() {
    entityFactory = new DefaultErrorEntityFactory();
  }

  public static ErrorEntity buildEntity(String message, Object context) {
    return entityFactory.entity(message, context);
  }
}
