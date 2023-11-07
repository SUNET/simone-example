package se.uhr.simone.restbucks.control;

import java.io.StringWriter;
import java.net.URI;
import java.time.Instant;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.uhr.simone.core.feed.control.AtomCategory;
import se.uhr.simone.core.feed.control.AtomCategory.Label;
import se.uhr.simone.core.feed.control.AtomCategory.Term;
import se.uhr.simone.core.feed.control.AtomEntry;
import se.uhr.simone.core.feed.control.Content;
import se.uhr.simone.core.feed.control.UniqueIdentifier;
import se.uhr.simone.core.SimOne;
import se.uhr.simone.restbucks.boundary.representation.Link;
import se.uhr.simone.restbucks.boundary.representation.OrderEventRepresentation;
import se.uhr.simone.restbucks.boundary.representation.OrderRepresentation;
import se.uhr.simone.restbucks.entity.OrderRepository;

@Dependent
public class OrderController {

	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	@Inject
	RestBucksProperties properties;

	@Inject
	OrderRepository orderRepository;

	@Inject
	SimOne simOne;

	@Counted(name = "order.placed.count", absolute = true)
	@Timed(name = "order.placed.count.time", absolute = true)
	@Transactional
	public OrderRepresentation create(String description) {
		UniqueIdentifier orderId = UniqueIdentifier.randomUniqueIdentifier();

		OrderRepresentation order =
				new OrderRepresentation(orderId.toString(), description, Instant.now());

		orderRepository.put(orderId, order);

		URI uri = UriBuilder.fromUri(properties.baseURI).segment("order").segment(orderId.toString()).build();

		Link link = new Link("order", uri.toString(), MediaType.APPLICATION_JSON);
		OrderEventRepresentation event = new OrderEventRepresentation(orderId.toString(), link);

		publishFeedEntry(orderId, convertToXml(event));

		LOG.info("Create order id: {}", orderId);

		return order;
	}

	public OrderRepresentation get(UniqueIdentifier id) {
		return orderRepository.get(id);
	}

	public java.util.List<OrderRepresentation> getAll() {
		return orderRepository.getAll();
	}

	private String convertToXml(OrderEventRepresentation event) {
		try {
			JAXBContext context = JAXBContext.newInstance(event.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter outstr = new StringWriter();
			m.marshal(event, outstr);
			return outstr.toString();
		} catch (JAXBException e) {
			throw new OrderException("Can't create event", e);
		}
	}

	private void publishFeedEntry(UniqueIdentifier uid, String content) {
		AtomEntry entry = AtomEntry.builder()
				.withAtomEntryId(uid.toString())
				.withSubmittedNow()
				.withContent(Content.builder().withValue(content).withContentType(MediaType.APPLICATION_XML).build())
				.withCategory(AtomCategory.builder().withTerm(Term.of("myterm")).withLabel(Label.of("mylabel")).build())
				.build();

		simOne.publish(entry);
	}
}
