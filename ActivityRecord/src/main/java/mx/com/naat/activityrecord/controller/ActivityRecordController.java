package mx.com.naat.activityrecord.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.naat.activityrecord.domain.api.ActivityRecordServicePort;
import mx.com.naat.activityrecord.domain.data.ActivityRecordDetailNestedDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordListDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordRequestDto;

@RestController
@RequestMapping("/activity-records")
public class ActivityRecordController {

	// ID del autor, es decir, id del usuario que está loggeado.
	// 00000000-0000-0000-0000-000000000001
	// 00000000-0000-0000-0000-000000000007
	// FE3F7DBF-7515-45C2-9D31-F8A7658CDB19
	UUID idAuthor = UUID.fromString("00000000-0000-0000-0000-000000000007");

	@Autowired
	private ActivityRecordServicePort activityRecordServicePort;

	@GetMapping("/{idActivityRecord}")
	public ActivityRecordDetailNestedDto activityRecordDetail(@PathVariable UUID idActivityRecord) {
		return activityRecordServicePort.getActivityRecordDetail(idActivityRecord, idAuthor);
	}

	@GetMapping
	private ResponseEntity<Iterable<ActivityRecordListDto>> getActivityRecordPage(
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "20") Integer size,
			@RequestParam(required = false, defaultValue = "id,asc") String sort) {

		Optional<LocalDate> lastDate = Optional
				.of(activityRecordServicePort.getActivityRecordLastDate(idAuthor, LocalDate.now()));

		int listSize = activityRecordServicePort.countAllActivityRecordsByIdAuthor(idAuthor);
		long days = ChronoUnit.DAYS.between(lastDate.get(), LocalDate.now());

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Total-Elements", "" + listSize);
		responseHeaders.set("Days-Since-Last-Record", "" + days);

		return new ResponseEntity<>(
				activityRecordServicePort.getActivityRecordsListByIdAuthor(page, size, sort, idAuthor), responseHeaders,
				HttpStatus.OK);
	}

	@PostMapping
	private ResponseEntity<ActivityRecordDetailNestedDto> postActivityRecordCreate(
			@RequestBody ActivityRecordRequestDto activityRecordRequest) {

		ActivityRecordDetailNestedDto activityRequest = activityRecordServicePort.postActivityRecordCreate(idAuthor,
				activityRecordRequest);

		return new ResponseEntity<>(activityRequest, HttpStatus.CREATED);
	}

	@PutMapping("/{idActivityRecord}")
	private ResponseEntity<ActivityRecordDetailNestedDto> putActivityRecordCreate(@PathVariable UUID idActivityRecord,
			@RequestBody ActivityRecordRequestDto activityRecordRequest) {

		ActivityRecordDetailNestedDto activityRequest = activityRecordServicePort
				.putActivityRecordUpdate(idActivityRecord, activityRecordRequest, idAuthor);

		return new ResponseEntity<>(activityRequest, HttpStatus.OK);
	}

	@DeleteMapping("/{idActivityRecord}")
	private ResponseEntity<Object> deleteActivityRecord(@PathVariable UUID idActivityRecord) {

		activityRecordServicePort.deleteActivityRecordById(idActivityRecord, idAuthor);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
