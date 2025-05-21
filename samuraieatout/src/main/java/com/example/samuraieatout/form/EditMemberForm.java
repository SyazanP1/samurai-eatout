package com.example.samuraieatout.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditMemberForm {
	
	@NotNull
	private Integer id;

	@NotBlank(message = "名前が未入力です")
	private String name;
	
	public EditMemberForm(Integer id,String name) {
		this.id = id;
		this.name = name;
	}
	
}
