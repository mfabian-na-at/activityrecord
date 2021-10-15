package mx.com.naat.activityrecord.domain.data;

import java.io.Serializable;
import java.time.LocalDate;

public class ActivitiesListDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private ActivityDto activity;
	private int duration;
	private LocalDate date;
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
