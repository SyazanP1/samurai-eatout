package com.example.samuraieatout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraieatout.entity.Certification;
import com.example.samuraieatout.entity.Member;

public interface CertificationRepository extends JpaRepository<Certification, Integer>{
	
	public Certification findByToken(String token);
	
	public Certification findByMember(Member member);

}
