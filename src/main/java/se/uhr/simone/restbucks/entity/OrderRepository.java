package se.uhr.simone.restbucks.entity;


import javax.enterprise.context.ApplicationScoped;

import se.uhr.simone.restbucks.boundary.OrderRepresentation;

@ApplicationScoped
public class OrderRepository extends DocumentStore<OrderRepresentation> {

	public OrderRepository() {
		super("COFFEE_ORDER", OrderRepresentation.class);
	}
}
