package com.example.samuraieatout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraieatout.entity.LocalStripe;
import com.example.samuraieatout.entity.Member;

public interface LocalStripeRepository extends JpaRepository<LocalStripe, Integer> {
	LocalStripe findByMember(Member member);
}
