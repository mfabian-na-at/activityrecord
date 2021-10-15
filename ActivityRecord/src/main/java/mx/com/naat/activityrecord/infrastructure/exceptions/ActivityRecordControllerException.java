package mx.com.naat.activityrecord.infrastructure.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ActivityRecordControllerException {

	private long timestamp = 0L;

	@ExceptionHandler(value = { ActivityRecordNotFoundException.class })
	public ResponseEntity<Object> activityRecordNotFoundException(ActivityRecordNotFoundException ex) {
		timestamp = Instant.now().toEpochMilli();
		CustomBadResponse cbr = new CustomBadResponse(timestamp, 404, "Not Found", "NOT_FOUND", "/activity-records");
		return new ResponseEntity<Object>(cbr, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { ProjectNotFoundException.class })
	public ResponseEntity<Object> projectNotFoundException(ProjectNotFoundException ex) {
		timestamp = Instant.now().toEpochMilli();
		CustomBadResponse cbr = new CustomBadResponse(timestamp, 400, "Bad Request", "PROJECT_NOT_FOUND", "/activity-records");
		return new ResponseEntity<Object>(cbr, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { ActivityNotFoundException.class })
	public ResponseEntity<Object> activityNotFoundException(ActivityNotFoundException ex) {
		timestamp = Instant.now().toEpochMilli();
		CustomBadResponse cbr = new CustomBadResponse(timestamp, 400, "Bad Request", "ACTIVITY_NOT_FOUND", "/activity-records");
		return new ResponseEntity<Object>(cbr, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { InvalidDurationException.class })
	public ResponseEntity<Object> invalidDurationException(InvalidDurationException ex) {
		timestamp = Instant.now().toEpochMilli();
		CustomBadResponse cbr = new CustomBadResponse(timestamp, 400, "Bad Request", "INVALID_DURATION", "/activity-records");
		return new ResponseEntity<Object>(cbr, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { InvalidDurationLess24Exception.class })
	public ResponseEntity<Object> invalidDurationLess24Exception(InvalidDurationLess24Exception ex) {
		timestamp = Instant.now().toEpochMilli();
		CustomBadResponse cbr = new CustomBadResponse(timestamp, 400, "Bad Request", "RECORD_TOTAL_TIME_FOR_ONE_DAY_MUST_BE_24_HOURS_OR_LESS", "/activity-records");
		return new ResponseEntity<Object>(cbr, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { InvalidDateException.class })
	public ResponseEntity<Object> invalidDateException(InvalidDateException ex) {
		timestamp = Instant.now().toEpochMilli();
		CustomBadResponse cbr = new CustomBadResponse(timestamp, 400, "Bad Request", "INVALID_DATE", "/activity-records");
		return new ResponseEntity<Object>(cbr, HttpStatus.BAD_REQUEST);
	}
}
