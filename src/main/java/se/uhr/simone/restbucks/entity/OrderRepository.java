package se.uhr.simone.restbucks.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import net.openhft.chronicle.map.ChronicleMap;
import se.uhr.simone.extension.api.Constants;
import se.uhr.simone.extension.api.entity.DatabaseAdmin;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;
import se.uhr.simone.restbucks.boundary.OrderRepresentation;

@ApplicationScoped
public class OrderRepository implements DatabaseAdmin {

	private static final String DB_NAME = "order";

	private static final File DB_FILE = new File(Constants.DB_DIRECTORY, DB_NAME);

	private final ChronicleMap<String, OrderRepresentation> store;

	public OrderRepository() throws IOException {
		store = ChronicleMap.of(String.class, OrderRepresentation.class).name(DB_NAME)
				.constantKeySizeBySample(UniqueIdentifier.ZERO_UNIQUE_IDENTIFIER.toString()).averageValueSize(200)
				.entries(50_000).createPersistedTo(DB_FILE);
	}

	public void create(UniqueIdentifier uuid, OrderRepresentation order) {
		store.put(uuid.toString(), order);
	}

	public OrderRepresentation get(UniqueIdentifier uuid) {
		return store.get(uuid.toString());
	}

	public List<OrderRepresentation> getAll() {
		return new ArrayList<>(store.values());
	}

	@Override
	public void dropTables() {
		store.clear();
	}

	@PreDestroy
	public void close() {
		store.close();
	}
}
