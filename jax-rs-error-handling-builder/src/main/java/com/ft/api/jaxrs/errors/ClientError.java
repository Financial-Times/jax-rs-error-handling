package com.ft.api.jaxrs.errors;

import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ClientError
 *
 * @author Simon.Gibbs
 */
public class ClientError {

    public static ClientErrorBuilder status(int code) {
        return new ClientErrorBuilder(code);
    }

    /**
     * Provides a standardised way of translating business layer exceptions into web layer exceptions
     * compatible with the JAX-RS standard.
     *
     * @author Simon.Gibbs
     */
    public static class ClientErrorBuilder extends AbstractErrorBuilder<WebApplicationClientException,ClientErrorBuilder > {

        public ClientErrorBuilder(int statusCode) {
            super(statusCode);
        }

        @Override
        protected void checkStatusCode(int code) {
            Preconditions.checkArgument(code<=499,"max client code is 499");
            Preconditions.checkArgument(code>=400,"min client code is 400");
        }

        @Override
        public WebApplicationClientException exception(Throwable cause) {
            checkNotNull(cause, "optional argument \"cause\" was unexpectedly null."); // surely null is impossible in a catch block!
            return new WebApplicationClientException(cause, response());
        }

        @Override
        public WebApplicationClientException exception() {
            return new WebApplicationClientException(response());
        }


    }
}
