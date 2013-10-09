package com.ft.api.jaxrs.client.exceptions;

import org.junit.Test;

import java.net.SocketTimeoutException;
import java.net.URI;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * ApiNetworkingExceptionTest
 *
 * @author Simon.Gibbs
 */
public class ApiNetworkingExceptionTest {

    public static final String EXAMPLE_URI = "http://example.com";

    @Test
    public void shouldAlwaysHaveMessage() {
        ApiNetworkingException objectInTest = buildExampleException();
        assertNotNull(objectInTest.getMessage());
    }

    @Test
    public void shouldEchoUriInMessage() {
        ApiNetworkingException objectInTest = buildExampleException();
        assertTrue(objectInTest.getMessage().contains(EXAMPLE_URI));
    }

    @Test
    public void shouldContainCause() {
        ApiNetworkingException objectInTest = buildExampleException();
        assertNotNull(objectInTest.getCause());
    }


    private ApiNetworkingException buildExampleException() {
        SocketTimeoutException exampleException = new SocketTimeoutException();
        URI exampleUri = URI.create(EXAMPLE_URI);

        return new ApiNetworkingException(exampleUri,"GET",exampleException);
    }

}
