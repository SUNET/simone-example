package se.uhr.simone.restbucks.entity;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import se.uhr.simone.extension.api.entity.DatabaseAdmin;
import se.uhr.simone.extension.api.feed.UniqueIdentifier;
import se.uhr.simone.restbucks.control.OrderException;

public class DocumentStore<T> implements DatabaseAdmin {

	private final String name;

	private final Class<T> type;

	private JdbcTemplate jdbcTemplate;

	protected DocumentStore(String name, Class<T> type) {
		this.name = name;
		this.type = type;
	}

	@PostConstruct
	public void postConstruct() {
		if (!isTableCreated()) {
			jdbcTemplate.execute("CREATE TABLE " + name + " (ID char(16) for bit data NOT NULL, VALUE XML)");
			jdbcTemplate.execute("ALTER TABLE " + name + " ADD PRIMARY KEY (ID)");			
		}
	}

	@Inject
	public void setDataSource(@RestbucksDS DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	public T get(UniqueIdentifier uuid) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT XMLSERIALIZE(VALUE AS CLOB(1M)) AS VALUE FROM " + name + " WHERE ID = ?");
		return jdbcTemplate.queryForObject(sql.toString(), new DocumentRowMapper(), uuid.toByteArray());
	}

	public void put(UniqueIdentifier uuid, T value) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO " + name
				+ " (ID, VALUE) VALUES (?, XMLPARSE( DOCUMENT CAST(? AS CLOB(1M)) PRESERVE WHITESPACE))");
		jdbcTemplate.update(sql.toString(), uuid.toByteArray(), convertToXml(value));
	}

	public List<T> getAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT XMLSERIALIZE(VALUE AS CLOB(1M)) AS VALUE FROM " + name);
		return jdbcTemplate.query(sql.toString(), new DocumentRowMapper());
	}

	@Override
	public void dropTables() {
		jdbcTemplate.execute("DELETE FROM " + name);
	}

	private boolean isTableCreated() {
		int count = jdbcTemplate.queryForObject("select count(*) from SYS.SYSTABLES where TABLENAME = '" + name + "'",
				Integer.class);
		return count != 0;
	}

	private String convertToXml(T event) {
		try {
			JAXBContext context = JAXBContext.newInstance(event.getClass());
			Marshaller m = context.createMarshaller();
			StringWriter outstr = new StringWriter();
			m.marshal(event, outstr);
			return outstr.toString();
		} catch (JAXBException e) {
			throw new OrderException("Can't create XML", e);
		}
	}

	@SuppressWarnings("unchecked")
	private T convertFromXml(String xml) {

		try {
			JAXBContext context = JAXBContext.newInstance(type);

			Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();

			return (T) jaxbUnmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			throw new OrderException("Can't create object from XML", e);
		}
	}

	class DocumentRowMapper implements RowMapper<T> {

		@Override
		public T mapRow(ResultSet rs, int rowNum) throws SQLException {
			String xml = rs.getString("VALUE");
			return convertFromXml(xml);
		}
	}
}
