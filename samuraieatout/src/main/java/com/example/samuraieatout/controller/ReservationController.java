package com.example.samuraieatout.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.entity.Review;
import com.example.samuraieatout.form.ReservationConfirmForm;
import com.example.samuraieatout.form.ReservationInputForm;
import com.example.samuraieatout.repository.CategoryRepository;
import com.example.samuraieatout.security.UserDetailsImpl;
import com.example.samuraieatout.service.ReservationService;
import com.example.samuraieatout.service.RestaurantService;
import com.example.samuraieatout.service.ReviewService;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

//	private final RestaurantRepository restaurantRepository;
	private final RestaurantService restaurantService;
//	private final CategoryRepository categoryRepository;
//	private final ReviewRepository reviewRepository;
	private final ReservationService reservationService;
	private final ReviewService reviewService;

	public ReservationController(RestaurantService restaurantService,
			CategoryRepository categoryRepository, ReservationService reservationService, ReviewService reviewService) {

//		this.restaurantRepository = restaurantRepository;
		this.restaurantService = restaurantService;
//		this.categoryRepository = categoryRepository;
//		this.reviewRepository = reviewRepository;
		this.reservationService = reservationService;
		this.reviewService = reviewService;
	}

	@GetMapping("/input/{restaurantId}")
	public String reservationInput(@ModelAttribute @Validated ReservationInputForm reservationInputForm,
			BindingResult bindingResult,
			@PathVariable(name = "restaurantId") Integer restaurantId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			Model model) {

		if (bindingResult.hasErrors()) {
			//　予約入力フォームで、入力エラーがあった場合同じページ（店舗詳細）を表示する

//			Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
//			Category category = restaurant.getCategory();
			Restaurant restaurant = restaurantService.obtainRestaurant(restaurantId);
			Category category = restaurantService.obtainCategory(restaurantId);
			

			//　画面下部に表示するレビュー
//			List<Review> listReview = reviewRepository.findTop6ByRestaurantOrderByUpdatedAt(restaurant);
			List<Review> listReview = reviewService.obtainTopReview(restaurant, userDetailsImpl);

			model.addAttribute("restaurant", restaurant);
			model.addAttribute("category", category);
			model.addAttribute("listReview", listReview);
			return "/restaurant/details";
		}
		
		//	日付からTを取り除いた表示用の日付
		String stringDate = reservationService.removeWordT(reservationInputForm.getDate());
		//　ログイン機能未実装のため、memberIdは固定値1を使用
		ReservationConfirmForm reservationConfirmForm = new ReservationConfirmForm(restaurantId, 1, reservationInputForm.getDate(), reservationInputForm.getNumber());

		model.addAttribute("reservationConfirmForm", reservationConfirmForm);
		model.addAttribute("stringDate", stringDate);
		return "/reservation/confirm";
	}
	
	@PostMapping("/confirm")
	public String reservationConfirm(@ModelAttribute ReservationConfirmForm reservationConfirmForm, Model model) {
		
		//　DBに登録
		reservationService.registNewReservation(reservationConfirmForm);

		return "redirect:/restaurant/details/" + reservationConfirmForm.getRestaurantId();
	}
}
