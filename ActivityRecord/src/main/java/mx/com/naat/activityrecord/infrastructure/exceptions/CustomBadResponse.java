package mx.com.naat.activityrecord.infrastructure.exceptions;

public class CustomBadResponse {
	
	private long timestamp;
	private int status;
	private String error;
	private String message;
	private String path;

	public CustomBadResponse(long timestamp, int status, String error, String message, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getPath() {
		return path;
	}

	public String getMessage() {
		return message;
	}
}
