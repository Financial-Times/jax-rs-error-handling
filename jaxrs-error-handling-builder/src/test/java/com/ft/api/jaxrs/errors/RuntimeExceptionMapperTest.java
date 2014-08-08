package com.ft.api.jaxrs.errors;


import com.google.common.base.Supplier;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

        ClientResponse clientResponse = invokeGetMockResource();

        assertThat(clientResponse.getStatus(),is(500));
        assertThat(clientResponse.getEntity(ErrorEntity.class),instanceOf(ErrorEntity.class));

    }

    private ClientResponse invokeGetMockResource() {
        return prepClientWithUrl()
                .get(ClientResponse.class);
    }

    private ClientResponse invokePostMockResource() {
        return prepClientWithUrl()
                .post(ClientResponse.class);
    }

    private WebResource.Builder prepClientWithUrl() {
        return resourceTestRule.client()
                .resource("/")
                .accept(MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void shouldNotConvertProperlyWrappedExceptions() {

        Response expectedResponse = ClientError.status(422).error("some error").response();
        ErrorEntity expectedEntity = (ErrorEntity) expectedResponse.getEntity();

        when(mockBusinessLayer.get()).thenThrow(new WebApplicationClientException(expectedResponse));

        ClientResponse clientResponse = invokeGetMockResource();

        ErrorEntity result = clientResponse.getEntity(ErrorEntity.class);

        assertThat(result.getMessage(),is(expectedEntity.getMessage()));
        assertThat(clientResponse.getStatus(),is(422));

    }

    @Test
    public void shouldNotChangeStatusCodeForMappingFailures() {

        ClientResponse clientResponse = invokePostMockResource();

        // not sure what Jersey generates, but it should be "method not allowed".
        assertThat(clientResponse.getStatus(),is(405));
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
