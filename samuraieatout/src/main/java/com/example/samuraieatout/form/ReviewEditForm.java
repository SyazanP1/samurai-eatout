package com.example.samuraieatout.form;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.entity.Restaurant;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
	
	private Integer id;

	private Restaurant restaurant;
	
	private Member member;
	
	private Integer score;
	
	@NotBlank(message = "内容が空白です。入力してください。")
	private String content;
}
