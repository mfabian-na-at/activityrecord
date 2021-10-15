package mx.com.naat.activityrecord.infrastructure.exceptions;

public class InvalidDurationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidDurationException(String errorMessage) {
		super(errorMessage);
	}
}
