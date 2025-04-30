package com.example.samuraieatout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraieatout.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{

	public Member findByEmail(String email);
}
