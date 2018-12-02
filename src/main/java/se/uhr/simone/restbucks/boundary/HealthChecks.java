package se.uhr.simone.restbucks.boundary;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import se.uhr.simone.restbucks.control.OrderController;

@Health
@ApplicationScoped
public class HealthChecks implements HealthCheck {

	@Inject
	OrderController orderController;

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.named("db").withData("size", Integer.toString(orderController.size())).up().build();

	}
}
