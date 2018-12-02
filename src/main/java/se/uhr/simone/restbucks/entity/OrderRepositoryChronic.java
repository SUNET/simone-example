package se.uhr.simone.restbucks.entity;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import net.openhft.chronicle.map.ChronicleMap;
import se.uhr.simone.example.api.OrderRepresentation;
import se.uhr.simone.extension.api.entity.DatabaseAdmin;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;

@ApplicationScoped
public class OrderRepositoryChronic implements DatabaseAdmin, OrderRepository {

	private final ChronicleMap<UUID, OrderRepresentation> store;

	@Inject
	public OrderRepositoryChronic(@ConfigProperty(name = "se.uhr.simone.example.db.home", defaultValue = "restbucks") Path dbPath)
		throws IOException {

		store = ChronicleMap.of(UUID.class, OrderRepresentation.class)
				.name("restbucks")
				.constantKeySizeBySample(UUID.randomUUID())
				.averageValue(OrderRepresentation.builder()
						.withId(UniqueIdentifier.randomUniqueIdentifier().toString())
						.withDescription(new String(new char[100]))
						.withTime(Instant.ofEpochSecond(0))
						.build())
				.entries(10_000)
				.createPersistedTo(dbPath.toFile());
	}

	@Override
	public void put(UniqueIdentifier orderId, OrderRepresentation order) {
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

	@Override
	public int size() {
		return store.size();
	}

	public void clear() {
		store.clear();
	}

	public void close() {
		store.close();
	}

	@Override
	public void dropTables() {
		store.clear();
	}
}
