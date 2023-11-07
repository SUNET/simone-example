package se.uhr.simone.restbucks.entity;

import java.util.List;

import se.uhr.simone.core.feed.control.UniqueIdentifier;
import se.uhr.simone.restbucks.boundary.representation.OrderRepresentation;

public interface OrderRepository {

	void put(UniqueIdentifier orderId, OrderRepresentation order);

	OrderRepresentation get(UniqueIdentifier id);

	List<OrderRepresentation> getAll();

}