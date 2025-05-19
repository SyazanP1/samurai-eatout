package com.example.samuraieatout.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.repository.MemberRepository;

@Service
public class AdminMemberService {
	private MemberRepository memberRepository;

	public AdminMemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	//	名称検索結果を取得
	public Page<Member> obtainMember(String name, Pageable pageable) {
		if (name == null || name.isBlank()) {
			Page<Member> pageMember = memberRepository.findAllByOrderByIdAsc(pageable);
			return pageMember;
		}
		Page<Member> pageMember = memberRepository.findByNameLikeOrderByIdAsc("%" + name + "%", pageable);
		return pageMember;
	}
}
