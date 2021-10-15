package mx.com.naat.activityrecord.domain.data;

import java.io.Serializable;
import java.util.List;

public class ActivityRecordListDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private ProjectListDto project;
	private List<ActivitiesListDto> activities;
	public ProjectListDto getProject() {
		return project;
	}
	public void setProject(ProjectListDto project) {
		this.project = project;
	}
	public List<ActivitiesListDto> getActivities() {
		return activities;
	}
	public void setActivities(List<ActivitiesListDto> activities) {
		this.activities = activities;
	}
	
	
}
