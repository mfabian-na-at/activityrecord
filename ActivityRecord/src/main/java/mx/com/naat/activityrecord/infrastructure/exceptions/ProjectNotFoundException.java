package mx.com.naat.activityrecord.infrastructure.exceptions;

public class ProjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProjectNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
