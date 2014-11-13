package com.ft.api.jaxrs.errors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * <p>Thrown when the server accepts responsibility for the failure of an HTTP transaction.</p>
 *
 * <p>The server may accept the responsibility for a failure when it is unclear where fault lies, or to be diplomatic.</p>
 *
 * @author Simon.Gibbs
 */
public class WebApplicationServerException extends WebApplicationException {

    private final LogLevel level;

    public WebApplicationServerException(Response response) {
        this(null,response,LogLevel.ERROR);
    }

    public WebApplicationServerException(Throwable cause, Response response) {
        this(cause, response, LogLevel.ERROR);
    }

    public WebApplicationServerException(Throwable cause, Response response, LogLevel level) {
        super(cause, response);
        this.level = level;
    }

    public LogLevel getLevel() {
        return level;
    }
}
