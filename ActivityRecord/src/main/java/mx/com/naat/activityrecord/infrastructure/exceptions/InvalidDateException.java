package mx.com.naat.activityrecord.infrastructure.exceptions;

public class InvalidDateException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidDateException(String errorMessage) {
		super(errorMessage);
	}
}
