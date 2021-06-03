package com.ft.api.jaxrs.client.exceptions;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.SocketTimeoutException;
import java.net.URI;
import org.junit.Test;

/**
 * ApiNetworkingExceptionTest
 *
 * @author Simon.Gibbs
 */
public class ApiNetworkingExceptionTest {

  public static final String EXAMPLE_URI = "http://example.com";
  public static final String EXAMPLE_VERB = "GET";

  @Test
  public void shouldAlwaysHaveMessage() {
    ApiNetworkingException objectInTest = buildPlainExampleException();
    assertNotNull(objectInTest.getMessage());
    assertUriAndVerb(objectInTest);
  }

  private void assertUriAndVerb(ApiNetworkingException objectInTest) {
    assertThat(objectInTest.getHttpMethod(), equalTo(EXAMPLE_VERB));
    assertThat(objectInTest.getUri(), equalTo(exampleUri()));
  }

  @Test
  public void shouldAlwaysHaveMessageGivenAnException() {
    ApiNetworkingException objectInTest = buildExampleException();
    assertNotNull(objectInTest.getMessage());
    assertUriAndVerb(objectInTest);
  }

  @Test
  public void shouldEchoUriInMessage() {
    ApiNetworkingException objectInTest = buildPlainExampleException();
    assertTrue(objectInTest.getMessage().contains(EXAMPLE_URI));
    assertUriAndVerb(objectInTest);
  }

  @Test
  public void shouldEchoUriInMessageGivenAnException() {
    ApiNetworkingException objectInTest = buildExampleException();
    assertTrue(objectInTest.getMessage().contains(EXAMPLE_URI));
    assertUriAndVerb(objectInTest);
  }

  @Test
  public void shouldContainCause() {
    ApiNetworkingException objectInTest = buildExampleException();
    assertNotNull(objectInTest.getCause());
    assertUriAndVerb(objectInTest);
  }

  private ApiNetworkingException buildExampleException() {
    SocketTimeoutException exampleException = new SocketTimeoutException();

    return new ApiNetworkingException(exampleUri(), EXAMPLE_VERB, exampleException);
  }

  private URI exampleUri() {
    return URI.create(EXAMPLE_URI);
  }

  private ApiNetworkingException buildPlainExampleException() {

    return new ApiNetworkingException(exampleUri(), "GET");
  }
}
