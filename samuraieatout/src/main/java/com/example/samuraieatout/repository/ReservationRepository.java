package com.example.samuraieatout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraieatout.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{

}
