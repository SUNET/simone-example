package se.uhr.simone.restbucks.control;

import java.util.concurrent.Semaphore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.uhr.simone.core.SimOne;

@ApplicationScoped
public class RestBucksScheduler {
	private static final Logger LOG = LoggerFactory.getLogger(RestBucksScheduler.class);

	private final Semaphore semaphore = new Semaphore(1);

	@Inject
	SimOne simOne;

	@Scheduled(every = "{worker.schedule.expr}")
	void scheduled() {
		if (semaphore.tryAcquire()) {
			try {
				simOne.connectEntrysToFeeds();
				simOne.createXmlForFeeds();
			} catch (Exception e) {
				LOG.error("timer event error", e);
			} finally {
				semaphore.release();
			}
		}
	}
}
