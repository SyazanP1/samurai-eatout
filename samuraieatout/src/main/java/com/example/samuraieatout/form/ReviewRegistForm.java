package com.example.samuraieatout.form;

import com.example.samuraieatout.entity.Restaurant;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRegistForm {
	
	private Restaurant restaurant;

	private Integer score;
	
	@NotBlank(message = "空白です。レビューを記入してください。")
//	@NotNull(message = "nullです")
	private String content;
	
	public ReviewRegistForm(Restaurant restaurant) {
		//	レビュー登録対象となる店舗はフォームクラス作成時に確定するため、コンストラクタで初期化する
		this.restaurant = restaurant;
	}
}
