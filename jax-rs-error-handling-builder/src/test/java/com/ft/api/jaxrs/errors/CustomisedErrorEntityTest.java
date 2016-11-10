package com.ft.api.jaxrs.errors;

import com.ft.api.jaxrs.errors.entities.ErrorEntityFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * CustomisedErrorEntityTest
 *
 * @author Simon.Gibbs
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomisedErrorEntityTest {


    /**
     * An arbitrary integer between 400 and 499 - the assigned range for client errors.
     */
    public static final int CLIENT_CODE = 401;

    /**
     * An arbitrary integer between 500 and 599 - the assigned range for server errors.
     */
    private static final int SERVER_CODE = 503;

    @Mock
    ErrorEntityFactory mockFactory;

    @Before
    public void setUp() {
        Errors.customise(mockFactory);
    }

    @After
    public void tearDown() {
        Errors.resetCustomisation();
    }

    @Test
    public void shouldCallEntityFactoryToPrepareServerErrorEntity() {

        givenFactoryWillReturnAnErrorEntity();

        ServerError.status(SERVER_CODE).response();

        verify(mockFactory).entity(anyString(),any());
    }


    @Test
    public void shouldCallEntityFactoryToPrepareClientErrorEntity() {

        givenFactoryWillReturnAnErrorEntity();

        ClientError.status(CLIENT_CODE).response();

        verify(mockFactory).entity(anyString(),any());

    }

    @Test
    public void shouldAllowResetBackToDefault() {

        Errors.resetCustomisation();

        ServerError.status(SERVER_CODE).response();

        verify(mockFactory,never()).entity(anyString(),any());

    }

    @Test
    public void entityFactoryShouldReceiveContextFromBuilder() {
        givenFactoryWillReturnAnErrorEntity();

        Object someContext = new Object();

        ClientError.status(CLIENT_CODE).context(someContext).response();

        verify(mockFactory).entity(anyString(),same(someContext));
    }

    @Test
    public void entityFactoryShouldReceiveMessageFromBuilder() {
        givenFactoryWillReturnAnErrorEntity();

        String message = "some message";

        ServerError.status(SERVER_CODE).error(message).response();

        verify(mockFactory).entity(same(message),any());
    }


    @Test(expected = NullPointerException.class)
    public void entityFactoryMustProduceAnEntity() {
        givenFactoryWillFailToReturnAnErrorEntity();

        ClientError.status(CLIENT_CODE).response();

    }

    private void givenFactoryWillReturnAnErrorEntity() {
        when(mockFactory.entity(anyString(), any())).thenReturn(new ErrorEntity("some error"));
    }

    private void givenFactoryWillFailToReturnAnErrorEntity() {
        when(mockFactory.entity(anyString(),any())).thenReturn(null);
    }

}
