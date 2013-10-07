package com.ft.api.jaxrs.client.exceptions;

import com.ft.api.jaxrs.errors.ErrorEntity;

import java.net.URI;

/**
 * RemoteApiException
 *
 * @author Simon.Gibbs
 */
public class RemoteApiException extends ApiException {

    private final int status;
    private final ErrorEntity entity;

    public RemoteApiException(URI uri, String httpMethod, int status) {
        super(uri, httpMethod);
        this.status = status;
        this.entity = null;
    }

    public RemoteApiException(URI uri, String httpMethod, int status, ErrorEntity entity) {
        super(messageOrNull(entity),uri, httpMethod);
        this.status = status;
        this.entity = entity;
    }

    private static String messageOrNull(ErrorEntity entity) {
        if(entity==null) {
            return null;
        }
        return entity.getMessage();
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return toStringHelper().add("status", status).add("entity", entity).toString();
    }


}
