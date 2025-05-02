package com.example.samuraieatout.controller;

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

import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.entity.Review;
import com.example.samuraieatout.form.ReviewEditForm;
import com.example.samuraieatout.form.ReviewRegistForm;
import com.example.samuraieatout.security.UserDetailsImpl;
import com.example.samuraieatout.service.RestaurantService;
import com.example.samuraieatout.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {
	private final ReviewService reviewService;
	private final RestaurantService restaurantService;

	public ReviewController(ReviewService reviewService, RestaurantService restaurantService) {
		this.reviewService = reviewService;
		this.restaurantService = restaurantService;
	}

	@GetMapping("/list/{restaurantId}")
	public String showReview(
			@PathVariable(name = "restaurantId") Integer restaurantId,
			@PageableDefault(page = 0, size = 4, sort = "updatedAt", direction = Direction.DESC) Pageable pageable,
			Model model) {

		Restaurant restaurant = restaurantService.obtainRestaurant(restaurantId);
		Page<Review> pageReview = reviewService.obtainAllReview(restaurant, pageable);

		model.addAttribute("pageReview", pageReview);
		model.addAttribute("restaurantId", restaurantId);

		return "review/list";
	}

	@GetMapping("/input/{restaurantId}")
	public String inputReview(
			@PathVariable(name = "restaurantId") Integer restaurantId,
			Model model) {

		Restaurant restaurant = restaurantService.obtainRestaurant(restaurantId);
		ReviewRegistForm reviewRegistForm = reviewService.createRegistFrom(restaurant);

		model.addAttribute("reviewRegistForm", reviewRegistForm);
		return "review/regist";
	}

	@PostMapping("/regist")
	public String registReview(@ModelAttribute @Validated ReviewRegistForm reviewRegistForm,
			BindingResult bindingResult,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			RedirectAttributes redirectAttributes,
			Model model) {

		Integer restaurantId = reviewRegistForm.getRestaurant().getId();

		if (bindingResult.hasErrors()) {
			//	エラー原因究明のため追加
			bindingResult.getAllErrors().forEach(error -> {
				System.out.println(error.toString());
			});
			return "review/regist";
		}

		reviewService.registReview(reviewRegistForm, userDetailsImpl);
		//		model.addAttribute("successMessage", "レビューが投稿されました。");
		redirectAttributes.addAttribute("successMessage", "レビューが投稿されました。");
		return "redirect:/review/list/" + restaurantId;
	}

	@GetMapping("/edit/{reviewId}")
	public String editReview(@PathVariable(name = "reviewId") Integer reviewId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			Model model) {

		//		Restaurant restaurant = restaurantService.obtainRestaurant(restaurantId);
		//		Review review = reviewService.obtainMyReview(restaurant, userDetailsImpl);
		Review review = reviewService.obtainEditReview(reviewId);

		ReviewEditForm reviewEditForm = reviewService.showEditReview(review);

		model.addAttribute("reviewEditForm", reviewEditForm);
		return "review/edit";
	}

	@PostMapping("/update")
	public String updateReview(@ModelAttribute @Validated ReviewEditForm reviewEditForm,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			//	エラー原因究明のため追加
			bindingResult.getAllErrors().forEach(error -> {
				System.out.println(error.toString());
			});
		}

		if (bindingResult.hasErrors()) {
//			model.addAttribute("editForm", editForm);
			return "review/edit";
		}

		String successMessage = reviewService.updateEditReview(reviewEditForm);

		model.addAttribute("successMessage", successMessage);
		model.addAttribute("reviewEditForm", reviewEditForm);
		return "review/edit";
	}

	@PostMapping("/delete/{reviewId}")
	public String delteReview(@PathVariable(name = "reviewId") Integer reviewId,
			RedirectAttributes redirectAttributes) {

		Restaurant restaurant = reviewService.obtainRestaurantFromReviewId(reviewId);
		String successMessage = reviewService.deleteReview(reviewId);

		redirectAttributes.addFlashAttribute("successMessage", successMessage);

		//		URLに変数を含めるためには、@RequestMappingがその変数を含んでいる必要があるため、今回は以下の形式では不可となる
		//		return "redirect:/restaurant/details/{restaurantId}";
		return "redirect:/restaurant/details/" + restaurant.getId();
	}
}
