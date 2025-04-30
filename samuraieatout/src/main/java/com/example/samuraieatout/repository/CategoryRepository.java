package com.example.samuraieatout.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraieatout.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//	トップページhome.htmlでの検索フォームにおけるセレクトボックス「カテゴリ検索」の選択肢
	List<Category> findByOrderByIdAsc();
}
