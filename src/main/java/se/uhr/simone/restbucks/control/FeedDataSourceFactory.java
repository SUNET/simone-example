package se.uhr.simone.restbucks.control;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import io.agroal.api.AgroalDataSource;
import se.uhr.simone.core.entity.FeedDS;

@ApplicationScoped
public class FeedDataSourceFactory {

	@Inject
	AgroalDataSource feedDataSource;

	@Produces
	@FeedDS
	public javax.sql.DataSource getFeedDataSource() {
		return feedDataSource;
	}
}
