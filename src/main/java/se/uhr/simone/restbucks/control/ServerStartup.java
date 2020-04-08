package se.uhr.simone.restbucks.control;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.runtime.StartupEvent;
import se.uhr.simone.extension.api.SimoneStartupEvent;

@ApplicationScoped
public class ServerStartup {

	@Inject
	private Event<SimoneStartupEvent> startupEvent;

	void onStart(@Observes StartupEvent ev) {
		startupEvent.fire(new SimoneStartupEvent());
	}
}
