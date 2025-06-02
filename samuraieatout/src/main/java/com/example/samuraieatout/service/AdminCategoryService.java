package com.example.samuraieatout.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.form.CategoryEditForm;
import com.example.samuraieatout.form.CategoryRegistForm;
import com.example.samuraieatout.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminCategoryService {
	private CategoryRepository categoryRepository;

	public AdminCategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	//	カテゴリ一覧の表示
	//	public Page<Category> showCategory(Pageable pageable) {
	//		Page<Category> pageCategory = categoryRepository.findAllByOrderByIdAsc(pageable);
	//		return pageCategory;
	//	}

	//	名称検索結果の取得
	public Page<Category> obtainSearchCategory(String name, Pageable pageable) {
		if (name == null || name.isBlank()) {
			Page<Category> pageCategory = categoryRepository.findAllByOrderByIdAsc(pageable);
			return pageCategory;

		} else {
			Page<Category> pageCategory = categoryRepository.findByNameLikeOrderByIdAsc("%" + name + "%", pageable);
			return pageCategory;
		}
	}

	//	新規登録
	@Transactional
	public void registCategory(CategoryRegistForm form) {
		Category category = new Category();
		category.setName(form.getName());
		category.setEnable(true);

		categoryRepository.save(category);
	}

	//	編集用に取得
	public Category obtainCategory(Integer categoryId) {
		Category category = categoryRepository.getReferenceById(categoryId);
		return category;
	}

	//	編集
	@Transactional
	public void updateCategory(CategoryEditForm form) {
		Category category = categoryRepository.getReferenceById(form.getId());
		category.setName(form.getName());
		category.setEnable(form.getEnable());

		categoryRepository.save(category);
	}

	//	削除
	@Transactional
	public void deleteCategory(Integer categoryId) {
		Category category = categoryRepository.getReferenceById(categoryId);
		category.setEnable(false);

		categoryRepository.save(category);
	}
	
	//	非同期更新
	@Transactional
	public void ajaxUpdateCategory(Integer categoryId, String categoryName) {
		Category category = categoryRepository.getReferenceById(categoryId);
		category.setName(categoryName);
		categoryRepository.save(category);
	}

}
