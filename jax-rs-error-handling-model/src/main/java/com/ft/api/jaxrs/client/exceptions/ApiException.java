package com.ft.api.jaxrs.client.exceptions;


import com.google.common.base.MoreObjects;

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
        super(MoreObjects.firstNonNull(message, reportUri(uri)));
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    public ApiException(URI uri, String httpMethod, Throwable cause) {
        super("Exception accessing: " + uri, cause);
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    public ApiException(URI uri, String httpMethod) {
        super(reportUri(uri));
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    private static String reportUri(URI uri) {
        return "Problem accessing: " + uri;
    }

    public URI getUri() {
        return uri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }


    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects
                .toStringHelper(this)
                .add("httpMethod", getMessage())
                .add("uri", uri);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
