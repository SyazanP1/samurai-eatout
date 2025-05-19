package com.example.samuraieatout.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryEditForm {
	
	@NotNull
	private Integer id;

	@NotBlank(message = "空白です。カテゴリ名を入力してください。")
	@Size(max = 20, message = "20文字以内で入力してください。")
	private String name;
	
	private Boolean enable;
}
