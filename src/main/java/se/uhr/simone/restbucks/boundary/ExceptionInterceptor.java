package se.uhr.simone.restbucks.boundary;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionInterceptor implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		return Response.serverError().entity(exception.getMessage()).build();		
	}

}
