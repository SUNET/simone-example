package se.uhr.simone.restbucks.boundary;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@ApplicationPath("/")
@OpenAPIDefinition(info = @Info(title = "Example application", version = "1.0.0"))
public class SimulatorApplication extends Application {

}
