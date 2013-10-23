package com.ft.api.jaxrs.errors.entities;

import com.ft.api.jaxrs.errors.ErrorEntity;

/**
 * ErrorEntityFactory
 *
 * @author Simon.Gibbs
 */
public interface ErrorEntityFactory {

    public ErrorEntity entity(String message, Object context);

}
