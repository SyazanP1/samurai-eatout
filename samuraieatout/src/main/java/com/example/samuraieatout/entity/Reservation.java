package com.example.samuraieatout.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Column(name = "date")
//	private LocalDate date; 年月日だけではなく、時刻も必要なためTimestampに変更
	private Timestamp date;
	
	@Column(name = "number")
	private Integer number;

}
