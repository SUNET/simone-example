package se.uhr.simone.restbucks.entity;

import java.util.List;

import se.uhr.simone.api.feed.UniqueIdentifier;
import se.uhr.simone.example.api.OrderRepresentation;

public interface OrderRepository {

	void put(UniqueIdentifier orderId, OrderRepresentation order);

	OrderRepresentation get(UniqueIdentifier id);

	List<OrderRepresentation> getAll();

}