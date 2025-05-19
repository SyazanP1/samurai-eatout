package com.example.samuraieatout.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.form.RestaurantEditForm;
import com.example.samuraieatout.form.RestaurantRegistForm;
import com.example.samuraieatout.service.AdminRestaurantService;
import com.example.samuraieatout.service.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminRestaurantController {
	private AdminRestaurantService adminRestaurantService;
	private CategoryService categoryService;

	public AdminRestaurantController(AdminRestaurantService adminRestaurantService, CategoryService categoryService) {
		this.adminRestaurantService = adminRestaurantService;
		this.categoryService = categoryService;
	}

	@GetMapping("/restaurant")
	public String showRestaurants(@RequestParam(name = "keywordName", required = false) String keywordName,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {

		//		Page<Restaurant> pageRestaurant = adminRestaurantService.showRestaurants(pageable);
		Page<Restaurant> pageRestaurant = adminRestaurantService.obtainSearchRestaurants(keywordName, pageable);

		model.addAttribute("keywordName", keywordName);
		model.addAttribute("pageRestaurant", pageRestaurant);
		return "admin/restaurant/home";
	}

	@GetMapping("/restaurant/input")
	public String inputRestaurant(Model model) {
		RestaurantRegistForm restaurantRegistForm = new RestaurantRegistForm();
		List<Category> listCategory = categoryService.obtainCategory();

		model.addAttribute("restaurantRegistForm", restaurantRegistForm);
		model.addAttribute("listCategory", listCategory);
		return "admin/restaurant/regist";
	}

	@PostMapping("/restaurant/regist")
	public String registRestaurant(@ModelAttribute @Valid RestaurantRegistForm restaurantRegistForm,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			List<Category> listCategory = categoryService.obtainCategory();
			model.addAttribute("listCategory", listCategory);
			return "admin/restaurant/regist";
		}

		adminRestaurantService.registNewRestaurant(restaurantRegistForm);
		redirectAttributes.addFlashAttribute("registMessage", "店舗を登録しました。");

		return "redirect:/admin/restaurant";
	}
	
	@GetMapping("/restaurant/edit/{restaurantId}")
	public String editRestaurant(@PathVariable(name = "restaurantId") Integer restaurantId,
			Model model) {
		Restaurant restaurant = adminRestaurantService.obtainRestaurant(restaurantId);
		String imageName = restaurant.getImageName();
		RestaurantEditForm restaurantEditForm = new RestaurantEditForm(restaurantId, restaurant.getName(), null, restaurant.getCategory().getId().toString(), restaurant.getAddress(), restaurant.getFetures());
		List<Category> listCategory = categoryService.obtainCategory();

		model.addAttribute("listCategory", listCategory);
		model.addAttribute("imageName", imageName);
		model.addAttribute("restaurantEditForm", restaurantEditForm);
		
		return "admin/restaurant/edit";
		
	}
	
	@PostMapping("/restaurant/update")
	public String updateRestaurant(@ModelAttribute @Valid RestaurantEditForm restaurantEditForm,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		
		if (bindingResult.hasErrors()) {
			List<Category> listCategory = categoryService.obtainCategory();
			model.addAttribute("listCategory", listCategory);
			return "admin/restaurant/edit";
		}
		
		adminRestaurantService.updateRestaurant(restaurantEditForm);
		redirectAttributes.addFlashAttribute("updateMessage", "店舗情報を更新しました。");

		return "redirect:/admin/restaurant";
	}
	
	@PostMapping("/restaurant/delete/{restaurantId}")
	public String deleteRestaurant(@PathVariable(name = "restaurantId") Integer restaurantId, RedirectAttributes redirectAttributes) {
		adminRestaurantService.deleteRestaurant(restaurantId);
		redirectAttributes.addFlashAttribute("deleteMessage", "店舗を削除しました。");
		
		return "redirect:/admin/restaurant";
	}

}
