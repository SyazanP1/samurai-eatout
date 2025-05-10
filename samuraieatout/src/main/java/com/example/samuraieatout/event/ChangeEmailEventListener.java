package com.example.samuraieatout.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.service.CertificationService;

@Component
public class ChangeEmailEventListener {

	private final CertificationService certificationService;
	private final JavaMailSender javaMailSender;
	
	public ChangeEmailEventListener(CertificationService certificationService, JavaMailSender javaMailSender) {
		this.certificationService = certificationService;
		this.javaMailSender = javaMailSender;
	}
	
	@EventListener
	private void onChangeEmailEvent(ChangeEmailEvent changeEmailEvent) {
		Member member = changeEmailEvent.getMember();
		String token = UUID.randomUUID().toString();
		certificationService.deleteRecord(member);
		certificationService.createRecord(member, token);
		
		String senderAddress = "nakanaka100@outlook.jp";
//		String recipientAddress = resetPasswordEvent.getMember().getEmail();
		String recipientAddress = changeEmailEvent.getNewEmail();
		String subject = "パスワードリセット";
		String confirmationUrl = changeEmailEvent.getRequestUrl() + "/verify?token=" + token;
		String message = "以下のリンクをクリックしてメールアドレスの変更を完了してください。";
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(senderAddress);
		mailMessage.setTo(recipientAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(message + "\n" + confirmationUrl);
		javaMailSender.send(mailMessage);
	}
}

