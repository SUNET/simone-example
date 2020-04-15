package se.uhr.simone.restbucks.control;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import io.quarkus.scheduler.Scheduled;
import se.uhr.simone.api.SimoneTimerEvent;

@ApplicationScoped
public class WorkerScheduler {

	@Inject
	private Event<SimoneTimerEvent> simoneTimerEvent;

	@Scheduled(every = "{simone.worker.schedule.expr}")
	void onStart() {
		simoneTimerEvent.fire(new SimoneTimerEvent());
	}
}
