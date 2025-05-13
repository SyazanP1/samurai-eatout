package com.example.samuraieatout.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{

	//	予約一覧画面に表示する用に取得
	Page<Reservation> findByMemberOrderByDateAsc(Member member, Pageable pageable);
}
