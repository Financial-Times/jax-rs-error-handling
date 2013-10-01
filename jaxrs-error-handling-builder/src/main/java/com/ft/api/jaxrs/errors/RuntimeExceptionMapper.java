package com.ft.api.jaxrs.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * RuntimeExceptionMapper
 *
 * @author Simon.Gibbs
 */
public class RuntimeExceptionMapper  implements ExceptionMapper<RuntimeException> {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

    public static final String GENERIC_MESSAGE = "server error";

    @Override
    public Response toResponse(RuntimeException exception) {

        // ensure exceptions are logged!
        LOG.error("unhandled exception", exception);

        // skip processing of responses that are already standardised.
        if(exception instanceof WebApplicationException) {
            Response response = ((WebApplicationException) exception).getResponse();
            if(response.getEntity() instanceof ErrorEntity) {
                return response;
            }
        }

        // force a standard response
        return ServerError.status(500).error(GENERIC_MESSAGE).response();
    }

}
