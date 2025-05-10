package com.example.samuraieatout.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeEmailForm {
	
	@NotBlank(message = "メールアドレスが未入力です。")
	@Email(message = "メールアドレスとして正しい形式で入力してください。")
	private String email;

}
