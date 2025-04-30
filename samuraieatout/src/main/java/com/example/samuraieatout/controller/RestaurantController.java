package com.example.samuraieatout.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.entity.Review;
import com.example.samuraieatout.form.ReservationInputForm;
import com.example.samuraieatout.repository.CategoryRepository;
import com.example.samuraieatout.repository.RestaurantRepository;
import com.example.samuraieatout.repository.ReviewRepository;
import com.example.samuraieatout.security.UserDetailsImpl;
import com.example.samuraieatout.service.RestaurantService;
import com.example.samuraieatout.service.ReviewService;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
	private final RestaurantRepository restaurantRepository;
	private final RestaurantService restaurantService;
	private final CategoryRepository categoryRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	
	public RestaurantController(RestaurantRepository restaurantRepository, RestaurantService restaurantService, CategoryRepository categoryRepository, ReviewRepository reviewRepository, ReviewService reviewService) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantService = restaurantService;
		this.categoryRepository = categoryRepository;
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
	}

	@GetMapping("/search")
	public String search(
			@RequestParam(name = "keywordName", required = false) String keywordName,
			@RequestParam(name = "keyidCategory",required = false) Integer keyidCategory,
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		
		//　RequestParamに応じて店舗Pageを作成する
		Page<Restaurant> pageRestaurants = restaurantService.searchRestaurant(keywordName, keyidCategory, pageable);
		
		// カテゴリ検索　セレクトボックス用
		List<Category> listCategories = categoryRepository.findByOrderByIdAsc();
		
		// カテゴリ検索　前回検索条件表示用
//		String keywordCategory = restaurantService.searcedCategoryName(keyidCategory);
		
		model.addAttribute("pageRestaurant", pageRestaurants);
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("keywordName", keywordName);
		model.addAttribute("keyidCategory", keyidCategory);
//		model.addAttribute("keywordCategory", keywordCategory);
		return "restaurant/searchResult";
	}
	
	@GetMapping("/details/{restaurantId}")
	public String showDetails(@PathVariable(name = "restaurantId") Integer restaurantId, 
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			Model model) {
		
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		Category category = restaurant.getCategory();
		
		//　画面下部に表示するレビュー
//		List<Review> listReview = reviewRepository.findTop6ByRestaurantOrderByUpdatedAt(restaurant);
		List<Review> listReview = reviewService.obtainTopReview(restaurant, userDetailsImpl);
		
		//	画面下部に表示するログイン者自身のレビュー
		Review myReview = reviewService.obtainMyReview(restaurant, userDetailsImpl);
		
		//　予約フォーム
		ReservationInputForm reservationInputForm = new ReservationInputForm();
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("category", category);
		model.addAttribute("listReview", listReview);
		model.addAttribute("myReview", myReview);
		model.addAttribute("reservationInputForm", reservationInputForm);
		return "restaurant/details";
	}
	
}
