package se.uhr.simone.restbucks.boundary;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderRepresentation {

	private String id;
	
	private String description;
	
	@XmlJavaTypeAdapter(value = LocalDateTimeAdaptor.class)
	private LocalDateTime time;
	
	public OrderRepresentation() {
		
	}
	
	public static OrderRepresentation of(String id, String description, LocalDateTime time) {
		return new OrderRepresentation(id, description, time);
	}

	private OrderRepresentation(String id, String description, LocalDateTime time) {
		this.id = id;
		this.description = description;
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
