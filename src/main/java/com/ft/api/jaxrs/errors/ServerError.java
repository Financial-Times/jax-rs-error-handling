package com.ft.api.jaxrs.errors;

import com.google.common.base.Preconditions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ServerError
 *
 * @author Simon.Gibbs
 */
public class ServerError {

    public static ServerErrorBuilder status(int code) {
        return new ServerErrorBuilder(code);
    }


    /**
     * Provides a standardised way of translating business layer exceptions into web layer exceptions
     * compatible with the JAX-RS standard.
     *
     * @author Simon.Gibbs
     */
    public static class ServerErrorBuilder {

        private String message;
        private int statusCode;

        public ServerErrorBuilder(int statusCode) {
            Preconditions.checkArgument(statusCode<=599,"max server code is 599");
            Preconditions.checkArgument(statusCode>=500,"min server code is 500");
            this.statusCode = statusCode;
        }

        public ServerErrorBuilder error(String message) {
            checkNotNull(message, "you must supply a message");
            this.message = message;
            return this;
        }

        public WebApplicationServerException exception(Throwable cause) {
            checkNotNull(cause, "optional argument \"cause\" was unexpectedly null."); // surely null is impossible in a catch block!
            return new WebApplicationServerException(cause, response());
        }

        public WebApplicationServerException exception() {
            return new WebApplicationServerException(response());
        }

        public Response response() {
            return Response.serverError()
                .status(statusCode)
                .entity(new ErrorEntity(message))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
        }
    }
}
