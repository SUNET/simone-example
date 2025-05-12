package se.uhr.simone.restbucks.boundary;

import java.io.InputStream;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import se.uhr.simone.core.feed.control.UniqueIdentifier;
import se.uhr.simone.core.SimOne;
import se.uhr.simone.core.boundary.Managed;
import se.uhr.simone.restbucks.boundary.representation.OrderRepresentation;
import se.uhr.simone.restbucks.control.OrderFileLoader;
import se.uhr.simone.restbucks.control.RestBucksProperties;
import se.uhr.simone.restbucks.control.OrderController;


@Tag(name = "order")
@Managed("restbucks")
@Path("/order")
@Produces({ MediaType.APPLICATION_JSON })
public class OrderResource {

	@Inject
	SimOne simOne;

	@Inject
	RestBucksProperties config;

	@Inject
	OrderController controller;

	@Inject
	OrderFileLoader orderFileLoader;

	@Operation(summary = "Create order")
	@APIResponse(responseCode = "201", description = "The order is created")
	@Consumes(MediaType.TEXT_PLAIN)
	@POST
	public Response create(String description) {
		OrderRepresentation order = controller.create(description);
		return Response.created(UriBuilder.fromUri(config.baseURI).segment("order", order.id).build()).build();
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

	@Operation(summary = "Create orders from file")
	@APIResponse(responseCode = "200", description = "On success")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@POST
	@Path("/file")
	public Response loadFromFile(@FormParam("file") EntityPart file) {
		var result = orderFileLoader.load(file.getContent());

		return Response.status(result == OrderFileLoader.Result.SUCCESS ? Status.OK : Status.BAD_REQUEST).build();
	}
}
