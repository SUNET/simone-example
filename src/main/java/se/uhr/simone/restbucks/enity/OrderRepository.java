package se.uhr.simone.restbucks.enity;

import java.util.HashMap;
import java.util.Map;

import se.uhr.nya.integration.sim.extension.api.feed.UniqueIdentifier;
import se.uhr.simone.restbucks.boundary.OrderRepresentation;

public class OrderRepository {

	private final Map<UniqueIdentifier, OrderRepresentation> store = new HashMap<>(); 
	
	public void create(UniqueIdentifier uuid, OrderRepresentation order) {
		store.put(uuid, order);
	}
	
	public OrderRepresentation read(UniqueIdentifier uuid) {
		return store.get(uuid);
	}
}
