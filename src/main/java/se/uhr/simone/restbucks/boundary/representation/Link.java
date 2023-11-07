package se.uhr.simone.restbucks.boundary.representation;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Link {

	public String rel;
	public String href;
	public String type;

	public Link() {
	}

	public Link(String rel, String href, String type) {
		this.rel = rel;
		this.href = href;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Link[" + "rel=" + rel + ", " + "href=" + href + ", " + "type=" + type + ']';
	}
}
