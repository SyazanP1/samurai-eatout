package com.example.samuraieatout.event;

import org.springframework.context.ApplicationEvent;

import com.example.samuraieatout.entity.Member;

import lombok.Getter;

@Getter
public class ChangeEmailEvent extends ApplicationEvent{

	private Member member;
	private String requestUrl;
	private String newEmail;
	
	public ChangeEmailEvent(Object source, Member member, String requestUrl, String newEmail) {
		//	親クラスのコンストラクタを呼び出し、イベントの発生源（パブリッシャー）を渡す
		super(source);
		
		this.member = member;
		this.requestUrl = requestUrl;
		this.newEmail = newEmail;
	}
}
