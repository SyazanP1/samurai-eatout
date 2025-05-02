package com.example.samuraieatout.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.example.samuraieatout.entity.Authority;
import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.event.SignupEventPublisher;
import com.example.samuraieatout.form.SignupForm;
import com.example.samuraieatout.repository.AuthorityRepository;
import com.example.samuraieatout.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class MemberService {
	final private MemberRepository memberRepository;
	final private AuthorityRepository authorityRepository;
	final private PasswordEncoder passwordEncoder;
	final private SignupEventPublisher signupEventPublisher;
	
	public MemberService(MemberRepository memberRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, SignupEventPublisher signupEventPublisher) {
		this.memberRepository = memberRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
		this.signupEventPublisher = signupEventPublisher;
	}

	//	仮会員登録
	@Transactional
	public Member registSignup(SignupForm signupForm) {
		Member member = new Member();
		Authority authority = authorityRepository.getReferenceById(1);
		
		member.setAuthority(authority);
		member.setEmail(signupForm.getEmail());
		member.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		member.setName(signupForm.getName());
		member.setEnable(false);
		
		return memberRepository.save(member);
	}
	
	//	認証メール送信イベントを発行
	public String publishCertificateMailEvent(SignupForm signupForm, HttpServletRequest httpServletRequest) {
		Member createdMember = registSignup(signupForm);
		String requestUrl = new String(httpServletRequest.getRequestURL());
		signupEventPublisher.publishSignupEvent(createdMember, requestUrl);
		
		return "認証メールを送信しました。メールに記載されたURLにアクセスして、会員登録を完了させてください。";
	}
	
	//	メールアドレスが重複することをチェック
	public boolean isEmailRegisterd(String email) {
		Member member = memberRepository.findByEmail(email);
		
		if(member != null) {
			return true;
		}

		 return false;
	}
	
	//	パスワードが一致することをチェック
	public boolean isSamePassword(SignupForm signupForm) {
		return signupForm.getPassword().equals(signupForm.getPasswordConfirm());
	}
	
	//	チェック結果に応じてエラー内容を追加
	public BindingResult addErrorBindingResult(BindingResult bindingResult, SignupForm signupForm) {
		
		//	メールアドレスが重複する場合エラーを追加
		if (isEmailRegisterd(signupForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "既に登録済みのメールアドレスです");
			bindingResult.addError(fieldError);
		}
		//	パスワードが一致しない場合エラーを追加
		if (!isSamePassword(signupForm)) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません");
			bindingResult.addError(fieldError);
		}
		
		return bindingResult;
	}
	
	//	メンバーを有効にする
	@Transactional
	public void enableMember(Member member) {
		member.setEnable(true);
		memberRepository.save(member);
	}
	
}
