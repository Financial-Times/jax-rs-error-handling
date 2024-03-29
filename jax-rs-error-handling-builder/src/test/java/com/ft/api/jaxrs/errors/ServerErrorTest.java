package com.ft.api.jaxrs.errors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Test;
import org.slf4j.Logger;

/**
 * ServerErrorTest
 *
 * @author Simon.Gibbs
 */
public class ServerErrorTest {

  @Test
  public void shouldDefaultResponseToJsonMediaType() {
    Response result = ServerError.status(500).response();
    assertThat(
        (MediaType) result.getMetadata().getFirst(HttpHeaders.CONTENT_TYPE),
        is(MediaType.APPLICATION_JSON_TYPE));
  }

  @Test
  public void shouldIncludeSelectedStatusInResponse() {
    Response result = ServerError.status(503).response();
    assertThat(result.getStatus(), is(503));
  }

  @Test
  public void shouldBeAbleToWrapResponseWithAnException() {
    WebApplicationServerException serverException = ServerError.status(502).exception();
    assertThat(serverException.getResponse().getStatus(), is(502));
  }

  @Test
  public void shouldReturnAnEntityOfTypeResourceError() {
    Response result = ServerError.status(503).response();
    assertThat(result.getEntity(), instanceOf(ErrorEntity.class));
  }

  @Test(expected = NullPointerException.class)
  public void shouldNotAcceptNullForOptionalException() {
    ServerError.status(502).exception(null);
  }

  @Test(expected = NullPointerException.class)
  public void shouldNotAcceptNullForMessage() {
    ServerError.status(502).error(null).response();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectStatusCode600() {
    int badCode = 600;
    ServerError.status(badCode).response();
  }

  @Test
  public void shouldAcceptStatusCode500() {
    int goodCode = 500;
    Response result = ServerError.status(goodCode).response();
    assertNotNull(result);
  }

  @Test
  public void shouldAcceptStatusCode599() {
    int goodCode = 599;
    Response result = ServerError.status(goodCode).response();
    assertNotNull(result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectStatusCode499() {
    int badCode = 499;
    ServerError.status(badCode).response();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectStatusCodeAboveRangeAllocatedInRFC() {
    int badCode = 600 + anyNonNegativeInteger();
    ServerError.status(badCode).response();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRejectStatusCodeBelowRangeAllocatedInRFC() {
    int badCode = (499 - anyNonNegativeInteger());
    ServerError.status(badCode).response();
  }

  @Test
  public void shouldRespectLogLevel() {
    final LogLevel mockLevel = mock(LogLevel.class);
    ServerError.status(500).error("mock failure").logLevel(mockLevel).exception();
    verify(mockLevel, times(1)).logTo(any(Logger.class), anyString());
  }

  @Test
  public void shouldRespectLogLevelWithCause() {
    final LogLevel mockLevel = mock(LogLevel.class);

    ServerError.status(500)
        .error("mock failure")
        .logLevel(mockLevel)
        .exception(new RuntimeException("Synthetic error"));

    verify(mockLevel, times(1)).logTo(any(Logger.class), anyString(), any(Exception.class));
  }

  private int anyNonNegativeInteger() {
    return (int) (Math.random() * Integer.MAX_VALUE);
  }
}
