package com.chzzkzzal.zzal;

import org.springframework.context.ApplicationEventPublisher;

import lombok.RequiredArgsConstructor;

public class Events {
	private static  ApplicationEventPublisher publisher;
	static void setPublisher(ApplicationEventPublisher publisher){
		Events.publisher = publisher;
	}

	public static void raise(Object event){
		if(publisher != null){
			publisher.publishEvent(event);
		}
	}
}
