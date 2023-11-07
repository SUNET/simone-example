package se.uhr.simone.restbucks.boundary.representation;

import java.time.Instant;
import java.util.Objects;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class OrderRepresentation {

	public String id;
	public String description;
	public Instant time;

	public OrderRepresentation() {
	}

	public OrderRepresentation(String id, String description, Instant time) {
		this.id = id;
		this.description = description;
		this.time = time;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (OrderRepresentation) obj;
		return Objects.equals(this.id, that.id) && Objects.equals(this.description, that.description) && Objects.equals(this.time, that.time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, time);
	}

	@Override
	public String toString() {
		return "OrderRepresentation[" + "id=" + id + ", " + "description=" + description + ", " + "time=" + time + ']';
	}

}
