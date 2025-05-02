package com.example.samuraieatout.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.service.CertificationService;

@Component
public class SignupEventListener {

	private final CertificationService certificationService;
	private final JavaMailSender javaMailSender;
	
	public SignupEventListener(CertificationService certificationService, JavaMailSender javaMailSender) {
		this.certificationService = certificationService;
		this.javaMailSender = javaMailSender;
	}
	
	@EventListener
	private void onSignupEvent(SignupEvent signupEvent) {
		Member member = signupEvent.getMember();
		String token = UUID.randomUUID().toString();
		certificationService.createRecord(member, token);
		
		String senderAddress = "nakanaka100@outlook.jp";
		String recipientAddress = member.getEmail();
		String subject = "メール認証";
		String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;
		String message = "以下のリンクをクリックして会員登録を完了してください。";
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(senderAddress);
		mailMessage.setTo(recipientAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(message + "\n" + confirmationUrl);
		javaMailSender.send(mailMessage);
	}
}

