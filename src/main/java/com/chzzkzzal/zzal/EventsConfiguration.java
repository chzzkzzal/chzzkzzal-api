package com.chzzkzzal.zzal;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class EventsConfiguration {
	private final ApplicationContext applicationContext;

	@Bean
	public InitializingBean eventsInitializer(){
		return  ()-> Events.setPublisher(applicationContext);
	}
}
