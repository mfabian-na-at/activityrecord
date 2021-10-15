package mx.com.naat.activityrecord.infrastructure.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import mx.com.naat.activityrecord.domain.data.ActivitiesListDto;
import mx.com.naat.activityrecord.domain.data.ActivityDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordDetailNestedDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordListDto;
import mx.com.naat.activityrecord.domain.data.ActivityRecordNestedDto;
import mx.com.naat.activityrecord.domain.data.ProjectDto;
import mx.com.naat.activityrecord.domain.data.ProjectListDto;
import mx.com.naat.activityrecord.domain.data.UserDto;
import mx.com.naat.activityrecord.infrastructure.entity.ActivityRecord;

public class ActivityRecordMyMapper {

	public static List<ActivityRecordListDto> activityRecordToActivityRecordListDto(
			List<ActivityRecordNestedDto> activityRecord) {

		ArrayList<ActivityRecordListDto> result = new ArrayList<>();
		ArrayList<ActivitiesListDto> activitiesList = null;
		HashSet<String> namesProject = new HashSet<>();
		ActivityRecordListDto listElement = null;
		ActivitiesListDto activities = null;
		ProjectListDto project = null;

		for (ActivityRecordNestedDto nested : activityRecord) {
			namesProject.add(nested.getProject().getName());
		}

		for (String np : namesProject) {
			listElement = new ActivityRecordListDto();

			project = new ProjectListDto();
			activitiesList = new ArrayList<>();

			for (ActivityRecordNestedDto nested : activityRecord) {
				if (nested.getProject().getName().equals(np)) {
					project.setName(nested.getProject().getName());
					project.setClient(nested.getProject().getIdClient());

					activities = new ActivitiesListDto();
					activities.setActivity(nested.getActivity());
					activities.setDate(nested.getDate());
					activities.setDuration(nested.getDuration());
					activitiesList.add(activities);
				}
			}

			listElement.setProject(project);
			listElement.setActivities(activitiesList);
			result.add(listElement);

		}

		return result;
	}
	
	public static ActivityRecordNestedDto activityRecordToActivityRecordDto(
			ActivityRecord activityRecord, ProjectDto project, ActivityDto activity, UserDto author) {
		ActivityRecordNestedDto result = new ActivityRecordNestedDto();
		
		result.setId(activityRecord.getId());
		result.setCreationDate(activityRecord.getCreationDate());
		result.setModificationDate(activityRecord.getModificationDate());
		result.setEnabled(activityRecord.isEnabled());
		result.setAuthor(author);
		result.setProject(project);
		result.setActivity(activity);
		result.setDuration(activityRecord.getDuration());
		result.setDate(activityRecord.getDate());
		return result;
	}
	
	public static ActivityRecordDetailNestedDto activityRecordToActivityRecordDetailDto(
			ActivityRecord activityRecord, ProjectDto project, ActivityDto activity, UserDto author) {
		ActivityRecordDetailNestedDto result = new ActivityRecordDetailNestedDto();
		
		result.setId(activityRecord.getId());
		result.setCreationDate(activityRecord.getCreationDate());
		result.setModificationDate(activityRecord.getModificationDate());
		result.setEnabled(activityRecord.isEnabled());
		result.setIdAuthor(author);
		result.setIdProject(project);
		result.setIdActivity(activity);
		result.setDuration(activityRecord.getDuration());
		result.setDate(activityRecord.getDate());
		return result;
	}
	
	public static ActivityRecordDetailNestedDto optionalToDetailNested(
			Optional<ActivityRecord> optionalActivityRecord, 
			UserDto idAuthor, ProjectDto idProject, ActivityDto idActivity) {
		ActivityRecordDetailNestedDto activityRecordsResponse = new ActivityRecordDetailNestedDto();

		activityRecordsResponse.setId(optionalActivityRecord.get().getId());
		activityRecordsResponse.setCreationDate(optionalActivityRecord.get().getCreationDate());
		activityRecordsResponse.setModificationDate(optionalActivityRecord.get().getModificationDate());
		activityRecordsResponse.setEnabled(optionalActivityRecord.get().isEnabled());

		activityRecordsResponse.setIdAuthor(idAuthor);
		activityRecordsResponse.setIdProject(idProject);
		activityRecordsResponse.setIdActivity(idActivity);

		activityRecordsResponse.setDuration(optionalActivityRecord.get().getDuration());
		activityRecordsResponse.setDate(optionalActivityRecord.get().getDate());

		return activityRecordsResponse;
	} 
}
