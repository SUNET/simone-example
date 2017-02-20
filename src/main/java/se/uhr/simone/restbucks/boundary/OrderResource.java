package se.uhr.simone.restbucks.boundary;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import se.uhr.nya.integration.sim.extension.api.feed.UniqueIdentifier;
import se.uhr.simone.restbucks.control.OrderController;

@Path("order")
@Singleton
public class OrderResource {

	@Inject
	private OrderController controller;
	
	@POST
	public Response create(String description) {
		OrderRepresentation order = controller.create(description);
		return Response.ok(order).build();
	}
	

	@GET
	@Path("/{orderId}")
	public Response read(@PathParam("orderId") String orderId) {
		UniqueIdentifier id = UniqueIdentifier.of(orderId);
		
		OrderRepresentation order = controller.read(id);
		
		return order != null ? Response.ok(order).build() : Response.status(Status.NOT_FOUND).build();
	}
}
