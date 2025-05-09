package com.example.samuraieatout.service;

import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Certification;
import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.repository.CertificationRepository;

import jakarta.transaction.Transactional;

@Service
public class CertificationService {
	private final CertificationRepository certificationRepository;
	private final MemberService memberService;

	public CertificationService(CertificationRepository certificationRepository, MemberService memberService) {
		this.certificationRepository = certificationRepository;
		this.memberService = memberService;
	}

	//	認証用のレコードをcertificationsテーブルに作成
	@Transactional
	public void createRecord(Member member, String token) {
		Certification certification = new Certification();

		certification.setMember(member);
		certification.setToken(token);

		certificationRepository.save(certification);
	}

	//	認証用のレコードを削除
	@Transactional
	public void deleteRecord(Member member) {

		Certification certification = certificationRepository.findByMember(member);
		if (certification != null) {

			certificationRepository.delete(certification);
		}
	}

	//	トークンの文字列で検索した結果を取得する
	public Certification obtainCertification(String token) {
		return certificationRepository.findByToken(token);
	}

	//	メールで送信したURLによる認証で会員登録を完了させる
	public Boolean completeRegistMember(String token) {

		Certification certification = obtainCertification(token);

		if (certification != null) {
			Member member = certification.getMember();
			memberService.enableMember(member);
			return true;

		} else {
			return false;
		}

	}

	//	URLのトークンからMemberを取得
	public Member obtainMemberFromToken(String token) {
		Certification certification = obtainCertification(token);
		Member member = null;
		if (certification != null) {
			member = certification.getMember();
		}

		return member;
	}

}
