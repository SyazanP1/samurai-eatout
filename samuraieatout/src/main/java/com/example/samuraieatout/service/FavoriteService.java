package com.example.samuraieatout.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Favorite;
import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.repository.FavoriteRepository;
import com.example.samuraieatout.security.UserDetailsImpl;

import jakarta.transaction.Transactional;

@Service
public class FavoriteService {
	private FavoriteRepository favoriteRepository;

	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}

	//	お気に入り登録
	@Transactional
	public void registFavorite(Member member, Restaurant restaurant) {
		Favorite favorite = new Favorite();
		favorite.setMember(member);
		favorite.setRestaurant(restaurant);

		favoriteRepository.save(favorite);
	}

	//	お気に入り解除
	@Transactional
	public void deleteFavorite(Member member, Restaurant restaurant) {
		Favorite favorite = favoriteRepository.findByMemberAndRestaurant(member, restaurant);
		favoriteRepository.delete(favorite);
	}

	//	お気に入り登録されていることを確認
	public Boolean isRegistedFavorite(UserDetailsImpl userDetailsImpl, Restaurant restaurant) {

		if (userDetailsImpl != null) {
			Favorite favorite = favoriteRepository.findByMemberAndRestaurant(userDetailsImpl.getMember(), restaurant);
			if (favorite != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	//	お気に入り一覧を取得
	public Page<Favorite> obtainAllFavorites(Member member, Pageable pageable) {
		return favoriteRepository.findByMemberOrderByIdAsc(member, pageable);
	}
}
