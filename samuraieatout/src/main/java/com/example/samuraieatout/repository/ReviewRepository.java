package com.example.samuraieatout.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

	//　店舗詳細ページdetails.htmlで表示するレビューを取得
	List<Review> findTop6ByRestaurantOrderByUpdatedAt(Restaurant restaurant);
	
	//	店舗詳細ページで表示するログイン者自身のレビューを取得
	Review findByRestaurantAndMember(Restaurant restaurant, Member member);
	
	//	レビュー一覧ページで表示するレビューを取得
	Page<Review> findByRestaurantOrderByUpdatedAt(Restaurant restaurant, Pageable pageable);
}
