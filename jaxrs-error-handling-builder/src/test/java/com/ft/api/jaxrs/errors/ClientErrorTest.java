package com.ft.api.jaxrs.errors;

import org.junit.Test;
import org.slf4j.Logger;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * ClientErrorTest
 *
 * @author Simon.Gibbs
 */
public class ClientErrorTest {

    @Test
    public void shouldDefaultResponseToJsonMediaType() {
        Response result = ClientError.status(400).response();
        assertThat((MediaType) result.getMetadata().getFirst(HttpHeaders.CONTENT_TYPE),is(MediaType.APPLICATION_JSON_TYPE));
    }

    @Test
    public void shouldIncludeSelectedStatusInResponse() {
        Response result = ClientError.status(401).response();
        assertThat(result.getStatus(),is(401));
    }

    @Test
    public void shouldBeAbleToWrapResponseWithAnException() {
        WebApplicationClientException clientException = ClientError.status(402).exception();
        assertThat(clientException.getResponse().getStatus(),is(402));
    }

    @Test
    public void shouldReturnAnEntityOfTypeResourceError() {
        Response result = ClientError.status(403).response();
        assertThat(result.getEntity(),instanceOf(ErrorEntity.class));
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullForOptionalException() {
        ClientError.status(402).exception(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullForMessage() {
        ClientError.status(402).error(null).response();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectStatusCode500() {
        int badCode = 500;
        ClientError.status(badCode).response();
    }

    @Test
    public void shouldAcceptStatusCode400() {
        int goodCode = 400;
        Response result = ClientError.status(goodCode).response();
        assertNotNull(result);
    }

    @Test
    public void shouldAcceptStatusCode499() {
        int goodCode = 499;
        Response result = ClientError.status(goodCode).response();
        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectStatusCode399() {
        int badCode = 399;
        ClientError.status(badCode).response();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectStatusCodeAboveRangeAllocatedInRFC() {
        int badCode = 500 + anyNonNegativeInteger();
        ClientError.status(badCode).response();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectStatusCodeBelowRangeAllocatedInRFC() {
        int badCode = (399 - anyNonNegativeInteger());
        ClientError.status(badCode).response();
    }

    @Test
    public void shouldRespectLogLevel() throws Exception {

        final LogLevel mockLevel = mock(LogLevel.class);

        ClientError.status(400).error("mock failure").logLevel(mockLevel).exception();

        verify(mockLevel,times(1)).logTo(any(Logger.class),anyString(),anyString(),anyInt());

    }


    @Test
    public void shouldRespectLogLevelWithCause() throws Exception {

        final LogLevel mockLevel = mock(LogLevel.class);

        ClientError.status(400).error("mock failure").logLevel(mockLevel).exception(new RuntimeException("Synthetic error"));

        verify(mockLevel,times(1)).logTo(any(Logger.class), anyString(), any(Exception.class));

    }


    private int anyNonNegativeInteger() {
        return (int) (Math.random()* Integer.MAX_VALUE);
    }
}
