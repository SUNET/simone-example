package se.uhr.simone.restbucks.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import javax.inject.Inject;

import se.uhr.simone.extension.api.feed.UniqueIdentifier;
import se.uhr.simone.extension.api.fileloader.ExtensionContext;
import se.uhr.simone.extension.api.fileloader.FileLoader;
import se.uhr.simone.extension.api.fileloader.FileLoaderDescriptor;
import se.uhr.simone.restbucks.boundary.OrderRepresentation;

public class OrderFileLoaderDescriptor implements FileLoaderDescriptor {

	@Inject
	private OrderController orderController;
	
	@Override
	public boolean accept(String filename) {
		return filename.endsWith(".txt");
	}

	@Override
	public FileLoader createJob(Reader reader) {
		return new OrderFileLoader(reader);
	}

	class OrderFileLoader implements FileLoader {

		private final BufferedReader reader;

		public OrderFileLoader(Reader reader) {
			this.reader = new BufferedReader(reader);
		}

		@Override
		public Result execute(ExtensionContext context) {
			try {
				String line;

				while ((line = reader.readLine()) != null) {
					OrderRepresentation order = orderController.create(line.trim());
					context.addEventId(UniqueIdentifier.of(order.getId()));
				}
			} catch (IOException e) {
				context.setErrorMessage("Can't load database: " + e.getMessage());
				return Result.ERROR;
			}
			return Result.SUCCESS;
		}
	}
}
