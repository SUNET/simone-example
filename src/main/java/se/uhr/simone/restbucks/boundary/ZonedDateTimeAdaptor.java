package se.uhr.simone.restbucks.boundary;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeAdaptor {

	static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

	public static String marshal(ZonedDateTime value) {
		if (value != null) {
			return value.format(DATE_TIME_FORMATTER);
		}
		return null;
	}

	public static ZonedDateTime unmarshal(String s) {
		try {
			return ZonedDateTime.parse(s, DATE_TIME_FORMATTER);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
