package se.uhr.simone.restbucks.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import se.uhr.simone.core.feed.control.UniqueIdentifier;
import se.uhr.simone.restbucks.boundary.representation.OrderRepresentation;

@ApplicationScoped
public class OrderRepositoryMap implements OrderRepository {

	private final ConcurrentMap<UUID, OrderRepresentation> store = new ConcurrentHashMap<>();

	private final int maxEntries;

	@Inject
	public OrderRepositoryMap(@ConfigProperty(name = "simone.example.db.max.entries", defaultValue = "10000") Integer maxEntries) {
		this.maxEntries = maxEntries;
	}

	@Override
	public void put(UniqueIdentifier orderId, OrderRepresentation order) {
		if (store.size() > maxEntries - 1) {
			throw new RepositoryException("Order repository has reached its maximum capacity: " + maxEntries + " entries");
		}

		store.put(orderId.getUuid(), order);
	}

	@Override
	public OrderRepresentation get(UniqueIdentifier id) {
		return store.get(id.getUuid());
	}

	@Override
	public List<OrderRepresentation> getAll() {
		return new ArrayList<>(store.values());
	}

	@Gauge(unit = MetricUnits.NONE, name = "order.db.size", absolute = true, description = "Number of entries")
	public int size() {
		return store.size();
	}

	public void clear() {
		store.clear();
	}

	public void dropTables() {
		store.clear();
	}
}
