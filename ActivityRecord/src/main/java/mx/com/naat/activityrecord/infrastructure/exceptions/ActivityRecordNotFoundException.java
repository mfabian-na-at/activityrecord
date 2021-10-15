package mx.com.naat.activityrecord.infrastructure.exceptions;

public class ActivityRecordNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ActivityRecordNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
