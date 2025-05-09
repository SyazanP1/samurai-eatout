package com.example.samuraieatout.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordSendEmailForm {
	
	

	@NotBlank(message = "メールアドレスが未入力です")
	@Email(message = "メールアドレスを入力してください")
	private String email;
}
