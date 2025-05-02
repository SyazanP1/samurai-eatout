package com.example.samuraieatout.event;

import org.springframework.context.ApplicationEvent;

import com.example.samuraieatout.entity.Member;

import lombok.Getter;

@Getter
public class SignupEvent extends ApplicationEvent{

	private Member member;
	private String requestUrl;
	
	public SignupEvent(Object source, Member member, String requestUrl) {
		//	親クラスのコンストラクタを呼び出し、イベントの発生源（パブリッシャー）を渡す
		super(source);
		
		this.member = member;
		this.requestUrl = requestUrl;
	}
}
