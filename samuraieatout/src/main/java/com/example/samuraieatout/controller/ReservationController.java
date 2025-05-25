package com.example.samuraieatout.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.entity.Reservation;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.entity.Review;
import com.example.samuraieatout.form.ReservationConfirmForm;
import com.example.samuraieatout.form.ReservationInputForm;
import com.example.samuraieatout.repository.CategoryRepository;
import com.example.samuraieatout.security.UserDetailsImpl;
import com.example.samuraieatout.security.UserDetailsServiceImpl;
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
			List<Review> listReview = reviewService.obtainShow6Review(restaurant, userDetailsImpl);

			model.addAttribute("restaurant", restaurant);
			model.addAttribute("category", category);
			model.addAttribute("listReview", listReview);
			return "restaurant/details";
		}
		
		//	日付からTを取り除いた表示用の日付
		String stringDate = reservationService.removeWordT(reservationInputForm.getDate());
		ReservationConfirmForm reservationConfirmForm = new ReservationConfirmForm(restaurantId, userDetailsImpl.getMember().getId(), reservationInputForm.getDate(), reservationInputForm.getNumber());

		model.addAttribute("reservationConfirmForm", reservationConfirmForm);
		model.addAttribute("stringDate", stringDate);
		return "reservation/confirm";
	}
	
	@PostMapping("/confirm")
	public String reservationConfirm(@ModelAttribute ReservationConfirmForm reservationConfirmForm, Model model) {
		
		//　DBに登録
		reservationService.registNewReservation(reservationConfirmForm);

//		return "redirect:/restaurant/details/" + reservationConfirmForm.getRestaurantId();
		return "redirect:/reservation/list?reserved";
	}
	
	@GetMapping("/list")
	public String showReservations(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			Model model,
			@PageableDefault(page = 0, size = 4, sort = "date", direction = Direction.ASC) Pageable pageable) {

		Page<Reservation> pageReservations = reservationService.obtainAllReservation(userDetailsImpl.getMember(), pageable);
		model.addAttribute("pageReservations", pageReservations);
		return "reservation/list";
	}
	
	@PostMapping("/delete/{reservationId}")
	public String deleteReservation(@PathVariable(name = "reservationId") Integer reservationId,
			@AuthenticationPrincipal UserDetailsServiceImpl userDetailsImpl,
			RedirectAttributes redirectAttributes) {
		
		reservationService.deleteReservation(reservationId);
		
		redirectAttributes.addFlashAttribute("deleteMessage", "予約を削除しました。");
		return "redirect:/reservation/list";
	}
}
