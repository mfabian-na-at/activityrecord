package mx.com.naat.activityrecord.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.naat.activityrecord.domain.api.ActivityRecordServicePort;
import mx.com.naat.activityrecord.domain.service.ActivityRecordServiceImpl;
import mx.com.naat.activityrecord.domain.spi.ActivityRecordPersistencePort;
import mx.com.naat.activityrecord.infrastructure.adapter.ActivityRecordJpaAdapter;

@Configuration
public class ActivityRecordConfig {
	
	@Bean
	public ActivityRecordPersistencePort activityRecordsPersistence() {
		return new ActivityRecordJpaAdapter();
	}
	
	@Bean
	public ActivityRecordServicePort activityServicePort() {
		return new ActivityRecordServiceImpl(activityRecordsPersistence());
	}
	
}
