package com.example.samuraieatout.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.samuraieatout.entity.Member;

@Component
public class ResetPasswordEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public ResetPasswordEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishResetPasswordEvent(Member member, String requestUrl) {
		applicationEventPublisher.publishEvent(new ResetPasswordEvent(this, member, requestUrl));
	}
}
