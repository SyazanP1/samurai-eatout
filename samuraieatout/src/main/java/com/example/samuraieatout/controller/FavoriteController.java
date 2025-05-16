package com.example.samuraieatout.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraieatout.entity.Favorite;
import com.example.samuraieatout.security.UserDetailsImpl;
import com.example.samuraieatout.service.AuthService;
import com.example.samuraieatout.service.FavoriteService;
import com.example.samuraieatout.service.RestaurantService;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {
	private FavoriteService favoriteService;
	private RestaurantService restaurantService;
	private AuthService authService;
	
	public FavoriteController(FavoriteService favoriteService, RestaurantService restaurantService, AuthService authService) {
		this.favoriteService = favoriteService;
		this.restaurantService = restaurantService;
		this.authService = authService;
	}

	@PostMapping("/regist/{restaurantId}")
	public String registFavorite(@PathVariable(name = "restaurantId") Integer restaurantId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			RedirectAttributes redirectAttributes) {
		
		favoriteService.registFavorite(userDetailsImpl.getMember(), restaurantService.obtainRestaurant(restaurantId));
		
		return "redirect:/restaurant/details/" + restaurantId;
	}

	@PostMapping("/delete/{restaurantId}")
	public String deleteFavorite(@PathVariable(name = "restaurantId") Integer restaurantId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			RedirectAttributes redirectAttributes) {
		
		favoriteService.deleteFavorite(userDetailsImpl.getMember(), restaurantService.obtainRestaurant(restaurantId));
		
		return "redirect:/restaurant/details/" + restaurantId;
	}
	
	@GetMapping("/list")
	public String showFavorites(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		
		Page<Favorite> pageFavorites = favoriteService.obtainAllFavorites(userDetailsImpl.getMember(), pageable);
		
		model.addAttribute("pageFavorites", pageFavorites);
		return "favorite/list";
	}

	@PostMapping("/delete/list/{restaurantId}")
	public String deleteListFavorite(@PathVariable(name = "restaurantId") Integer restaurantId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			RedirectAttributes redirectAttributes) {
		
		favoriteService.deleteFavorite(userDetailsImpl.getMember(), restaurantService.obtainRestaurant(restaurantId));
		
		return "redirect:/favorite/list";
	}
}
