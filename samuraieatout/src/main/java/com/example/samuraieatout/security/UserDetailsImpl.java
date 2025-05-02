package com.example.samuraieatout.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.samuraieatout.entity.Member;

public class UserDetailsImpl implements UserDetails{
	
	private final Member member;
	private final Collection<GrantedAuthority> authorities;
	
	public UserDetailsImpl(Member member, Collection<GrantedAuthority> authorities) {
		this.member = member;
		this.authorities = authorities;
	}
	
	public Member getMember() {
		return member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 自動生成されたメソッド・スタブ
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO 自動生成されたメソッド・スタブ
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO 自動生成されたメソッド・スタブ
		return member.getEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		//	メール認証に成功しているメンバーのみログイン可能
		return member.getEnable();
	}
}
