package mx.com.naat.activityrecord.domain.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ActivityRecordNestedDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private UUID id;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	private boolean enabled;
	private UserDto author;
	private ProjectDto project;
	private ActivityDto activity;
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
	public UserDto getAuthor() {
		return author;
	}
	public void setAuthor(UserDto author) {
		this.author = author;
	}
	public ProjectDto getProject() {
		return project;
	}
	public void setProject(ProjectDto project) {
		this.project = project;
	}
	public ActivityDto getActivity() {
		return activity;
	}
	public void setActivity(ActivityDto activity) {
		this.activity = activity;
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
