package se.uhr.simone.restbucks.boundary;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdaptor extends XmlAdapter<String, LocalDateTime> {

	static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("GMT"));

	@Override
	public String marshal(LocalDateTime value) {
		if (value != null) {
			return value.format(DATE_TIME_FORMATTER);
		}
		return null;
	}

	@Override
	public LocalDateTime unmarshal(String s) {
		try {
			return LocalDateTime.parse(s, DATE_TIME_FORMATTER);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
