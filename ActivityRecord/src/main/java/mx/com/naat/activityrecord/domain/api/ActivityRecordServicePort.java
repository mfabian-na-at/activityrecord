package mx.com.naat.activityrecord.domain.api;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import mx.com.naat.activityrecord.domain.data.ActivityRecordDetailNestedDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordListDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordRequestDto;

public interface ActivityRecordServicePort {

	public List<ActivityRecordListDto> getActivityRecordsListByIdAuthor(Integer page, Integer size, String sort,
			UUID idAuthor);

	public ActivityRecordDetailNestedDto getActivityRecordDetail(UUID idActivityRecord, UUID idAuthor);

	public ActivityRecordDetailNestedDto putActivityRecordUpdate(UUID idActivityRecord,
			ActivityRecordRequestDto activityRecordRequest, UUID idAuthor);

	public ActivityRecordDetailNestedDto postActivityRecordCreate(UUID idAuthor,
			ActivityRecordRequestDto activityRecordRequest);

	public void deleteActivityRecordById(UUID idActivityRecord, UUID idAuthor);

	public LocalDate getActivityRecordLastDate(UUID id, LocalDate date);

	public int countAllActivityRecords();

	public int countAllActivityRecordsByIdAuthor(UUID idAuthor);

	public int countHoursByDayByAuthor(UUID idAuthor, LocalDate date);

}
