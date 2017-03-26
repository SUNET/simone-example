package se.uhr.simone.restbucks.boundary;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import java.time.Instant;

import org.junit.Test;

public class InstantAdaptorTest {

	private static final Instant EPOC = Instant.ofEpochSecond(0);

	@Test
	public void shouldConvertDateToString() throws Exception {
		assertThat(InstantAdaptor.marshal(EPOC), is("1970-01-01T00:00:00Z"));
	}

	@Test
	public void shouldConvertDateFromString() throws Exception {
		assertThat(InstantAdaptor.unmarshal("1970-01-01T00:00:00Z"), is(EPOC));
	}
}
