package com.example.samuraieatout.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupForm {

	@NotBlank(message = "名前が未入力です")
	private String name;
	
	@NotBlank(message = "メールアドレスが未入力です")
	@Email(message = "メールアドレスとして正しい形式で入力してください")
	private String email;
	
	@NotBlank(message = "パスワードが未入力です")
	@Length(min = 8, message = "パスワードは8文字以上で入力してください")
	private String password;
	
	@NotBlank(message = "パスワードが未入力です")
	private String passwordConfirm;
}
