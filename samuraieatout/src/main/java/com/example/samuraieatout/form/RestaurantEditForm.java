package com.example.samuraieatout.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantEditForm {
	
	@NotNull
	private Integer id;

	@NotBlank(message = "空白です。店舗名を入力してください。")
	@Size(max = 50, message = "50文字以内で入力してください。")
	private String name;
	
	private MultipartFile imageFile;

	@NotBlank(message = "カテゴリを選択してください。")
	private String categoryId;
	
	
	@NotBlank(message = "空白です。住所を入力してください。")
	@Size(max = 50, message = "50文字以内で入力してください。")
	private String address;
	
	@Size(max = 200, message = "200文字以内で入力してください。")
	private String fetures;
}
