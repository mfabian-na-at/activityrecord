package mx.com.naat.activityrecord.domain.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class ActivityRecordRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private UUID idProject;
	private UUID idActivity;
	private Integer duration;
	private LocalDate date;
	
	public UUID getIdProject() {
		return idProject;
	}
	public void setIdProject(UUID idProject) {
		this.idProject = idProject;
	}
	public UUID getIdActivity() {
		return idActivity;
	}
	public void setIdActivity(UUID idActivity) {
		this.idActivity = idActivity;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	

}
