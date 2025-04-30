package com.example.samuraieatout.form;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationInputForm {

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@NotNull(message = "日付を選択してください")
	private LocalDateTime date;
	
	@NotNull(message = "人数を選択してください")
	@Min(value = 1, message = "1人以上を選択してください")
	private Integer number;
}
