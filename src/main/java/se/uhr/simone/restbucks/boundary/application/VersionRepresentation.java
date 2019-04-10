package se.uhr.simone.restbucks.boundary.application;

public class VersionRepresentation {

	private String implementation;

	private String scm;

	private BuildRepresentation build;

	public VersionRepresentation() {

	}

	private VersionRepresentation(String implementation, String scm, BuildRepresentation build) {
		this.implementation = implementation;
		this.scm = scm;
		this.build = build;
	}

	public static VersionRepresentation of(String implVersion, String scmVersion, BuildRepresentation build) {
		return new VersionRepresentation(implVersion, scmVersion, build);
	}

	public String getImplementation() {
		return implementation;
	}

	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}

	public String getScm() {
		return scm;
	}

	public void setScm(String scm) {
		this.scm = scm;
	}

	public BuildRepresentation getBuild() {
		return build;
	}

	public void setBuild(BuildRepresentation build) {
		this.build = build;
	}
}
