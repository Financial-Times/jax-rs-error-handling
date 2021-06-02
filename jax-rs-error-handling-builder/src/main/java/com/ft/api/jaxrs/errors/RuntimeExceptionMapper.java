package com.ft.api.jaxrs.errors;

import com.google.common.base.MoreObjects;
import java.net.SocketTimeoutException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RuntimeExceptionMapper
 *
 * <p>Converts any RuntimeException including those that result in a response code of - 415 - 500 -
 * 503 to a sensible response with a human-readable error message
 *
 * <p>Some other exceptional scenarios like requests that result in a response code of - 400 are not
 * handled because Jersey constructs error responses.
 *
 * @author Simon.Gibbs
 */
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

  private static final Logger LOG = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

  public static final String GENERIC_MESSAGE = "server error";

  @Override
  public Response toResponse(RuntimeException exception) {

    if (exception instanceof NotFoundException) {
      String message = "404 Not Found";
      LOG.debug(message);
      return ClientError.status(404).error(message).response();
    }

    if (exception instanceof WebApplicationException) {
      Response response = ((WebApplicationException) exception).getResponse();

      // skip processing of responses that are already present.
      if (response.getEntity() != null) {
        return response;
      }

      // fill out null responses
      String message = MoreObjects.firstNonNull(exception.getMessage(), GENERIC_MESSAGE);

      if (!GENERIC_MESSAGE.equals(message)) {
        // Don't turn this off. You should be using ServerError and ClientError builders.
        LOG.warn(
            "Surfaced exception message from unknown tier. Expected ErrorEntity from web tier.");
      }
      AbstractErrorBuilder<?, ?> responseBuilder;
      if (response.getStatus() < 500) {
        if (GENERIC_MESSAGE.equals(
            message)) { // if we didn't get a specific message from the exception
          message = "client error";
        }
        responseBuilder = ClientError.status(response.getStatus());
      } else {
        responseBuilder = ServerError.status(response.getStatus());

        // ensure server error exceptions are logged!
        LOG.error("Server error: ", exception);
      }

      return responseBuilder.error(message).response();
    } else if (exception.getCause() instanceof SocketTimeoutException) {
      return ServerError.status(504).exception(exception).getResponse();
    }

    /* Force a standard response for unexpected error types.
     * This should always be logged as ERROR (because unexpected)
     */
    LOG.error("Server error, unexpected exception: ", exception);
    return ServerError.status(500).error(GENERIC_MESSAGE).response();
  }
}
