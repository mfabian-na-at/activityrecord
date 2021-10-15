package mx.com.naat.activityrecord.infrastructure.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "activityrecord")
public class ActivityRecord {
	
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

	public UUID getIdAuthor() {
		return idAuthor;
	}

	public void setIdAuthor(UUID idAuthor) {
		this.idAuthor = idAuthor;
	}

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

	@Id
	@Column(name = "activity_record_id")
	private UUID id;
	
	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	
	@Column(name = "modification_date")
	private LocalDateTime modificationDate;
	
	@Column(name = "activity_record_enabled")
	private boolean enabled;
	
	@Column(name = "user_id")
	private UUID idAuthor;
	
	@Column(name = "project_id")
	private UUID idProject;
	
	@Column(name = "activity_id")
	private UUID idActivity;
	
	@Column(name = "activity_duration")
	private int duration;
	
	@Column(name = "activity_record_date")
	private LocalDate date;
	
	
}
