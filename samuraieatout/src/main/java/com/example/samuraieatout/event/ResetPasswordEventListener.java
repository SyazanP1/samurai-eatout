package com.example.samuraieatout.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.service.CertificationService;

@Component
public class ResetPasswordEventListener {

	private final CertificationService certificationService;
	private final JavaMailSender javaMailSender;
	
	public ResetPasswordEventListener(CertificationService certificationService, JavaMailSender javaMailSender) {
		this.certificationService = certificationService;
		this.javaMailSender = javaMailSender;
	}
	
	@EventListener
	private void onResetPasswordEvent(ResetPasswordEvent resetPasswordEvent) {
		Member member = resetPasswordEvent.getMember();
		
		String token = UUID.randomUUID().toString();
		certificationService.deleteRecord(member);
		certificationService.createRecord(member, token);
		
		String senderAddress = "nakanaka100@outlook.jp";
		String recipientAddress = resetPasswordEvent.getMember().getEmail();
		String subject = "パスワードリセット";
		String confirmationUrl = resetPasswordEvent.getRequestUrl() + "/verify?token=" + token;
		String message = "以下のリンクをクリックして新しいパスワードを設定してください。";
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(senderAddress);
		mailMessage.setTo(recipientAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(message + "\n" + confirmationUrl);
		javaMailSender.send(mailMessage);
	}
}

