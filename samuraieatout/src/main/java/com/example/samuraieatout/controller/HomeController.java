package com.example.samuraieatout.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.repository.CategoryRepository;
import com.example.samuraieatout.repository.RestaurantRepository;

@Controller
public class HomeController {
	private final RestaurantRepository restaurantRepository;
	private final CategoryRepository categoryRepository;
	
	public HomeController(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository) {
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping("/home")
	public String showHome(Model model) {
		
		// 店舗表示用
		List<Restaurant> listRestaurants = restaurantRepository.findTop10ByOrderByIdDesc();
		
		
		// カテゴリ検索　セレクトボックス用
//		List<Category> listCategories = categoryRepository.findByOrderByIdAsc();
		List<Category> listCategories = categoryRepository.findByEnableNotOrderByIdAsc(false);
		
		model.addAttribute("listRestaurants", listRestaurants);
		model.addAttribute("listCategories", listCategories);
		return "home";
	}

}
