package com.example.samuraieatout.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.entity.Review;
import com.example.samuraieatout.form.ReviewEditForm;
import com.example.samuraieatout.form.ReviewRegistForm;
import com.example.samuraieatout.repository.ReviewRepository;
import com.example.samuraieatout.security.UserDetailsImpl;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	//	private final UserDetailsImpl userDetailsImpl;

	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
		//		this.userDetailsImpl = userDetailsImpl;
	}

	//	店舗詳細画面の下部に表示する6件のレビューを取得する
	public List<Review> obtainShow6Review(Restaurant restaurant, UserDetailsImpl userDetailsImpl) {

		List<Review> listReviews = reviewRepository.findTop6ByRestaurantOrderByUpdatedAt(restaurant);
		if (userDetailsImpl != null) {
			// 済ログイン
			Member member = userDetailsImpl.getMember();
			if (existeMyReview(restaurant, member)) {
				//	ログイン者のレビューが存在する
				Review myReview = obtainMyReview(restaurant, userDetailsImpl);
				if (listReviews.indexOf(myReview) != -1) {
					//	ログイン者のレビューが最新6件に含まれる
					listReviews.remove(listReviews.indexOf(myReview));
					return listReviews;
				} else {
					//	ログイン者のレビューが最新6件に含まれない
					return listReviews;
				}

			} else {
				//	ログイン者のレビューが存在しない
				return listReviews;
			}

		} else {
			//	未ログイン
			return listReviews;
		}

	}

	//	店舗詳細画面下部に表示するログイン者自身のレビューを取得する
	//	レビュー編集画面でも使用
	public Review obtainMyReview(Restaurant restaurant, UserDetailsImpl userDetailsImpl) {
		Review myReview = null;

		if (userDetailsImpl != null) {
			myReview = reviewRepository.findByRestaurantAndMember(restaurant, userDetailsImpl.getMember());
		}
		return myReview;
	}

	//	ログイン者のレビューが存在することを確認
	public Boolean existeMyReview(Restaurant restaurant, Member member) {

		Review myReview = reviewRepository.findByRestaurantAndMember(restaurant, member);
		if (myReview != null) {
			return true;
		} else {
			return false;
		}
	}

	//	レビュー一覧画面に表示するレビューを取得する
	public Page<Review> obtainAllReview(Restaurant restaurant, Pageable pageable) {

		Page<Review> pageReviews = reviewRepository.findByRestaurantOrderByUpdatedAt(restaurant, pageable);

		return pageReviews;
	}

	//	レビュー登録画面に渡すフォームクラスを作成
	public ReviewRegistForm createRegistFrom(Restaurant restaurant) {
		ReviewRegistForm form = new ReviewRegistForm(restaurant);
		return form;
	}

	//	レビューを登録
	@Transactional
	public void registReview(ReviewRegistForm registForm, UserDetailsImpl userDetailsImpl) {

		Restaurant restaurant = registForm.getRestaurant();
		Member member = userDetailsImpl.getMember();
		Review review = new Review();

		review.setRestaurant(restaurant);
		review.setMember(member);
		review.setScore(registForm.getScore());
		review.setContent(registForm.getContent());

		reviewRepository.save(review);
	}

	//	IDからレビューを取得
	public Review obtainEditReview(Integer reviewId) {
		Review review = reviewRepository.getReferenceById(reviewId);

		return review;
	}

	//	編集画面に表示するためのレビューフォーム
	public ReviewEditForm showEditReview(Review review) {
		//		Member member = userDetailsImpl.getMember();

		//		Integer score = review.getScore();
		//		String content = review.getContent();
		ReviewEditForm form = new ReviewEditForm(review.getId(), review.getRestaurant(), review.getMember(),
				review.getScore(), review.getContent());

		return form;
	}

	//	編集画面でのレビュー更新
	@Transactional
	public String updateEditReview(ReviewEditForm reviewEditForm) {

		Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
		review.setScore(reviewEditForm.getScore());
		review.setContent(reviewEditForm.getContent());
		reviewRepository.save(review);

		String successMessage = "レビューを更新しました";
		return successMessage;
	}

	//	レビューの削除
	@Transactional
	public String deleteReview(Integer reviewId) {
		reviewRepository.deleteById(reviewId);

		String successMessage = "レビューを削除しました";
		return successMessage;
	}

	//	レビューIDから店舗エンティティを取得
	public Restaurant obtainRestaurantFromReviewId(Integer reviewId) {
		Review review = reviewRepository.getReferenceById(reviewId);
		Restaurant restaurant = review.getRestaurant();

		return restaurant;
	}
}
