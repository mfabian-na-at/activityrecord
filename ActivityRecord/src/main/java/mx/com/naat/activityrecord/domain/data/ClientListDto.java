package mx.com.naat.activityrecord.domain.data;

import java.io.Serializable;
import java.util.UUID;

public class ClientListDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;
	private String key;
	private String name;
	private String descripcion;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}