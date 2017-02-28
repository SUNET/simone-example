package se.uhr.simone.restbucks.entity;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

@ApplicationScoped
public class RestbucksDataSourceFactory {

	@Resource(mappedName = "java:/jdbc/Restbucks")
	private DataSource ds;

	@Produces
	@RestbucksDS
	public DataSource getFeedDataSource() {
		return ds;
	}
}
