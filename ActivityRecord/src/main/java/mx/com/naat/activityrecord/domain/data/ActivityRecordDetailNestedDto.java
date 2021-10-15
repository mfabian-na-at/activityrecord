package mx.com.naat.activityrecord.domain.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ActivityRecordDetailNestedDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private UUID id;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	private boolean enabled;
	private UserDto idAuthor;
	private ProjectDto idProject;
	private ActivityDto idActivity;
	private int duration;
	private LocalDate date;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public LocalDateTime getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(LocalDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public UserDto getIdAuthor() {
		return idAuthor;
	}
	public void setIdAuthor(UserDto idAuthor) {
		this.idAuthor = idAuthor;
	}
	public ProjectDto getIdProject() {
		return idProject;
	}
	public void setIdProject(ProjectDto idProject) {
		this.idProject = idProject;
	}
	public ActivityDto getIdActivity() {
		return idActivity;
	}
	public void setIdActivity(ActivityDto idActivity) {
		this.idActivity = idActivity;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	

}
