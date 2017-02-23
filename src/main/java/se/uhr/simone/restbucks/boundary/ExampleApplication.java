package se.uhr.simone.restbucks.boundary;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/api")
public class ExampleApplication extends Application {

	public ExampleApplication() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/sim/api");
		beanConfig.setResourcePackage("se.uhr.simone,se.uhr.simone.restbucks");
		beanConfig.setScan(true);
	}
}
