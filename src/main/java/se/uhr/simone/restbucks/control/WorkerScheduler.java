package se.uhr.simone.restbucks.control;

import java.util.concurrent.Semaphore;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.scheduler.Scheduled;
import se.uhr.simone.api.SimoneTimerEvent;

@ApplicationScoped
public class WorkerScheduler {

	private final static Logger LOG = LoggerFactory.getLogger(WorkerScheduler.class);

	@Inject
	private Event<SimoneTimerEvent> simoneTimerEvent;

	private final Semaphore semaphore = new Semaphore(1);

	@Scheduled(every = "{simone.worker.schedule.expr}")
	void onStart() {
		if (semaphore.tryAcquire()) {
			simoneTimerEvent.fire(new SimoneTimerEvent());
			semaphore.release();
		} else {
			LOG.trace("could not acquire permit");
		}
	}
}
