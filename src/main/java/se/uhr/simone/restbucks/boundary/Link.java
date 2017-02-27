package se.uhr.simone.restbucks.boundary;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "link")
@XmlAccessorType(value = XmlAccessType.NONE)
public class Link {

	@XmlAttribute
	private String rel;
	
	@XmlAttribute
	private String href;
	
	@XmlAttribute
	private String type;

	public Link() {
		
	}
	
	public static Link of(String rel, URI href, String type) {
		return new Link(rel, href, type);
	}
	
	private Link(String rel, URI href, String type) {
		this.rel = rel;
		this.href = href.toString();
		this.type = type;
	}
	
	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
