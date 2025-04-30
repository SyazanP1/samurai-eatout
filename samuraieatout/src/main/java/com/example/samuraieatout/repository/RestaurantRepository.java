package com.example.samuraieatout.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

	//	トップページhome.htmlで表示する店舗を取得
	List<Restaurant> findTop10ByOrderByIdDesc();

	//	検索　条件未入力時
	Page<Restaurant> findAllByOrderByIdAsc(Pageable pageable);
	
	//　検索　店舗名入力時
	Page<Restaurant> findByNameLikeOrderByIdAsc(String name, Pageable pageable);
	
	//　検索　カテゴリ選択時
	Page<Restaurant> findByCategoryOrderByIdAsc(Category category, Pageable pageable);
	
	//　検索　店舗名入力、かつ、カテゴリ選択時
	Page<Restaurant> findByNameLikeAndCategoryOrderByIdAsc(String name, Category category, Pageable pageable);
	
}
