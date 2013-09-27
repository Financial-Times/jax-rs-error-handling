package com.ft.api.jaxrs.errors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Thrown when the server attributes the failure of a a HTTP transaction to the client.
 *
 * @author Simon.Gibbs
 */
public class WebApplicationClientException extends WebApplicationException {


}
