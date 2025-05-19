package com.example.samuraieatout.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraieatout.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//	トップページhome.htmlでの検索フォームにおけるセレクトボックス「カテゴリ検索」の選択肢
//	List<Category> findByOrderByIdAsc();
	List<Category> findByEnableNotOrderByIdAsc(Boolean enable);
	
	//	全カテゴリ
	Page<Category> findAllByOrderByIdAsc(Pageable pageable);
	
	//	名称で検索
	Page<Category> findByNameLikeOrderByIdAsc(String name, Pageable pageable);
}
