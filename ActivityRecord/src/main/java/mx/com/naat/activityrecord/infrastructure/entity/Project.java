package mx.com.naat.activityrecord.infrastructure.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECTS")
public class Project {
	
	@Id
	@Column(name = "PROJECT_ID")
	private UUID id;
	
	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;
	
	@Column(name = "MODIFICATION_DATE")
	private LocalDateTime modificationDate;
	
	@Column(name = "PROJECT_ENABLED")
	private boolean enabled;

	@Column(name = "AUTHOR_ID")
	private UUID idAuthor;
	
	@Column(name = "CLIENT_ID")
	private UUID idClient;
	
	@Column(name = "PROJECT_KEY")
	private String key;
	
	@Column(name = "PROJECT_NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;

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

	public UUID getIdClient() {
		return idClient;
	}

	public void setIdClient(UUID idClient) {
		this.idClient = idClient;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
