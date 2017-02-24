package se.uhr.simone.restbucks.control;

import java.io.StringWriter;
import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.uhr.simone.extension.api.feed.AtomCategory;
import se.uhr.simone.extension.api.feed.AtomEntry;
import se.uhr.simone.extension.api.feed.FeedPublisher;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;
import se.uhr.simone.extension.api.feed.AtomCategory.Label;
import se.uhr.simone.extension.api.feed.AtomCategory.Term;
import se.uhr.simone.extension.api.feed.AtomEntry.AtomEntryId;
import se.uhr.simone.restbucks.boundary.OrderRepresentation;
import se.uhr.simone.restbucks.entity.OrderRepository;

public class OrderController {

	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@Inject
	private OrderRepository orderRepository;

	@Inject
	private FeedPublisher feedPublisher;

	public OrderRepresentation create(String description) {
		UniqueIdentifier orderId = UniqueIdentifier.randomUniqueIdentifier();

		OrderRepresentation order = OrderRepresentation.of(orderId.toString(), description, LocalDateTime.now());

		orderRepository.create(orderId, order);

		publishFeedEntry(orderId, convertToXml(order));

		LOG.info("Create order id: {}", orderId.toString());
		
		return order;
	}

	public OrderRepresentation get(UniqueIdentifier id) {
		return orderRepository.get(id);
	}

	public java.util.List<OrderRepresentation> getAll() {
		return orderRepository.getAll();
	}
	
	private String convertToXml(OrderRepresentation order) {
		try {
			JAXBContext context = JAXBContext.newInstance(order.getClass());
			Marshaller m = context.createMarshaller();
			StringWriter outstr = new StringWriter();
			m.marshal(order, outstr);
			return outstr.toString();
		} catch (JAXBException e) {
			throw new OrderException("Can't create event", e);
		}
	}

	private void publishFeedEntry(UniqueIdentifier uid, String content) {
		AtomEntry entry = AtomEntry.builder().withAtomEntryId(AtomEntryId.of(uid, MediaType.APPLICATION_XML))
				.withSubmittedNow().withXml(content)
				.withCategory(AtomCategory.of(Term.of("myterm"), Label.of("mylabel"))).build();

		feedPublisher.publish(entry);
	}
}
