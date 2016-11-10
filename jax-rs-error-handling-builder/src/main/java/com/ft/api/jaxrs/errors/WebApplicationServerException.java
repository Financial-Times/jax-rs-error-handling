package com.ft.api.jaxrs.errors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * <p>Thrown when the server accepts responsibility for the failure of an HTTP transaction.</p>
 *
 * <p>The server may accept the responsibility for a failure when it is unclear where fault lies, or to be diplomatic.</p>
 *
 *
 *
 * @author Simon.Gibbs
 */
public class WebApplicationServerException extends WebApplicationException {

    public WebApplicationServerException(Response response) {
        super(response);
    }

    public WebApplicationServerException(Throwable cause, Response response) {
        super(cause, response);
    }

}
