package com.example.samuraieatout.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.repository.MemberRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MemberRepository memberRepository;

	public UserDetailsServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			// TODO 自動生成されたメソッド・スタブ
			Member member = memberRepository.findByEmail(email);
			String memberRoleName = member.getAuthority().getName();
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(memberRoleName));
			return new UserDetailsImpl(member, authorities);
		} catch (Exception e) {
			throw new UsernameNotFoundException("ユーザーが見つかりません。");
		}
	}

}
