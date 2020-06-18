package se.uhr.simone.restbucks.boundary.application;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "application")
@Path("/application")
@Produces({ MediaType.APPLICATION_JSON })
public class ApplicationResource {

	@ConfigProperty(name = "quarkus.application.version")
	String applicationVersion;

	@Operation(summary = "Information about the application")
	@APIResponse(description = "Version information", content = @Content(schema = @Schema(implementation = VersionRepresentation.class)))
	@GET
	@Path("/version")
	public Response version() {
		VersionRepresentation version = VersionRepresentation.of(applicationVersion);
		return Response.ok(version).build();
	}
}
