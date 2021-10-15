package mx.com.naat.activityrecord.infrastructure.adapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.com.naat.activityrecord.domain.data.ActivityDto;
import mx.com.naat.activityrecord.domain.data.ClientDto;
import mx.com.naat.activityrecord.domain.data.ProjectDto;
import mx.com.naat.activityrecord.domain.data.UserDto;
import mx.com.naat.activityrecord.domain.spi.ActivityRecordPersistencePort;
import mx.com.naat.activityrecord.infrastructure.entity.ActivityRecord;
import mx.com.naat.activityrecord.infrastructure.repository.ActivityRecordRepository;
import mx.com.naat.activityrecord.infrastructure.repository.ProjectRepository;

@Service
public class ActivityRecordJpaAdapter implements ActivityRecordPersistencePort {

	@Autowired
	private ActivityRecordRepository activityRecordsRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public Optional<List<ActivityRecord>> getActivityRecordsListByIdAuthor(UUID idAuthor, Pageable page) {
		return Optional.of(activityRecordsRepository.findByIdAuthorOrderByDateDescIdProjectDesc(idAuthor, page));
	}

	@Override
	public Optional<ActivityRecord> getActivityRecord(UUID idActivityRecord) {
		return activityRecordsRepository.findById(idActivityRecord);
	}

	@Override
	public Optional<ActivityRecord> putActivityRecordUpdate(UUID idActivityRecord, ActivityRecord activityRecordRequest,
			UUID idAuthor, LocalDateTime timestamp) {

		Optional<ActivityRecord> response = null;

		if (activityRecordsRepository.existsById(idActivityRecord)) {
			if (activityRecordsRepository.findById(idActivityRecord).get().getIdAuthor().equals(idAuthor)) {
				response = Optional.of(activityRecordsRepository.save(activityRecordRequest));
			}
		}

		return response;
	}

	@Override
	public Optional<ActivityRecord> postActivityRecordCreate(ActivityRecord activityRecordRequest, UUID idAuthor,
			LocalDateTime timestamp) {

		Optional<ActivityRecord> response = null;

		if (!activityRecordsRepository.existsByIdAuthorAndIdProjectAndIdActivityAndDate(idAuthor,
				activityRecordRequest.getIdProject(), activityRecordRequest.getIdActivity(),
				activityRecordRequest.getDate())) {
			response = Optional.of(activityRecordsRepository.save(activityRecordRequest));
		}

		return response;
	}

	@Override
	public void deleteActivityRecordById(UUID idActivityRecord, UUID idAuthor) {
		if (activityRecordsRepository.existsById(idActivityRecord)
				&& activityRecordsRepository.findById(idActivityRecord).get().getIdAuthor().equals(idAuthor)) {
			activityRecordsRepository.deleteById(idActivityRecord);
		}
	}

	@Override
	public Optional<LocalDate> getActivityRecordLastDate(UUID idAuthor, LocalDate date) {
		return Optional
				.of((activityRecordsRepository.findDistinctByIdAuthorAndDateBeforeOrderByDateDesc(idAuthor, date))
						.get(0).getDate());
	}

	@Override
	public Integer countAllActivityRecords() {
		return (int) activityRecordsRepository.count();
	}

	@Override
	public Integer countAllActivityRecordsByIdAuthor(UUID idAuthor) {
		return (int) activityRecordsRepository.countByIdAuthor(idAuthor);
	}

	@Override
	public Optional<List<ActivityRecord>> getHoursByDayByAuthor(UUID idAuthor, LocalDate date) {
		return Optional.of(activityRecordsRepository.findByIdAuthorAndDate(idAuthor, date));
	}

	@Override
	public Integer getActivityRecordHoursByUniqueKey(UUID idAuthor, UUID idProject, UUID idActivity, LocalDate date) {
		return activityRecordsRepository
				.findByIdAuthorAndIdProjectAndIdActivityAndDate(idAuthor, idProject, idActivity, date).getDuration();
	}

	@Override
	public Optional<UserDto> getUserDto(UUID idUser) {
		String uri = "http://localhost:8080/users/" + idUser;
		RestTemplate restTemplate = new RestTemplate();
		Optional<UserDto> result = Optional.of(restTemplate.getForObject(uri, UserDto.class));
		return result;
	}

	@Override
	public Optional<ProjectDto> getProjectDto(UUID idProject, UUID idClient) {
		String uri = "http://localhost:8082/clients/" + idClient + "/projects/" + idProject;
		RestTemplate restTemplate = new RestTemplate();
		Optional<ProjectDto> result = Optional.of(restTemplate.getForObject(uri, ProjectDto.class));
		return result;
	}

	@Override
	public Optional<ActivityDto> getActivityDto(UUID idActivity) {
		String uri = "http://localhost:8083/activities/" + idActivity;
		RestTemplate restTemplate = new RestTemplate();
		Optional<ActivityDto> result = Optional.of(restTemplate.getForObject(uri, ActivityDto.class));
		return result;
	}

	@Override
	public Optional<ClientDto> getClientDto(UUID idClient) {
		String uri = "http://localhost:8081/clients/" + idClient;
		RestTemplate restTemplate = new RestTemplate();
		Optional<ClientDto> result = Optional.of(restTemplate.getForObject(uri, ClientDto.class));
		return result;
	}

	@Override
	public Optional<UUID> getIdClientFromProject(UUID idProject) {
		return Optional.of(projectRepository.findById(idProject).get().getIdClient());
	}

	@Override
	public boolean existActivityRecordById(UUID idActivityRecord) {
		return activityRecordsRepository.existsById(idActivityRecord);
	}

	@Override
	public boolean existProjectById(UUID idProject) {
		return projectRepository.existsById(idProject);
	}

	@Override
	public boolean existActivityRecordHoursByUniqueKey(UUID idAuthor, UUID idProject, UUID idActivity, LocalDate date) {
		return activityRecordsRepository.existsByIdAuthorAndIdProjectAndIdActivityAndDate(idAuthor, idProject,
				idActivity, date);
	}

	@Override
	public boolean existByIdActivityRecordAndIdAuthor(UUID idActivityRecord, UUID idAuthor) {
		return activityRecordsRepository.existsByIdAndIdAuthor(idActivityRecord, idAuthor);
	}

}
