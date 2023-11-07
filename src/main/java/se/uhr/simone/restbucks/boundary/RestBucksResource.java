package se.uhr.simone.restbucks.boundary;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.Context;
import se.uhr.simone.core.SimOne;
import se.uhr.simone.core.admin.boundary.AdminResource;
import se.uhr.simone.core.feed.boundary.SimulatorFeedResource;

@Path("/")
public class RestBucksResource {

	@Context
	ResourceContext resourceContext;

	@Inject
	SimOne simOne;

	@Path("/feed")
	public SimulatorFeedResource getFeedResource() {
		return resourceContext.initResource(new SimulatorFeedResource(simOne));
	}

	@Path("/admin")
	public AdminResource getAdminResource() {
		return resourceContext.initResource(new AdminResource(simOne));
	}
}
