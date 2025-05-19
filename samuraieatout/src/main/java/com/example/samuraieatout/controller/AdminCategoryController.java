package com.example.samuraieatout.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.form.CategoryEditForm;
import com.example.samuraieatout.form.CategoryRegistForm;
import com.example.samuraieatout.service.AdminCategoryService;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
	private final AdminCategoryService adminCategoryService;
	
	public AdminCategoryController(AdminCategoryService adminCategoryService) {
		this.adminCategoryService = adminCategoryService;
	}

	@GetMapping
	public String showCategory(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			@RequestParam(name = "keywordName", required = false) String keywordName,
			Model model) {
		Page<Category> pageCategory = adminCategoryService.obtainSearchCategory(keywordName, pageable);
		model.addAttribute("pageCategory", pageCategory);
		
		return "admin/category/home";
	}
	
	@GetMapping("/input")
	public String inputCategory(Model model) {
		CategoryRegistForm categoryRegistForm = new CategoryRegistForm();
		model.addAttribute("categoryRegistForm", categoryRegistForm);
		
		return "/admin/category/regist";
	}
	
	@PostMapping("/regist")
	public String registCategory(@ModelAttribute @Validated CategoryRegistForm categoryRegistForm,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			return "/admin/category/regist";
		}
		adminCategoryService.registCategory(categoryRegistForm);
		redirectAttributes.addFlashAttribute("registMessage", "カテゴリが新規登録されました。");
		
		return "redirect:/admin/category";
	}
	
	@GetMapping("/edit/{categoryId}")
	public String editCategory(@PathVariable(name = "categoryId") Integer categoryId, Model model) {
		Category category = adminCategoryService.obtainCategory(categoryId);
		CategoryEditForm categoryEditForm = new CategoryEditForm(categoryId, category.getName(), category.getEnable());
		model.addAttribute("categoryEditForm", categoryEditForm);
		
		return "/admin/category/edit";
	}
	
	@PostMapping("/update")
	public String updateCategory(@ModelAttribute @Validated CategoryEditForm categoryEditForm,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			return "/admin/category/edit";
		}
		adminCategoryService.updateCategory(categoryEditForm);
		redirectAttributes.addFlashAttribute("updateMessage", "カテゴリが更新されました。");
		
		return "redirect:/admin/category";
	}
	
	@PostMapping("/delete/{categoryId}")
	public String deleteCategory(@PathVariable(name = "categoryId") Integer categoryId, RedirectAttributes redirectAttributes) {
		adminCategoryService.deleteCategory(categoryId);
		redirectAttributes.addFlashAttribute("deleteMessage", "カテゴリを無効にしました。");
		
		return "redirect:/admin/category";
	}
}
