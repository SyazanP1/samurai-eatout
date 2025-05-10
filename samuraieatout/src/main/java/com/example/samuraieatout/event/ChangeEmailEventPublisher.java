package com.example.samuraieatout.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.samuraieatout.entity.Member;

@Component
public class ChangeEmailEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public ChangeEmailEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishChangeEmailEvent(Member member, String requestUrl, String newEmail) {
		applicationEventPublisher.publishEvent(new ChangeEmailEvent(this, member, requestUrl, newEmail));
	}
}
