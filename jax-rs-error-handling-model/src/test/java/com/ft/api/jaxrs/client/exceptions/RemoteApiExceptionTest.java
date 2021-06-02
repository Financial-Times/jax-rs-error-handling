package com.ft.api.jaxrs.client.exceptions;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ft.api.jaxrs.errors.ErrorEntity;
import java.net.URI;
import org.junit.Test;

/**
 * RemoteApiExceptionTest
 *
 * @author Simon.Gibbs
 */
public class RemoteApiExceptionTest {

  public static final String EXAMPLE_URI = "http://example.com";
  private static final String EXAMPLE_MESSAGE = "expected message";

  @Test
  public void shouldUseMessageFromServerWhenAvailable() {
    RemoteApiException example =
        new RemoteApiException(exampleUri(), "GET", 500, new ErrorEntity(EXAMPLE_MESSAGE));

    assertThat(example.getMessage(), equalTo(EXAMPLE_MESSAGE));
  }

  @Test
  public void shouldHaveAMessageEvenWhenServerMessageIsMissing() {
    RemoteApiException example =
        new RemoteApiException(exampleUri(), "GET", 500, new ErrorEntity(null));

    assertThat(example.getMessage(), notNullValue());
  }

  @Test
  public void shouldHaveAMessageEvenWhenServerMessageIsNotSupplied() {
    RemoteApiException example = new RemoteApiException(exampleUri(), "GET", 500);

    assertThat(example.getMessage(), notNullValue());
  }

  private URI exampleUri() {
    return URI.create(EXAMPLE_URI);
  }
}
