package se.uhr.simone.restbucks.boundary.application;

public class VersionRepresentation {

	private String version;

	public VersionRepresentation() {

	}

	private VersionRepresentation(String version) {
		this.version = version;
	}

	public static VersionRepresentation of(String version) {
		return new VersionRepresentation(version);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
