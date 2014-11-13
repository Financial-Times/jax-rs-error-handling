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
    private Object context;
    protected LogLevel logLevel = LogLevel.ERROR;

    public AbstractErrorBuilder(int code) {
        checkStatusCode(code);
        this.statusCode = code;
    }

    protected String getMessage() {
        return message;
    }

    protected int getStatusCode() {
        return statusCode;
    }

    protected abstract void checkStatusCode(int code);

    public B error(String message) {
        checkNotNull(message, "you must supply a message");
        this.message = message;
        return (B) this;
    }

    /**
     * <p>Used with enumerated reason code systems. Extracts a message from the enum per</p>
     *
     * <code>error(enumeratedReason.toString())</code>
     *
     * <p>This is used where the business wants to sign off on and control messages, and allows
     * developers to consolidate signed off messages in an enum with it's own Javadoc etc.
     * and manage the messages in source control.</p>
     *
     * @param enumeratedReason any enum value
     * @return the builder
     */
    public B reason(Enum<?> enumeratedReason) {
        checkNotNull(enumeratedReason, "you must supply an enumeratedReason");
        return error(enumeratedReason.toString());
    }

    public B context(Object context) {
        this.context = context;
        return (B) this;
    }

    public B logLevel(LogLevel level) {
        this.logLevel = level;
        return (B) this;
    }

    public Response response() {

        ErrorEntity entity = Errors.buildEntity(message,context);

        if(entity==null) {
            throw new NullPointerException("Configured ErrorEntityFactory failed to produce an entity");
        }

        //This is not a server error and the serverError method is used as a template to build a generic error response
        return Response.serverError()
                .status(statusCode)
                .entity(entity)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }


    /**
     * Creates an exception wrapping the configured response and the <code>cause</code>. The exception implements a Jersey
     * exception type and contains an HTTP response code and body, and therefore any configured exception mapping is
     * skipped including {@link RuntimeExceptionMapper}.
     * @param cause
     * @return an exception, which should be thrown.
     */
    public abstract E exception(Throwable cause);

    /**
     * Creates an exception wrapping the configured response. The exception implements a Jersey exception type and
     * contains an HTTP response code and body, and therefore any configured exception mapping is
     * skipped including {@link RuntimeExceptionMapper}.
     * @return an exception, which should be thrown.
     */
    public abstract E exception();

}
