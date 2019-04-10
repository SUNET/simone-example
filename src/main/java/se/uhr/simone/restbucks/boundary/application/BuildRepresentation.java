package se.uhr.simone.restbucks.boundary.application;

public class BuildRepresentation {

	private String number;

	private String time;

	public BuildRepresentation() {

	}

	private BuildRepresentation(String number, String time) {
		this.number = number;
		this.time = time;
	}

	public static BuildRepresentation of(String number, String time) {
		return new BuildRepresentation(number, time);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
