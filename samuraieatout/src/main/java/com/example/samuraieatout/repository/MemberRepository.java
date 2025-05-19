package com.example.samuraieatout.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraieatout.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{

	Member findByEmail(String email);
	
	Page<Member> findAllByOrderByIdAsc(Pageable pageable);
	
	Page<Member> findByNameLikeOrderByIdAsc(String name, Pageable pageable);
}
