package com.ft.api.jaxrs.client.exceptions;

import com.google.common.base.Objects;

import java.net.URI;

/**
 * ApiException
 *
 * @author Simon.Gibbs
 */
public class ApiException extends RuntimeException {

    protected final URI uri;
    protected final String httpMethod;


    public ApiException(String message, URI uri, String httpMethod) {
        super(message);
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    public ApiException(URI uri, String httpMethod, Throwable cause) {
        super("Exception accessing: " + uri, cause);
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    public ApiException(URI uri, String httpMethod) {
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    public URI getUri() {
        return uri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }


    protected Objects.ToStringHelper toStringHelper() {
        return Objects
                .toStringHelper(this)
                .add("httpMethod", getMessage())
                .add("uri", uri);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
