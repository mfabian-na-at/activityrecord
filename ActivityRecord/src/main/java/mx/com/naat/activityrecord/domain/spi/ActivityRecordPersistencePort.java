package mx.com.naat.activityrecord.domain.spi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import mx.com.naat.activityrecord.domain.data.ActivityDto;
import mx.com.naat.activityrecord.domain.data.ClientDto;
import mx.com.naat.activityrecord.domain.data.ProjectDto;
import mx.com.naat.activityrecord.domain.data.UserDto;
import mx.com.naat.activityrecord.infrastructure.entity.ActivityRecord;

public interface ActivityRecordPersistencePort {

	public Optional<List<ActivityRecord>> getActivityRecordsListByIdAuthor(UUID idAuthor, Pageable page);

	public Optional<ActivityRecord> getActivityRecord(UUID idActivityRecord);

	public Optional<ActivityRecord> putActivityRecordUpdate(UUID idActivityRecord, ActivityRecord activityRecordRequest,
			UUID idAuthor, LocalDateTime timestamp);

	public Optional<ActivityRecord> postActivityRecordCreate(ActivityRecord activityRecordRequest, UUID idAuthor,
			LocalDateTime timestamp);

	public void deleteActivityRecordById(UUID idActivityRecord, UUID idAuthor);

	public Optional<LocalDate> getActivityRecordLastDate(UUID idAuthor, LocalDate date);

	public Integer countAllActivityRecords();

	public Integer countAllActivityRecordsByIdAuthor(UUID idAuthor);

	public Optional<List<ActivityRecord>> getHoursByDayByAuthor(UUID idAuthor, LocalDate date);

	public Integer getActivityRecordHoursByUniqueKey(UUID idAuthor, UUID idProject, UUID idActivity, LocalDate date);

	public Optional<UserDto> getUserDto(UUID idUser);

	public Optional<ProjectDto> getProjectDto(UUID idProject, UUID idClient);

	public Optional<ActivityDto> getActivityDto(UUID idActivity);

	public Optional<ClientDto> getClientDto(UUID idClient);

	public Optional<UUID> getIdClientFromProject(UUID idProject);

	public boolean existActivityRecordById(UUID idActivityRecord);

	public boolean existProjectById(UUID idProject);

	public boolean existActivityRecordHoursByUniqueKey(UUID idAuthor, UUID idProject, UUID idActivity, LocalDate date);

	public boolean existByIdActivityRecordAndIdAuthor(UUID idActivityRecord, UUID idAuthor);

}
