package com.ft.api.jaxrs.errors;

import com.google.common.base.Objects;
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


        if(exception instanceof WebApplicationException) {
            Response response = ((WebApplicationException) exception).getResponse();

            // skip processing of responses that are already standardised.
            if(response.getEntity() instanceof ErrorEntity) {
                return response;
            }

            // fill out null responses
            if(response.getEntity() == null) {

                String message = Objects.firstNonNull(exception.getMessage(),GENERIC_MESSAGE);

                if(!GENERIC_MESSAGE.equals(message)) {
                    LOG.warn("Surfaced exception message from unknown tier. Expected ErrorEntity from web tier.");
                }
                AbstractErrorBuilder<?,?> responseBuidler;
                if(response.getStatus()<500) {
                    responseBuidler = ClientError.status(response.getStatus());
                } else {
                    responseBuidler = ServerError.status(response.getStatus());
                }

                return responseBuidler.error(message).response();

            }


        }

        // force a standard response
        return ServerError.status(500).error(GENERIC_MESSAGE).response();
    }

}
