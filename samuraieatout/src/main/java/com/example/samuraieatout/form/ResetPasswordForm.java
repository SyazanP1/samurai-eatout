package com.example.samuraieatout.form;

import org.hibernate.validator.constraints.Length;

import com.example.samuraieatout.entity.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordForm {
	
	private Member member;

	@NotBlank(message = "パスワードが未入力です")
	@Length(min = 8, message = "パスワードは8文字以上で設定してください")
	private String password;
	
	private String passwordConfirm;
	
	public ResetPasswordForm(Member member) {
		this.member = member;
	}
}
