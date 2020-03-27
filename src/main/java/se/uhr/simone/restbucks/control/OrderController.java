package se.uhr.simone.restbucks.control;

import java.io.StringWriter;
import java.net.URI;
import java.time.Instant;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.uhr.simone.example.api.Link;
import se.uhr.simone.example.api.OrderEventRepresentation;
import se.uhr.simone.example.api.OrderRepresentation;
import se.uhr.simone.extension.api.SimoneProperties;
import se.uhr.simone.extension.api.feed.AtomCategory;
import se.uhr.simone.extension.api.feed.AtomCategory.Label;
import se.uhr.simone.extension.api.feed.AtomCategory.Term;
import se.uhr.simone.extension.api.feed.AtomEntry;
import se.uhr.simone.extension.api.feed.Content;
import se.uhr.simone.extension.api.feed.FeedPublisher;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;
import se.uhr.simone.restbucks.entity.OrderRepository;

@Dependent
public class OrderController {

	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	@Inject
	OrderRepository orderRepository;

	@Inject
	FeedPublisher feedPublisher;

	@Counted(name = "order.placed.count", absolute = true)
	@Timed(name = "order.placed.count.time", absolute = true)
	@Transactional(value = TxType.REQUIRES_NEW)
	public OrderRepresentation create(String description) {

		org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog("init");
		log.info("init");

		UniqueIdentifier orderId = UniqueIdentifier.randomUniqueIdentifier();

		OrderRepresentation order =
				OrderRepresentation.builder().withId(orderId.toString()).withDescription(description).withTime(Instant.now()).build();

		orderRepository.put(orderId, order);

		URI uri = UriBuilder.fromUri(SimoneProperties.getBaseRestURI()).segment("order").segment(orderId.toString()).build();

		Link link = Link.builder().withRel("order").withHref(uri.toString()).withType(MediaType.APPLICATION_JSON).build();
		OrderEventRepresentation event = OrderEventRepresentation.builder().withId(orderId.toString()).withLink(link).build();

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

		feedPublisher.publish(entry);
	}
}
