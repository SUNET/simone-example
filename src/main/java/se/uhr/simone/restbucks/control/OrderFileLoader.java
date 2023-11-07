package se.uhr.simone.restbucks.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dependent
public class OrderFileLoader {

	private static final Logger LOG = LoggerFactory.getLogger(OrderFileLoader.class);

	public enum Result {
		SUCCESS,
		ERROR
	}

	@Inject
	OrderController orderController;

	public Result load(InputStream is) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
			reader.lines().forEach(line -> {
				var order = orderController.create(line.trim());
				LOG.info("added: {}", order.id);
			});
		} catch (IOException e) {
			LOG.error("error loading orders", e);
			return Result.ERROR;
		}
		return Result.SUCCESS;
	}
}
