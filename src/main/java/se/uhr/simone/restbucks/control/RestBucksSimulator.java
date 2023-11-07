package se.uhr.simone.restbucks.control;

import java.net.URI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.UriBuilder;
import io.agroal.api.AgroalDataSource;
import se.uhr.simone.core.SimOne;
import se.uhr.simone.core.feed.entity.DerbyFeedRepository;
import se.uhr.simone.restbucks.entity.OrderRepositoryMap;

@ApplicationScoped
public class RestBucksSimulator {

	@Inject
	AgroalDataSource feedDataSource;

	@Inject
	OrderRepositoryMap orderRepository;

	@Inject
	RestBucksProperties properties;

	@Produces
	@ApplicationScoped
	public SimOne create() {
		var feedRepository = new DerbyFeedRepository(feedDataSource);

		URI feedBaseUri = UriBuilder.fromUri(properties.baseURI).segment("feed").build();

		return SimOne.builder()
				.withName("restbucks")
				.withFeedBaseURI(feedBaseUri)
				.withFeedRepository(feedRepository)
				.withClearDatabaseFunction(() -> orderRepository.clear())
				.build();
	}
}
