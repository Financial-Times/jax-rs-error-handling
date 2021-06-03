package com.ft.api.jaxrs.errors;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  public static class ServerErrorBuilder
      extends AbstractErrorBuilder<WebApplicationServerException, ServerErrorBuilder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerErrorBuilder.class);

    public ServerErrorBuilder(int statusCode) {
      super(statusCode);
    }

    @Override
    protected void checkStatusCode(int code) {
      Preconditions.checkArgument(code <= 599, "max server code is 599");
      Preconditions.checkArgument(code >= 500, "min server code is 500");
    }

    @Override
    public WebApplicationServerException exception(Throwable cause) {
      checkNotNull(
          cause,
          "optional argument \"cause\" was unexpectedly null."); // surely null is impossible in a
      // catch block!
      logLevel.logTo(
          LOGGER,
          String.format("message=\"%s\" status=\"%d\"", getMessage(), getStatusCode()),
          cause);
      return new WebApplicationServerException(cause, response());
    }

    @Override
    public WebApplicationServerException exception() {
      logLevel.logTo(
          LOGGER, String.format("message=\"%s\" status=\"%d\"", getMessage(), getStatusCode()));
      return new WebApplicationServerException(null, response());
    }
  }
}
