package se.uhr.simone.restbucks.control;

public class OrderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OrderException(String message, Throwable cause) {
		super(message, cause);
	}
}
