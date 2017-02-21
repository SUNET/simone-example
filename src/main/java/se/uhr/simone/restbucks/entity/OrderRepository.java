package se.uhr.simone.restbucks.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import se.uhr.simone.extension.api.entity.DatabaseAdmin;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;
import se.uhr.simone.restbucks.boundary.OrderRepresentation;

@ApplicationScoped
public class OrderRepository implements DatabaseAdmin{

	private final Map<UniqueIdentifier, OrderRepresentation> store = new HashMap<>(); 
	
	public void create(UniqueIdentifier uuid, OrderRepresentation order) {
		store.put(uuid, order);
	}
	
	public OrderRepresentation get(UniqueIdentifier uuid) {
		return store.get(uuid);
	}

	public List<OrderRepresentation> getAll() {
		return new ArrayList<>(store.values());
	}
	
	@Override
	public void dropTables() {
		store.clear();
	}
}
