package com.example.samuraieatout.service;

import org.springframework.stereotype.Service;

import com.example.samuraieatout.security.UserDetailsImpl;

@Service
public class HomeService {

	//	ログイン者がADMINであることを確認
	public Boolean isAuthorityADMIN(UserDetailsImpl userDetailsImpl) {
		if (userDetailsImpl != null) {
			Integer authorityId = userDetailsImpl.getMember().getAuthority().getId();
			//	権限がADMINの場合
			if (authorityId == 9) {
				return true;

			} else {
				//	権限がADMIN以外の場合
				return false;
			}

		} else {
			//	未ログインの場合
			return false;
		}
	}
}
