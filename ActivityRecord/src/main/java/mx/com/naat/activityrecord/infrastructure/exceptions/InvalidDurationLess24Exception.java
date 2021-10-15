package mx.com.naat.activityrecord.infrastructure.exceptions;

public class InvalidDurationLess24Exception extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidDurationLess24Exception(String errorMessage) {
		super(errorMessage);
	}
}