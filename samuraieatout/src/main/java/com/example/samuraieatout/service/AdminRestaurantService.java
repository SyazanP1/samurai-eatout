package com.example.samuraieatout.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.samuraieatout.entity.Restaurant;
import com.example.samuraieatout.form.RestaurantEditForm;
import com.example.samuraieatout.form.RestaurantRegistForm;
import com.example.samuraieatout.repository.CategoryRepository;
import com.example.samuraieatout.repository.FavoriteRepository;
import com.example.samuraieatout.repository.ReservationRepository;
import com.example.samuraieatout.repository.RestaurantRepository;
import com.example.samuraieatout.repository.ReviewRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminRestaurantService {
	private RestaurantRepository restaurantRepository;
	private CategoryRepository categoryRepository;
	private ReviewRepository reviewRepository;
	private ReservationRepository reservationRepository;
	private FavoriteRepository favoriteRepository;

	public AdminRestaurantService(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository, ReviewRepository reviewRepository, ReservationRepository reservationRepository, FavoriteRepository favoriteRepository) {
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
		this.reviewRepository = reviewRepository;
		this.reservationRepository = reservationRepository;
		this.favoriteRepository = favoriteRepository;
	}

	//	店舗一覧を表示
//	public Page<Restaurant> showRestaurants(Pageable pageable) {
//		Page<Restaurant> pageRestaurant = restaurantRepository.findAllByOrderByIdAsc(pageable);
//		return pageRestaurant;
//	}

	//	店舗名検索の結果を取得
	public Page<Restaurant> obtainSearchRestaurants(String keywordName, Pageable pageable) {
		if (keywordName == null || keywordName.isBlank()) {
			Page<Restaurant> pageRestaurant = restaurantRepository.findAllByOrderByIdAsc(pageable);
			return pageRestaurant;

		} else {
			Page<Restaurant> pageRestaurant = restaurantRepository.findByNameLikeOrderByIdAsc("%" + keywordName + "%", pageable);
			return pageRestaurant;

		}
	}

	//	新規登録
	@Transactional
	public void registNewRestaurant(RestaurantRegistForm form) {
		Restaurant restaurant = new Restaurant();

		MultipartFile imageFile = form.getImageFile();
		
		if (!imageFile.isEmpty()) {
			String imageNmae = imageFile.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageNmae);
			Path filePaht = Paths.get("src/main/resources/static/images/" + hashedImageName);
			copyImageFile(imageFile, filePaht);
			restaurant.setImageName(hashedImageName);
		}
		
		restaurant.setCategory(categoryRepository.getReferenceById(Integer.parseInt(form.getCategoryId())));
		restaurant.setName(form.getName());
		restaurant.setAddress(form.getAddress());
		restaurant.setFetures(form.getFetures());
		restaurantRepository.save(restaurant);
	}
	
	//	UUIDを使って生成したファイル名を返す
	public String generateNewFileName(String fileName) {
		String[] fileNames = fileName.split("\\.");
		for (int i = 0; i < fileNames.length - 1; i++) {
			fileNames[i] = UUID.randomUUID().toString();
		}
		String hashedFileName = String.join(".", fileNames);
		return hashedFileName;
	}
	
	//	画像ファイルを指定ディレクトリにコピーする
	public void copyImageFile(MultipartFile imageFile, Path filePath) {
		try {
			Files.copy(imageFile.getInputStream(), filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//	編集
	@Transactional
	public void updateRestaurant(RestaurantEditForm form) {
		Restaurant restaurant = restaurantRepository.getReferenceById(form.getId());
		MultipartFile imageFile = form.getImageFile();

		if (!imageFile.isEmpty()) {
			String imageNmae = imageFile.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageNmae);
			Path filePaht = Paths.get("src/main/resources/static/images/" + hashedImageName);
			copyImageFile(imageFile, filePaht);
			restaurant.setImageName(hashedImageName);
		}

		restaurant.setCategory(categoryRepository.getReferenceById(Integer.parseInt(form.getCategoryId())));
		restaurant.setName(form.getName());
		restaurant.setAddress(form.getAddress());
		restaurant.setFetures(form.getFetures());
		restaurantRepository.save(restaurant);
	}

	//	削除
	@Transactional
	public void deleteRestaurant(Integer restaurantId) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		//	restaurantのidが外部キーとなっている他テーブルのレコードを左記に削除
		//	これをしないと外部キーエラーでrestaurantのテーブルを削除できない
		reviewRepository.deleteByRestaurant(restaurant);
		reservationRepository.deleteByRestaurant(restaurant);
		favoriteRepository.deleteByRestaurant(restaurant);
		restaurantRepository.delete(restaurant);
	}
	
	//	編集用に取得
	public Restaurant obtainRestaurant(Integer restaurantId) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		return restaurant;
	}
}
