package se.uhr.simone.restbucks.entity;

import java.util.List;

import se.uhr.simone.example.api.OrderRepresentation;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;

public interface OrderRepository {

	void put(UniqueIdentifier orderId, OrderRepresentation order);

	OrderRepresentation get(UniqueIdentifier id);

	List<OrderRepresentation> getAll();

}