package mx.com.naat.activityrecord.domain.data;

import java.io.Serializable;

public class ActivityDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;

}
