package mx.com.naat.activityrecord.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import mx.com.naat.activityrecord.domain.api.ActivityRecordServicePort;
import mx.com.naat.activityrecord.domain.data.ActivityDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordDetailNestedDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordListDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordNestedDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordRequestDto;
import mx.com.naat.activityrecord.domain.data.ClientDto;
import mx.com.naat.activityrecord.domain.data.ProjectDto;
import mx.com.naat.activityrecord.domain.spi.ActivityRecordPersistencePort;
import mx.com.naat.activityrecord.infrastructure.entity.ActivityRecord;
import mx.com.naat.activityrecord.infrastructure.exceptions.ActivityNotFoundException;
import mx.com.naat.activityrecord.infrastructure.exceptions.ActivityRecordNotFoundException;
import mx.com.naat.activityrecord.infrastructure.exceptions.InvalidDateException;
import mx.com.naat.activityrecord.infrastructure.exceptions.InvalidDurationException;
import mx.com.naat.activityrecord.infrastructure.exceptions.InvalidDurationLess24Exception;
import mx.com.naat.activityrecord.infrastructure.exceptions.ProjectNotFoundException;
import mx.com.naat.activityrecord.infrastructure.mapper.ActivityRecordMyMapper;

import org.springframework.data.domain.Pageable;

public class ActivityRecordServiceImpl implements ActivityRecordServicePort {

	private ActivityRecordPersistencePort activityRecordsPersistencePort;

	public ActivityRecordServiceImpl(ActivityRecordPersistencePort activityRecordsPersistancePort) {
		this.activityRecordsPersistencePort = activityRecordsPersistancePort;
	}

	@Override
	public List<ActivityRecordListDto> getActivityRecordsListByIdAuthor(Integer page, Integer size, String sort,
			UUID idAuthor) {

		List<ActivityRecordNestedDto> activityRecords = null;
		ActivityRecordNestedDto activityRecordNestedDto = null;

		Pageable pageable = null;
		UUID idClient = null;

		String sortTemp[] = null;
		String sortColumn = null;

		if (size == null) {
			size = activityRecordsPersistencePort.countAllActivityRecords();
		}

		sortTemp = sort.split(",");
		sortColumn = sortTemp[0];

		if (sortTemp.length > 1 && sortTemp[1].equals("desc")) {
			pageable = PageRequest.of(page, size, Sort.by(sortColumn).descending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sortColumn).ascending());
		}

		activityRecords = new ArrayList<>();

		for (ActivityRecord ar : activityRecordsPersistencePort.getActivityRecordsListByIdAuthor(idAuthor, pageable)
				.get()) {

			idClient = activityRecordsPersistencePort.getIdClientFromProject(ar.getIdProject()).get();

			activityRecordNestedDto = ActivityRecordMyMapper.activityRecordToActivityRecordDto(ar,
					activityRecordsPersistencePort.getProjectDto(ar.getIdProject(), idClient).get(),
					activityRecordsPersistencePort.getActivityDto(ar.getIdActivity()).get(),
					activityRecordsPersistencePort.getUserDto(ar.getIdAuthor()).get());

			activityRecords.add(activityRecordNestedDto);

		}

		return ActivityRecordMyMapper.activityRecordToActivityRecordListDto(activityRecords);

	}

	@Override
	public ActivityRecordDetailNestedDto getActivityRecordDetail(UUID idActivityRecord, UUID idAuthor) {
		if (activityRecordsPersistencePort.existActivityRecordById(idActivityRecord)) {

			Optional<ActivityRecord> optionalActivityRecord = null;

			optionalActivityRecord = activityRecordsPersistencePort.getActivityRecord(idActivityRecord);

			if (optionalActivityRecord == null) {
				throw new ActivityRecordNotFoundException(
						"Este ActivityRecord con id [" + idActivityRecord + "] no existe.");
			}

			UUID idClient = activityRecordsPersistencePort
					.getIdClientFromProject(optionalActivityRecord.get().getIdProject()).get();

			if (idClient == null) {
				throw new ProjectNotFoundException(
						"Este Projecto con id [" + optionalActivityRecord.get().getIdProject()
								+ "] no pertenece a ese cliente con id [" + idClient + "].");
			}

			if (optionalActivityRecord.get().getIdAuthor().equals(idAuthor)) {
				return ActivityRecordMyMapper.optionalToDetailNested(optionalActivityRecord,
						activityRecordsPersistencePort.getUserDto(optionalActivityRecord.get().getIdAuthor()).get(),
						activityRecordsPersistencePort
								.getProjectDto(optionalActivityRecord.get().getIdProject(), idClient).get(),
						activityRecordsPersistencePort.getActivityDto(optionalActivityRecord.get().getIdActivity())
								.get());

			} else {
				throw new ActivityRecordNotFoundException(
						"Este ActivityRecord con id [" + idActivityRecord + "] no existe.");
			}
		}
		throw new ActivityRecordNotFoundException("Este ActivityRecord con id [" + idActivityRecord + "] no existe.");
	}

	@Override
	public ActivityRecordDetailNestedDto putActivityRecordUpdate(UUID idActivityRecord,
			ActivityRecordRequestDto activityRecordRequest, UUID idAuthor) {

		LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		Optional<ActivityRecord> tempActivityRecord = null;

		ClientDto client = null;
		ProjectDto project = null;
		ActivityDto activity = null;
		ActivityRecordDetailNestedDto response = null;

		int totalCurrentHours = 0;
		int activityCurrentHours = -1;
		int tempCurrentHours = 0;

		UUID idClient = null;

		if (activityRecordsPersistencePort.existProjectById(activityRecordRequest.getIdProject())) {
			idClient = activityRecordsPersistencePort.getIdClientFromProject(activityRecordRequest.getIdProject())
					.get();
		} else {
			throw new ProjectNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdProject() + "] no existe.");
		}

		if (idActivityRecord != null) {

			if (activityRecordsPersistencePort.existActivityRecordById(idActivityRecord)) {
				tempActivityRecord = activityRecordsPersistencePort.getActivityRecord(idActivityRecord);
			} else {
				throw new ActivityRecordNotFoundException(
						"Este ActivityRecord con id [" + idActivityRecord + "] no existe.");
			}
		} else {
			throw new ActivityRecordNotFoundException(
					"Este ActivityRecord con id [" + idActivityRecord + "] no existe.");
		}

		totalCurrentHours = countHoursByDayByAuthor(idAuthor, activityRecordRequest.getDate());

		try {
			client = activityRecordsPersistencePort.getClientDto(idClient).get();
		} catch (Exception e) {
			throw new ProjectNotFoundException("Este Projecto con id [" + activityRecordRequest.getIdProject()
					+ "] no pertenece a ese cliente con id [" + idClient + "].");
		}

		try {
			project = activityRecordsPersistencePort.getProjectDto(activityRecordRequest.getIdProject(), idClient)
					.get();
		} catch (Exception e) {
			throw new ProjectNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdProject() + "] no existe.");
		}

		try {
			activity = activityRecordsPersistencePort.getActivityDto(activityRecordRequest.getIdActivity()).get();
		} catch (Exception e) {
			throw new ActivityNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdActivity() + "] no existe.");
		}

		if (client == null) {
			throw new ProjectNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdProject() + "] no existe.");
		}

		if (project == null) {
			throw new ProjectNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdProject() + "] no existe.");
		}

		if (activity == null) {
			throw new ActivityNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdActivity() + "] no existe.");
		}

		if (activityRecordRequest.getDuration() != null) {

			activityCurrentHours = tempActivityRecord.get().getDuration();

			tempCurrentHours = totalCurrentHours - activityCurrentHours;

			if (tempCurrentHours >= 24) {
				throw new InvalidDurationLess24Exception("El día de hoy ya se registraron 24 horas.");
			}

			if (activityRecordRequest.getDuration() <= 0) {
				throw new InvalidDurationException("La duración no debe de ser menor igual que 0, puso ["
						+ activityRecordRequest.getDuration() + "]");
			}

			if ((activityRecordRequest.getDuration() + tempCurrentHours) > 24) {
				throw new InvalidDurationLess24Exception(
						"La duración total no debe de ser mayor que 24, puso [" + activityRecordRequest.getDuration()
								+ "], ya tendría registradas [" + tempCurrentHours + "] horas");
			}

			if (activityRecordRequest.getDuration() > 24) {
				throw new InvalidDurationLess24Exception(
						"La duración no debe de ser mayor que 24, puso [" + activityRecordRequest.getDuration() + "]");
			}
		} else {
			throw new InvalidDurationException(
					"La duración no debe de ser menor igual que 0 puso [" + activityRecordRequest.getDuration() + "]");
		}

		if (activityRecordRequest.getDate() != null) {
			if (activityRecordRequest.getDate().isAfter(LocalDate.now())) {
				throw new InvalidDateException("No puede poner fechas futuras");
			}
		} else {
			throw new InvalidDateException("No puede poner ese tipo de fechas");
		}

		if (activityRecordsPersistencePort.existActivityRecordHoursByUniqueKey(idAuthor,
				activityRecordRequest.getIdProject(), activityRecordRequest.getIdActivity(),
				activityRecordRequest.getDate())) { // Existe la llave

			if (tempActivityRecord.get().getIdAuthor().equals(idAuthor)) { // Si corresponde al mismo autor
				// tempActivityRecord.get().setIdProject(activityRecordRequest.getIdProject());
				// tempActivityRecord.get().setIdActivity(activityRecordRequest.getIdActivity());

				tempActivityRecord.get().setModificationDate(timestamp);
				tempActivityRecord.get().setDuration(activityRecordRequest.getDuration());
				tempActivityRecord.get().setDate(activityRecordRequest.getDate());

				response = ActivityRecordMyMapper.activityRecordToActivityRecordDetailDto(
						activityRecordsPersistencePort.putActivityRecordUpdate(idActivityRecord,
								tempActivityRecord.get(), idAuthor, timestamp).get(),
						project, activity, activityRecordsPersistencePort.getUserDto(idAuthor).get());

				if (response == null) {
					throw new ActivityRecordNotFoundException("No existe este ActivityRecord.");
				}
			} else {
				throw new ActivityRecordNotFoundException(
						"Este ActivityRecord con id [" + idActivityRecord + "] no existe.");
			}

		} else {
			throw new ActivityRecordNotFoundException(
					"La llave de este ActivityRecord con id [" + idActivityRecord + "] no existe.");
		}

		return response;

	}

	@Override
	public ActivityRecordDetailNestedDto postActivityRecordCreate(UUID idAuthor,
			ActivityRecordRequestDto activityRecordRequest) {

		LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		LocalDate today = LocalDate.now();

		ClientDto client = null;
		ProjectDto project = null;
		ActivityDto activity = null;
		ActivityRecordDetailNestedDto response = null;

		UUID idClient = null;

		if (activityRecordsPersistencePort.existProjectById(activityRecordRequest.getIdProject())) {
			idClient = activityRecordsPersistencePort.getIdClientFromProject(activityRecordRequest.getIdProject())
					.get();
		} else {
			throw new ProjectNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdProject() + "] no existe.");
		}

		try {
			client = activityRecordsPersistencePort.getClientDto(idClient).get();
		} catch (Exception e) {
			throw new ProjectNotFoundException("Este Projecto con id [" + activityRecordRequest.getIdProject()
					+ "] no pertenece a ese cliente con id [" + idClient + "].");
		}

		try {
			project = activityRecordsPersistencePort.getProjectDto(activityRecordRequest.getIdProject(), idClient)
					.get();
		} catch (Exception e) {
			throw new ProjectNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdProject() + "] no existe.");
		}

		try {
			activity = activityRecordsPersistencePort.getActivityDto(activityRecordRequest.getIdActivity()).get();
		} catch (Exception e) {
			throw new ActivityNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdActivity() + "] no existe.");
		}

		if (client == null) {
			throw new ProjectNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdProject() + "] no existe.");
		}

		if (project == null) {
			throw new ProjectNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdProject() + "] no existe.");
		}

		if (activity == null) {
			throw new ActivityNotFoundException(
					"Este Projecto con id [" + activityRecordRequest.getIdActivity() + "] no existe.");
		}

		if (activityRecordRequest.getDuration() != null) {
			if (countHoursByDayByAuthor(idAuthor, activityRecordRequest.getDate()) >= 24) {
				throw new InvalidDurationLess24Exception("El día de hoy ya se registraron 24 horas.");
			}
			if (activityRecordRequest.getDuration() <= 0) {
				throw new InvalidDurationException("La duración no debe de ser menor igual que 0, puso ["
						+ activityRecordRequest.getDuration() + "]");
			}
			if ((activityRecordRequest.getDuration() + countHoursByDayByAuthor(idAuthor, today)) > 24) {
				throw new InvalidDurationLess24Exception(
						"La duración total no debe de ser mayor que 24, puso [" + activityRecordRequest.getDuration()
								+ "], ya tiene registradas [" + countHoursByDayByAuthor(idAuthor, today) + "] horas");
			}
			if (activityRecordRequest.getDuration() > 24) {
				throw new InvalidDurationLess24Exception(
						"La duración no debe de ser mayor que 24, puso [" + activityRecordRequest.getDuration() + "]");
			}
		} else {
			throw new InvalidDurationException(
					"La duración no debe de ser menor igual que 0 puso [" + activityRecordRequest.getDuration() + "]");
		}

		if (activityRecordRequest.getDate() != null) {
			if (activityRecordRequest.getDate().isAfter(today)) {
				throw new InvalidDateException("No puede poner fechas futuras");
			}
		} else {
			throw new InvalidDateException("No puede poner ese tipo de fechas");
		}

		if (activityRecordsPersistencePort.existActivityRecordHoursByUniqueKey(idAuthor,
				activityRecordRequest.getIdProject(), activityRecordRequest.getIdActivity(),
				activityRecordRequest.getDate())) {

			throw new ActivityRecordNotFoundException("Este ActivityRecord no se puede crear, llave ya existente.");

		} else {
			ActivityRecord activityRecordToCreate = new ActivityRecord();

			activityRecordToCreate.setId(UUID.randomUUID());

			activityRecordToCreate.setCreationDate(timestamp);
			activityRecordToCreate.setModificationDate(timestamp);
			activityRecordToCreate.setEnabled(true);
			activityRecordToCreate.setIdAuthor(idAuthor);

			activityRecordToCreate.setIdProject(activityRecordRequest.getIdProject());
			activityRecordToCreate.setIdActivity(activityRecordRequest.getIdActivity());
			activityRecordToCreate.setDuration(activityRecordRequest.getDuration());
			activityRecordToCreate.setDate(activityRecordRequest.getDate());

			response = ActivityRecordMyMapper
					.activityRecordToActivityRecordDetailDto(
							activityRecordsPersistencePort
									.postActivityRecordCreate(activityRecordToCreate, idAuthor, timestamp).get(),
							project, activity, activityRecordsPersistencePort.getUserDto(idAuthor).get());
			if (response == null) {
				throw new ActivityRecordNotFoundException("No se ha podido crear ese ActivityRecord");
			}
		}
		return response;
	}

	@Override
	public void deleteActivityRecordById(UUID idActivityRecord, UUID idAuthor) {
		if (activityRecordsPersistencePort.existByIdActivityRecordAndIdAuthor(idActivityRecord, idAuthor)) {
			activityRecordsPersistencePort.deleteActivityRecordById(idActivityRecord, idAuthor);
		} else {
			throw new ActivityRecordNotFoundException(
					"Este ActivityRecord con id [" + idActivityRecord + "] no existe.");
		}
	}

	@Override
	public LocalDate getActivityRecordLastDate(UUID id, LocalDate date) {
		return (activityRecordsPersistencePort.getActivityRecordLastDate(id, date) != null)
				? activityRecordsPersistencePort.getActivityRecordLastDate(id, date).get()
				: LocalDate.MIN;
	}

	@Override
	public int countAllActivityRecords() {
		return activityRecordsPersistencePort.countAllActivityRecords() != null
				? activityRecordsPersistencePort.countAllActivityRecords()
				: 0;
	}

	@Override
	public int countAllActivityRecordsByIdAuthor(UUID idAuthor) {
		return activityRecordsPersistencePort.countAllActivityRecordsByIdAuthor(idAuthor) != null
				? activityRecordsPersistencePort.countAllActivityRecordsByIdAuthor(idAuthor)
				: 0;
	}

	@Override
	public int countHoursByDayByAuthor(UUID idAuthor, LocalDate date) {
		Integer totalHours = 0;
		for (ActivityRecord ar : activityRecordsPersistencePort.getHoursByDayByAuthor(idAuthor, date).get()) {
			totalHours += ar.getDuration();
		}
		return totalHours;
	}

}
