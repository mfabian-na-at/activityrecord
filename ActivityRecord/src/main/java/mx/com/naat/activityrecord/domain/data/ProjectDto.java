package mx.com.naat.activityrecord.domain.data;

import java.io.Serializable;

public class ProjectDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private ClientDto idClient;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ClientDto getIdClient() {
		return idClient;
	}
	public void setIdClient(ClientDto idClient) {
		this.idClient = idClient;
	}
	
	

}