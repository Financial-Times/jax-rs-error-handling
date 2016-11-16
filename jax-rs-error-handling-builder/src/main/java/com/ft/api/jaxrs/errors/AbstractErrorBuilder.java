package com.ft.api.jaxrs.errors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * AbstractErrorBuilder
 *
 * @author Simon.Gibbs
 */
public abstract class AbstractErrorBuilder<E, B extends AbstractErrorBuilder> {


    private String message;
    private int statusCode;

    public AbstractErrorBuilder(int code) {
        checkStatusCode(code);
        this.statusCode = code;
    }

    protected abstract void checkStatusCode(int code);

    public B error(String message) {
        checkNotNull(message, "you must supply a message");
        this.message = message;
        return (B) this;
    }

    public Response response() {
        return Response.serverError()
                .status(statusCode)
                .entity(new ErrorEntity(message))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    public abstract E exception(Throwable cause);

    public abstract E exception();

}
