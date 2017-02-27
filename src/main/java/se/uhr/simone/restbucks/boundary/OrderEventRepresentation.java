package se.uhr.simone.restbucks.boundary;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import se.uhr.simone.extension.api.feed.UniqueIdentifier;

@XmlRootElement(name = "orderEvent")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderEventRepresentation {

	private String id;
	
	private Link link;

	public OrderEventRepresentation() {
		
	}
	
	private OrderEventRepresentation(UniqueIdentifier id, URI uri) {
		this.id = id.toString();
		this.link = Link.of("order", uri, MediaType.APPLICATION_JSON);
	}
	
	public static OrderEventRepresentation of(UniqueIdentifier id, URI uri) { 
		return new OrderEventRepresentation(id, uri);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

}
