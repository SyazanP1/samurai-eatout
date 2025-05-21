package com.example.samuraieatout.form;

import lombok.Data;

@Data
public class ConfirmMemberForm {
	
	private Integer id;

	private String name;
	
	private String email;
	
	public ConfirmMemberForm(Integer id,String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
}
