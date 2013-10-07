package com.ft.api.jaxrs.client.exceptions;


import java.net.URI;

/**
 * ApiNetworkingException
 *
 * @author Simon.Gibbs
 */
public class ApiNetworkingException extends ApiException {
    public ApiNetworkingException(URI uri, String httpMethod) {
        super(uri, httpMethod);
    }
}
