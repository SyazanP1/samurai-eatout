package com.example.samuraieatout.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.repository.CategoryRepository;
import com.example.samuraieatout.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;
	private final CategoryRepository categoryRepository;

	public RestaurantService(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository) {
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
	}

	//　店舗名、カテゴリで検索する
	public Page<Restaurant> searchRestaurant(String name, Integer categoryId, Pageable pageable) {

		Page<Restaurant> pageRestaurant = null;

		if (!name.isEmpty() && categoryId == null) {
			//　店舗名条件のみ入力の場合
			pageRestaurant = restaurantRepository.findByNameLikeOrderByIdAsc("%" + name + "%", pageable);
		}

		else if (name.isEmpty() && categoryId != null) {
			//	カテゴリ条件のみ入力の場合
			Category category = categoryRepository.getReferenceById(categoryId);
			pageRestaurant = restaurantRepository.findByCategoryOrderByIdAsc(category, pageable);
		}

		else if (!name.isEmpty() && categoryId != null) {
			//　両条件が入力されている場合
			Category category = categoryRepository.getReferenceById(categoryId);
			pageRestaurant = restaurantRepository.findByNameLikeAndCategoryOrderByIdAsc("%" + name + "%", category,
					pageable);
		}

		else if (name.isEmpty() && categoryId == null) {
			//　両条件が未入力の場合
			pageRestaurant = restaurantRepository.findAllByOrderByIdAsc(pageable);
		}

		return pageRestaurant;
	}

	//	IDからRestaurantを取得する
	public Restaurant obtainRestaurant(Integer restaurantId) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		return restaurant;
	}

	//	店舗のカテゴリを取得する
	public Category obtainCategory(Integer restaurantId) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		Category category = restaurant.getCategory();
		return category;
	}

	
	//	トップページhomeに表示する店舗を取得
	public List<Restaurant> obtainList10Restaurant(){
		List<Restaurant> listRestaurant = restaurantRepository.findTop10ByOrderByIdDesc();
		return listRestaurant;
	}

}
