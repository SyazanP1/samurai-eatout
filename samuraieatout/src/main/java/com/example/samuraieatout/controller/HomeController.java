package com.example.samuraieatout.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.security.UserDetailsImpl;
import com.example.samuraieatout.service.CategoryService;
import com.example.samuraieatout.service.HomeService;
import com.example.samuraieatout.service.RestaurantService;

@Controller
//@RequestMapping("/")
public class HomeController {
	private final RestaurantService restaurantService;
	private final CategoryService categoryService;
	private final HomeService homeService;
	
	public HomeController(RestaurantService restaurantService, CategoryService categoryService, HomeService homeService) {
		this.restaurantService = restaurantService;
		this.categoryService = categoryService;
		this.homeService = homeService;
	}
	
//	@GetMapping("/home")
	@GetMapping("/")
	public String showHome(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model, RedirectAttributes redirectAttributes) {
		
		if (homeService.isAuthorityADMIN(userDetailsImpl)) {
			//	管理者用の画面にリダイレクト
			return "redirect:/admin/restaurant";
		}
		
		//	管理者以外、または未ログイン時
		List<Restaurant> listRestaurants = restaurantService.obtainList10Restaurant();
		List<Category> listCategories = categoryService.obtainCategory();
		
		model.addAttribute("listRestaurants", listRestaurants);
		model.addAttribute("listCategories", listCategories);
		return "home";
	}

}
