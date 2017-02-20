package se.uhr.simone.restbucks.boundary;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/api")
public class RestbucksApplication extends Application {

	public RestbucksApplication() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/sim");
		beanConfig.setResourcePackage("se.uhr.simone.restbucks.boundary,se.uhr.nya.integration.sim.server");
		beanConfig.setScan(true);
	}
}
