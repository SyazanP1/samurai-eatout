package com.example.samuraieatout.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRegistForm {
	
	@NotBlank(message = "空白です。カテゴリ名を入力してください。")
	@Size(max = 20, message = "20文字以内で入力してください。")
	private String name;
	
	private Boolean enable;
	
}
