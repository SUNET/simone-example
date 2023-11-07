package se.uhr.simone.restbucks.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import se.uhr.simone.core.feed.control.UniqueIdentifier;
import se.uhr.simone.restbucks.boundary.representation.OrderRepresentation;

class OrderRepositoryMapTest {

	private static final Instant EPOC = Instant.ofEpochSecond(0);

	private static final UniqueIdentifier ORDER_ID = UniqueIdentifier.randomUniqueIdentifier();

	private static final OrderRepresentation ORDER = create(ORDER_ID);

	@Test
	void shouldPutAndGet() {
		OrderRepositoryMap cut = new OrderRepositoryMap(10);
		cut.put(ORDER_ID, ORDER);
		assertThat(cut.get(ORDER_ID).time).isEqualTo(EPOC);
	}

	@Test
	void shouldReturnAll() {
		OrderRepositoryMap cut = new OrderRepositoryMap(10);

		UniqueIdentifier id1 = UniqueIdentifier.randomUniqueIdentifier();
		cut.put(id1, create(id1));
		UniqueIdentifier id2 = UniqueIdentifier.randomUniqueIdentifier();
		cut.put(id2, create(id2));
		UniqueIdentifier id3 = UniqueIdentifier.randomUniqueIdentifier();
		cut.put(id3, create(id3));
		assertThat(cut.getAll()).hasSize(3);
	}

	@Test
	void shouldDrop() {
		OrderRepositoryMap cut = new OrderRepositoryMap(10);

		UniqueIdentifier id1 = UniqueIdentifier.randomUniqueIdentifier();
		cut.put(id1, create(id1));

		cut.dropTables();

		UniqueIdentifier id2 = UniqueIdentifier.randomUniqueIdentifier();
		cut.put(id2, create(id2));

		assertThat(cut.getAll()).hasSize(1);
	}

	@Test
	void shouldThrowOnMaxCapacity() {
		OrderRepositoryMap cut = new OrderRepositoryMap(2);

		UniqueIdentifier id1 = UniqueIdentifier.randomUniqueIdentifier();
		cut.put(id1, create(id1));
		UniqueIdentifier id2 = UniqueIdentifier.randomUniqueIdentifier();
		cut.put(id2, create(id2));
		UniqueIdentifier id3 = UniqueIdentifier.randomUniqueIdentifier();

		assertThatExceptionOfType(RepositoryException.class).isThrownBy(() -> {
			cut.put(id3, create(id3));
		}).withNoCause();
	}

	static OrderRepresentation create(UniqueIdentifier id) {
		return new OrderRepresentation(ORDER_ID.toString(), "desc", EPOC);
	}
}
