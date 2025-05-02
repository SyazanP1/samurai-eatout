package com.example.samuraieatout.form;

import com.example.samuraieatout.entity.Restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewRegistForm {
	
	private Restaurant restaurant;

	@NotNull(message = "点数を選択してください")
	private Integer score;
	
	@NotBlank(message = "空白です。レビューを記入してください。")
	@Size(max = 1000, message = "内容は1000文字以内で入力してください")
	private String content;
	
	public ReviewRegistForm(Restaurant restaurant) {
		//	レビュー登録対象となる店舗はフォームクラス作成時に確定するため、コンストラクタで初期化する
		this.restaurant = restaurant;
	}
}
