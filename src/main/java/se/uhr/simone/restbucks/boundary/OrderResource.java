package se.uhr.simone.restbucks.boundary;

import java.util.List;

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
import javax.ws.rs.core.UriBuilder;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import se.uhr.simone.core.control.SimoneConfiguration;
import se.uhr.simone.example.api.OrderRepresentation;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;
import se.uhr.simone.restbucks.control.OrderController;

@Tag(name = "order")
@Path("/order")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class OrderResource {

	@Inject
	SimoneConfiguration config;

	@Inject
	OrderController controller;

	@Operation(summary = "Create order")
	@APIResponse(responseCode = "201", description = "The order is created")
	@POST
	public Response create(String description) {
		OrderRepresentation order = controller.create(description);
		return Response.created(UriBuilder.fromUri(config.getBaseRestURI()).segment("order", order.getId()).build()).build();
	}

	@Operation(summary = "Get all orders")
	@APIResponse(responseCode = "200", description = "List of orders", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = OrderRepresentation.class)))
	@GET
	public Response readAll() {
		GenericEntity<List<OrderRepresentation>> orders = new GenericEntity<List<OrderRepresentation>>(controller.getAll()) {
		};

		return Response.ok(orders).build();
	}

	@Operation(summary = "Get specific order")
	@APIResponse(responseCode = "200", description = "On success", content = @Content(schema = @Schema(implementation = OrderRepresentation.class)))
	@APIResponse(responseCode = "404", description = "If the specified order could not be found", content = @Content(schema = @Schema(implementation = OrderRepresentation.class)))
	@GET
	@Path("/{orderId}")
	public Response read(@PathParam("orderId") String orderId) {
		UniqueIdentifier id = UniqueIdentifier.of(orderId);

		OrderRepresentation order = controller.get(id);

		return order != null ? Response.ok(order).build() : Response.status(Status.NOT_FOUND).build();
	}

}
