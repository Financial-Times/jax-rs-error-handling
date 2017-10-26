package com.ft.api.jaxrs.errors;


import com.google.common.base.Supplier;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.SocketTimeoutException;
import java.net.URI;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * RuntimeExceptionMapperTest
 *
 * @author Simon.Gibbs
 */
public class RuntimeExceptionMapperTest {

    @SuppressWarnings("unchecked")
    Supplier<String> mockBusinessLayer = mock(Supplier.class);

    @Rule
    public ResourceTestRule resourceTestRule = ResourceTestRule.builder()
            .addResource(new MockResource(mockBusinessLayer))
            .addProvider(RuntimeExceptionMapper.class)
            .build();

    @Test
    public void shouldConvertUnhandledRuntimeExceptions() {

        when(mockBusinessLayer.get()).thenThrow(new NullPointerException("synthetic NPE"));

        Response clientResponse = invokeGetMockResource();

        assertThat(clientResponse.getStatus(), is(500));
        assertThat(clientResponse.readEntity(ErrorEntity.class), instanceOf(ErrorEntity.class));

    }

    @Test
    public void shouldConvertProcessingExceptionsThatWithSocketTimeoutExceptionCause() {

        when(mockBusinessLayer.get()).thenThrow(new ProcessingException(new SocketTimeoutException()));

        Response clientResponse = invokeGetMockResource();

        assertThat(clientResponse.getStatus(), is(504));
        assertThat(clientResponse.readEntity(ErrorEntity.class), instanceOf(ErrorEntity.class));

    }

    private Response invokeGetMockResource() {
        return resourceTestRule.client().target("/")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
    }

    private Response invokePostMockResource() {
        return resourceTestRule.client().target("/")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json("test"));
    }

    @Test
    public void shouldNotConvertProperlyWrappedExceptions() {

        Response expectedResponse = Response.status(422).entity(new ErrorEntity("some error")).build();
        ErrorEntity expectedEntity = (ErrorEntity) expectedResponse.getEntity();

        when(mockBusinessLayer.get()).thenThrow(new WebApplicationClientException(expectedResponse));

        Response clientResponse = invokeGetMockResource();

        ErrorEntity result = clientResponse.readEntity(ErrorEntity.class);

        assertThat(result.getMessage(), is(expectedEntity.getMessage()));
        assertThat(clientResponse.getStatus(), is(422));

    }

    @Test
    public void shouldNotChangeStatusCodeForMappingFailures() {

        Response clientResponse = invokePostMockResource();

        // not sure what Jersey generates, but it should be "method not allowed".
        assertThat(clientResponse.getStatus(), is(405));
    }

    @Test
    public void shouldHandleNotFoundExceptions() throws Exception {

        when(mockBusinessLayer.get()).thenThrow(new NotFoundException(new URI("/nonexistentresource").toString()));

        Response clientResponse = invokeGetMockResource();

        ErrorEntity result = clientResponse.readEntity(ErrorEntity.class);

        assertThat(result.getMessage(), is("404 Not Found"));
        assertThat(clientResponse.getStatus(), is(404));
    }

    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public static class MockResource {

        Supplier<String> businessLayer;

        public MockResource(Supplier<String> bizLayer) {
            this.businessLayer = bizLayer;
        }

        @GET
        public String getString() {
            return businessLayer.get();
        }
    }

}
