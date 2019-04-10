package se.uhr.simone.restbucks.boundary;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InstantAdaptor {

	private InstantAdaptor() {

	}

	public static String marshal(Instant value) {
		if (value != null) {
			return DateTimeFormatter.ISO_INSTANT.format(value);
		}
		return null;
	}

	public static Instant unmarshal(String s) {
		try {
			return Instant.parse(s);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
