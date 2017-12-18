package se.uhr.simone.restbucks.entity;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import net.openhft.chronicle.map.ChronicleMap;
import se.uhr.simone.example.api.OrderRepresentation;
import se.uhr.simone.extension.api.entity.DatabaseAdmin;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;

@ApplicationScoped
public class OrderRepository implements DatabaseAdmin {

	private final ChronicleMap<UUID, OrderRepresentation> store;

	public OrderRepository() throws IOException {
		store = ChronicleMap.of(UUID.class, OrderRepresentation.class)
				.name("restbucks")
				.constantKeySizeBySample(UUID.randomUUID())
				.averageValue(OrderRepresentation.builder()
						.withId(UniqueIdentifier.randomUniqueIdentifier().toString())
						.withDescription(new String(new char[100]))
						.withTime(Instant.ofEpochSecond(0))
						.build())
				.entries(10_000)
				.createPersistedTo(getStorage());
	}

	static File getStorage() {
		return new File(System.getProperty("se.uhr.simone.example.db.home", "restbucks"));
	}

	public void put(UniqueIdentifier orderId, OrderRepresentation order) {
		store.put(orderId.getUuid(), order);
	}

	public OrderRepresentation get(UniqueIdentifier id) {
		return store.get(id.getUuid());
	}

	public List<OrderRepresentation> getAll() {
		return new ArrayList<>(store.values());
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
