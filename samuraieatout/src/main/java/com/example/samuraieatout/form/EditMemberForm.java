package com.example.samuraieatout.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditMemberForm {
	
	@NotNull
	private Integer id;

	@NotBlank(message = "名前が未入力です")
	private String name;
	
//	@NotBlank(message = "メールアドレスが未入力です")
//	@Email(message = "メールアドレスとして正しい形式で入力してください")
//	private String email;
	
	@NotBlank(message = "パスワードが未入力です")
	@Length(min = 8, message = "パスワードは8文字以上で入力してください")
	private String password;
	
	@NotBlank(message = "パスワードが未入力です")
	private String passwordConfirm;
	
//	public EditMemberForm(Integer id,String name, String email) {
	public EditMemberForm(Integer id,String name) {
		this.id = id;
		this.name = name;
//		this.email = email;
	}
	
}
