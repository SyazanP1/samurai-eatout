package com.example.samuraieatout.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Category;
import com.example.samuraieatout.repository.CategoryRepository;

@Service
public class CategoryService {
	private CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	//	カテゴリ選択用セレクトボックスのため
	public List<Category> obtainCategory(){
		List<Category> listCategory = categoryRepository.findByOrderByIdAsc();
		return listCategory;
	}
}
