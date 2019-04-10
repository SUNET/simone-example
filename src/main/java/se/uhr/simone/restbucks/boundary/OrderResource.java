package se.uhr.simone.restbucks.boundary;

import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import se.uhr.simone.example.api.OrderRepresentation;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;
import se.uhr.simone.restbucks.control.OrderController;

@Tag(name = "order")
@Path("order")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Singleton
public class OrderResource {

	@Inject
	private OrderController controller;

	@Operation(summary = "Create order")
	@APIResponse(description = "The order", content = @Content(schema = @Schema(implementation = OrderRepresentation.class)))
	@POST
	public Response create(String description) {
		OrderRepresentation order = controller.create(description);
		return Response.ok(order).build();
	}

	@Operation(summary = "Get all orders")
	@APIResponse(description = "List of orders", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = OrderRepresentation.class)))
	@GET
	public Response readAll() {
		GenericEntity<List<OrderRepresentation>> orders = new GenericEntity<List<OrderRepresentation>>(controller.getAll()) {
		};

		return Response.ok(orders).build();
	}

	@Operation(summary = "Get specific order")
	@APIResponse(description = "The order", content = @Content(schema = @Schema(implementation = OrderRepresentation.class)))
	@GET
	@Path("/{orderId}")
	public Response read(@PathParam("orderId") String orderId) {
		UniqueIdentifier id = UniqueIdentifier.of(orderId);

		OrderRepresentation order = controller.get(id);

		return order != null ? Response.ok(order).build() : Response.status(Status.NOT_FOUND).build();
	}

}
