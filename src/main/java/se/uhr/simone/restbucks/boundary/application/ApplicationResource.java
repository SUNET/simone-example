package se.uhr.simone.restbucks.boundary.application;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import se.uhr.simone.restbucks.control.application.ApplicationManifest;

@Tag(name = "application")
@Path("/application")
@Produces({ MediaType.APPLICATION_JSON })
public class ApplicationResource {

	@Operation(summary = "Information about the application")
	@APIResponse(description = "Version information", content = @Content(schema = @Schema(implementation = VersionRepresentation.class)))
	@GET
	@Path("/version")
	public Response version() {

		InputStream manifestStream = ApplicationResource.class.getResourceAsStream("/META-INF/MANIFEST.MF");

		try {

			ApplicationManifest manifest = new ApplicationManifest(manifestStream);

			VersionRepresentation version = VersionRepresentation.of(manifest.getImplementationVersion(),
					manifest.getImplementationSCMVersion(), BuildRepresentation.of(manifest.getBuildNumber(), manifest.getBuildTime()));
			return Response.ok(version).build();

		} catch (IOException e) {
			throw new InternalServerErrorException(e.getMessage(), e);
		}
	}
}
