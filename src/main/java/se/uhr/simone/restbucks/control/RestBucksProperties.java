package se.uhr.simone.restbucks.control;

import java.net.URI;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Dependent
public class RestBucksProperties {

	@Inject
	@ConfigProperty(name = "base.uri", defaultValue = "http://localhost:8080")
	public URI baseURI;

}
