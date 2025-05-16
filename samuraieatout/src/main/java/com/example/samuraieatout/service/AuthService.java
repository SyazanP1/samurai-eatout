package com.example.samuraieatout.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.security.UserDetailsImpl;

@Service
public class AuthService {
	
//	private MemberRepository memberRepository;
	
//	public AuthService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}

	//	ログイン認証情報を更新
	public void updateSecurityContext(Member member) {
		//	https://spring.pleiades.io/spring-security/reference/servlet/authentication/architecture.html
		//	https://spring.pleiades.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContext.html
//		Integer memberId = userDetailsImpl.getMember().getId();
//		Member member = memberRepository.getReferenceById(memberId);
		SecurityContext context = SecurityContextHolder.getContext();
//		Authentication authentication = context.getAuthentication();
		//	（値の例）test2@a.co.jp
//		String username = authentication.getName();
		//	（値の例）com.example.samuraieatout.security.UserDetailsImpl@3389b6e8
//		Object principal = authentication.getPrincipal();
		
		//	（値の例）PAID
		//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(member.getAuthority().getName()));

//		UserDetails userDetails = User.builder()
//				.username(member.getName())
//				.password(member.getPassword())
//				.authorities(authorities)
//				.build();
		
		UserDetailsImpl updatedUserDetailsImpl = new UserDetailsImpl(member, authorities);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(updatedUserDetailsImpl, member.getPassword(), authorities);
		
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		context.setAuthentication(token);
		
		System.out.print(context);


	}
}
