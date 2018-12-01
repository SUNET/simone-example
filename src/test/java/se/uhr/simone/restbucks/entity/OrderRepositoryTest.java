package se.uhr.simone.restbucks.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.uhr.simone.example.api.OrderRepresentation;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;

public class OrderRepositoryTest {

	private final OrderRepository cut;

	private static final Instant EPOC = Instant.ofEpochSecond(0);

	private static final UniqueIdentifier ORDER_ID = UniqueIdentifier.randomUniqueIdentifier();

	private static final OrderRepresentation ORDER = create(ORDER_ID);

	public OrderRepositoryTest() throws IOException {
		cut = new OrderRepository();
	}

	@BeforeEach
	public void before() throws IOException {
		cut.clear();
	}

	@Test
	public void shouldPutAndGet() {
		cut.put(ORDER_ID, ORDER);
		assertThat(cut.get(ORDER_ID).getTime()).isEqualTo(EPOC);
	}

	@Test
	public void shouldReturnAll() {
		UniqueIdentifier id1 = UniqueIdentifier.randomUniqueIdentifier();
		cut.put(id1, create(id1));
		UniqueIdentifier id2 = UniqueIdentifier.randomUniqueIdentifier();
		cut.put(id2, create(id2));
		UniqueIdentifier id3 = UniqueIdentifier.randomUniqueIdentifier();
		cut.put(id3, create(id3));
		assertThat(cut.getAll()).hasSize(3);
	}

	@Test
	public void shouldReadFromDisk() throws IOException {
		cut.put(ORDER_ID, ORDER);
		assertThat(cut.get(ORDER_ID).getTime()).isEqualTo(EPOC);

		OrderRepository tmp = new OrderRepository();

		assertThat(tmp.get(ORDER_ID).getTime()).isEqualTo(EPOC);

	}

	static OrderRepresentation create(UniqueIdentifier id) {
		return OrderRepresentation.builder().withId(ORDER_ID.toString()).withDescription("desc").withTime(EPOC).build();
	}

}
