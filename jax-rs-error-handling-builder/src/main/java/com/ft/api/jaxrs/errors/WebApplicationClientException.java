package com.ft.api.jaxrs.errors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Thrown when the server attributes the failure of a a HTTP transaction to the client.
 *
 * @author Simon.Gibbs
 */
public class WebApplicationClientException extends WebApplicationException {

    public WebApplicationClientException(Response response) {
        super(response);
    }

    public WebApplicationClientException(Throwable cause, Response response) {
        super(cause, response);
    }

    public WebApplicationClientException(String message, Throwable cause, Response response) {
        super(message, cause, response);
    }

}
