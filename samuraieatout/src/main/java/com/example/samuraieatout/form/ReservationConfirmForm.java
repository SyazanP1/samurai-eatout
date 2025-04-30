package com.example.samuraieatout.form;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationConfirmForm {

	private Integer restaurantId;
	
	private Integer memberId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime date;
	
	private Integer number;
}
