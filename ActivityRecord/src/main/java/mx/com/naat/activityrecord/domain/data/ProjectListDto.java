package mx.com.naat.activityrecord.domain.data;

import java.io.Serializable;

public class ProjectListDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private ClientDto client;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ClientDto getClient() {
		return client;
	}
	public void setClient(ClientDto client) {
		this.client = client;
	}
	
}
