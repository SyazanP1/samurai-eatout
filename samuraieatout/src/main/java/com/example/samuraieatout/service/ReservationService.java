package com.example.samuraieatout.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.entity.Reservation;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.form.ReservationConfirmForm;
import com.example.samuraieatout.repository.MemberRepository;
import com.example.samuraieatout.repository.ReservationRepository;
import com.example.samuraieatout.repository.RestaurantRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationService {
	private final RestaurantRepository restaurantRepository;
	private final MemberRepository memberRepository;
	private final ReservationRepository reservationRepository;
	
	public ReservationService(RestaurantRepository restaurantRepository, MemberRepository memberRepository, ReservationRepository reservationRepository) {
		this.restaurantRepository = restaurantRepository;
		this.memberRepository = memberRepository;
		this.reservationRepository = reservationRepository;
	}

	//　LocalDateTimeだとTが含まれてしまうため、ビューにはString型（Tを含まない）の日付で表示する
	public String removeWordT(LocalDateTime date) {

		String stringDate = date.toString().replace("T", " ");
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//		LocalDateTime removedTDate = LocalDateTime.parse(stringDate, formatter);
		
		return stringDate;
	}
	
	//	新規予約の登録
	public void registNewReservation(ReservationConfirmForm reservationConfirmForm) {
		
		Restaurant restaurant = restaurantRepository.getReferenceById(reservationConfirmForm.getRestaurantId());
		Member member = memberRepository.getReferenceById(reservationConfirmForm.getMemberId());
//		LocalDateTime date = reservationConfirmForm.getDate();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		String stringDate = date.format(formatter);
		Timestamp timestampDate = Timestamp.valueOf(reservationConfirmForm.getDate());
		
		Reservation reservation = new Reservation();
		
		reservation.setRestaurant(restaurant);
		reservation.setMember(member);
		reservation.setDate(timestampDate);
		reservation.setNumber(reservationConfirmForm.getNumber());
		
		reservationRepository.save(reservation);
	}
	
	//	予約一覧の表示
	public Page<Reservation> obtainAllReservation(Member member, Pageable pageable){
		Page<Reservation> pageReservations = reservationRepository.findByMemberOrderByDateAsc(member, pageable);
		return pageReservations;
	}
	
	//	削除
	@Transactional
	public void deleteReservation(Integer reservationId) {
		Reservation reservation = reservationRepository.getReferenceById(reservationId);
		reservationRepository.delete(reservation);
	}
}
