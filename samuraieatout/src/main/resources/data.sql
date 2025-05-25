-- categories
INSERT IGNORE INTO `categories` (`id`,`name`,`enable`) VALUES (1,'和食',1);
INSERT IGNORE INTO `categories` (`id`,`name`,`enable`) VALUES (2,'中華',1);
INSERT IGNORE INTO `categories` (`id`,`name`,`enable`) VALUES (3,'洋食',1);
INSERT IGNORE INTO `categories` (`id`,`name`,`enable`) VALUES (4,'イタリア料理',1);
INSERT IGNORE INTO `categories` (`id`,`name`,`enable`) VALUES (5,'フランス料理',1);
INSERT IGNORE INTO `categories` (`id`,`name`,`enable`) VALUES (6,'テスト',0);
INSERT IGNORE INTO `categories` (`id`,`name`,`enable`) VALUES (7,'テスト２',1);
INSERT IGNORE INTO `categories` (`id`,`name`,`enable`) VALUES (8,'カテゴリ３aaabbb',1);
INSERT IGNORE INTO `categories` (`id`,`name`,`enable`) VALUES (9,'カテゴリ9変更後',0);


-- restaurants

INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (1,1,'店舗名その1','image1.jpg','住所その1','店舗の特徴について1');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (2,2,'店舗名その2','image2.jpg','住所その2','店舗の特徴について2');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (3,3,'店舗名その3','image3.jpg','住所その3','店舗の特徴について3');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (4,4,'店舗名その4','image4.jpg','住所その4','店舗の特徴について4');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (5,5,'店舗名その5','image5.jpg','住所その5','店舗の特徴について5');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (6,5,'店舗名その6','image6.jpg','住所その6','店舗の特徴について6');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (7,5,'店舗名その7','image7.jpg','住所その7','店舗の特徴について7');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (8,1,'店舗名その8','image8.jpg','住所その8','店舗の特徴について8');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (9,1,'店舗名その9','image9.jpg','住所その9','店舗の特徴について9');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (10,1,'店舗名その10','image10.jpg','住所その10','店舗の特徴について10');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (11,1,'店舗名その11','ed828682-5d61-4251-9d03-3a928039ceb0.jpg','住所その11','店舗の特徴について11');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (12,1,'店舗名その12','2efb9b8b-09a5-4b47-949c-61219a0433a9.jpg','住所その12','店舗の特徴について12');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (13,1,'店舗名その13','4fa8595e-2648-4907-9016-09d9bfdf86f2.jpg','住所その13','店舗の特徴について13');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (14,1,'店舗名その14','b90cedab-9c02-4a9b-9e06-ea1333a565b0.jpg','住所その14','店舗の特徴について14');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (15,1,'店舗名その15','c04b855c-74bf-4f4c-b920-dd2a8cf66976.jpg','住所その15','店舗の特徴について15');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (16,1,'店舗名その16','0972f5dc-493e-437e-a5ef-adc3bb496b2e.jpg','住所その16','店舗の特徴について16');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (17,3,'店舗名その17','84b82061-ea4e-4b74-b6d0-2743e398e8a4.jpg','住所その17','店舗の特徴について17');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (18,3,'店舗名その18','13272c9f-fd28-47e7-801f-239c19073b9f.jpg','住所その18','店舗の特徴について18');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (19,3,'店舗名その19','2d375d46-1af5-413a-b106-517f8406435e.jpg','住所その19','店舗の特徴について19');
INSERT IGNORE INTO `restaurants` (`id`,`category_id`,`name`,`image_name`,`address`,`fetures`) VALUES (20,3,'店舗名その20','26c43219-4cd9-4dc0-90d1-bb9ce0585890.jpg','住所その20','店舗の特徴について20');


--authorities
INSERT IGNORE INTO `authorities` (`id`,`name`) VALUES (1,'FREE');
INSERT IGNORE INTO `authorities` (`id`,`name`) VALUES (2,'PAID');
INSERT IGNORE INTO `authorities` (`id`,`name`) VALUES (9,'ADMIN');

--members

INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (1,2,'1@a.co.jp',NULL,'$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO','無料会員',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (2,1,'2@a.co.jp',NULL,'a','無料会員',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (3,2,'3@a.co.jp',NULL,'a','有料会員',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (4,2,'4@a.co.jp',NULL,'a','有料会員',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (5,2,'5@a.co.jp',NULL,'a','有料会員',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (6,2,'6@a.co.jp',NULL,'a','有料会員',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (7,2,'7@a.co.jp',NULL,'a','有料会員',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (8,9,'8@a.co.jp',NULL,'a','管理者',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (9,1,'9@a.co.jp',NULL,'$2a$10$Og2fdGQsal7kxmt.7wuCj.LbQiIkvmc2B7o3aW6eZXGN3GdLdu36C','aaaaaa',0);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (10,1,'10@a.co.jp',NULL,'$2a$10$bCiMv1BDR4YcUOrI6.Pm3.U2Z/jBdnAyqZzJUX3ulwFPxycpqZESW','aa',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (11,1,'11@a.co.jp',NULL,'$2a$10$JFOUu/SMmHwWj2EQprNINuqtETjZzz8szUVTpkBRh45yNgG2yo/ZK','aaa',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (12,1,'12@a.co.jp',NULL,'$2a$10$TcjLnyCdNs293J3wO.aXMuNI.dW/xpQynyYIpGiHnoeKT8Y48May2','aaa',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (13,1,'13@a.co.jp',NULL,'$2a$10$149OmN68Fyprpr4b6ZRasu9tt6WMZXwiz.J6vdOv0RPFWJ.XXOcxW','abcd',0);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (14,1,'14@a.co.jp',NULL,'$2a$10$1LnB9KzFQDAWdfPp10nlKeU03gX7ZyhKy/hu1ozJ7JtygFzqo0b5C','aaa',0);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (15,1,'15@a.co.jp',NULL,'$2a$10$BiPEpmrGxikXjU1V2Lmt6eds6oI5Nbfnh98B852nCiguWkTpWl4Py','aa',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (16,1,'16@a.co.jp',NULL,'$2a$10$XTi1xxIyhUwURpr0ZVXF6.WNnjGH/Ki//rJgsI36LZAdZI/Ob.M4K','aa',0);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (17,1,'17@a.co.jp',NULL,'$2a$10$5TVDjhyLQ3Dq71LrF.q9sucwQG3OZzCtzquM9b176g5dCl7v3Ch7O','aa',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (18,1,'18@a.co.jp',NULL,'$2a$10$.SWasfA1maCXlxDPRtAvu.sDImFw2eO9WLl8RHXTWS70/K6lFrPqm','ああ',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (19,1,'19@a.co.jp',NULL,'$2a$10$PJYYqQ2ExUE5a3y9GzYIgOo2vIlFb9V2Sh8pAIefmF/Ky/wgrv446','aa',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (20,1,'20@a.co.jp',NULL,'$2a$10$UM2yeXz0hcdmgJPwjw8kjOTBESHVKilfHGOJ84z4C28HhOhGsHr8i','a',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (21,9,'21@a.co.jp',NULL,'$2a$10$uUOssKHXKzkVIH3PV41XZu13kMh.9vcrO140Q1Dv0H1exRXWn71kO','aaa',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (22,2,'22@a.co.jp',NULL,'$2a$10$DeJiYRhi9Dp.ifmprwXHeOBQ9l9TCh7Yd2cNvZIT9yYylf4IvgMLG','22aaa',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (23,1,'23@a.co.jp',NULL,'$2a$10$3lJxxTYeSdrhMzPpw/vySeYSBCYyvQM.OOvmsTP5TYjVi2IwfGDGa','23aaa',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (24,1,'24@a.co.jp','24@a.co.jp','$2a$08$VcNycXz7xIhtlBqfzAv7gexh.EJ8K0GJX5fhTF63kExgPYISUueIK','24test',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (25,2,'test1@a.co.jp',NULL,'$2a$10$5ePy9f87mSKwwccWIiW.4.MHaaIBiV2mw66bS1jRCvk6bIAcE4nCy','test1edit',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (26,2,'test2@a.co.jp',NULL,'$2a$10$edSlSvMDdDdJNtu2.0yKj.fAR7heDYqRHy5XpaYR1sX6bWTFIDHDu','test2',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (27,9,'test9@a.co.jp',NULL,'$2a$08$Lpn3Nz9aFepey7l61Wor5epYiktcpd5c8VmHFqHS0Bh2ahK4bLLey','test9',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (28,1,'test11@a.co.jp',NULL,'$2a$10$7rnegKA4b08byLblA66uSucALEFQPGAW3LwlT3gCpAnOaY0ZDsBMq','test11aaacccc',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (29,1,'test12@a.co.jp',NULL,'$2a$10$RRhcNcTaFcgwkMB0PWst2uk1FFKIf05WMcaycR4RhXHMTPqUn1RCS','test12',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (30,1,'test14@a.co.jp','test14@a.co.jp','$2a$10$tQXvSIU7iFbzlM5Am.R4jO2bGnGG5TTDQNBkPe5l5KdPRFvRxKV/6','テスト13',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (31,1,'test15@a.co.jp',NULL,'$2a$10$0ZhCpbB3R/njS5PIHYI6NuUu6.dXm.tsWh1xbYH2dui263jEwMWvG','test15',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (32,1,'userFREE@example.com','userFREE@example.com','$2a$10$mjI8Oy.CFmQzgbgPfRkjCumITWZiW43p.lxomVJTdIvhOQJ6.uBne','侍　太郎',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (33,2,'userPAID@example.com',NULL,'$2a$10$sDKcr7OB2Kw3izdzOT.XMucghAn5B2Cd7fr9m60gpHV8FQIDrmOMq','侍　二郎',1);
INSERT IGNORE INTO `members` (`id`,`authority_id`,`email`,`temporary_email`,`password`,`name`,`enable`) VALUES (34,9,'userADMIN@example.com',NULL,'$2a$10$8q86X9N0nEQObFJBu0ZbburLIW6.2qBu87mPzzKEANbDGwyCu6IIO','侍　三郎',1);


--reviews

INSERT IGNORE INTO `reviews` (`id`,`restaurant_id`,`member_id`,`score`,`content`,`created_at`,`updated_at`) VALUES (1,1,1,1,'レビュー内容1','2025-05-01 06:03:27','2025-05-01 06:03:27');
INSERT IGNORE INTO `reviews` (`id`,`restaurant_id`,`member_id`,`score`,`content`,`created_at`,`updated_at`) VALUES (2,1,2,2,'レビュー内容2','2025-04-18 08:34:53','2025-04-18 08:34:53');
INSERT IGNORE INTO `reviews` (`id`,`restaurant_id`,`member_id`,`score`,`content`,`created_at`,`updated_at`) VALUES (3,1,3,3,'レビュー内容3','2025-04-18 08:34:53','2025-04-18 08:34:53');
INSERT IGNORE INTO `reviews` (`id`,`restaurant_id`,`member_id`,`score`,`content`,`created_at`,`updated_at`) VALUES (4,1,4,4,'レビュー内容4','2025-04-18 08:34:53','2025-04-18 08:34:53');
INSERT IGNORE INTO `reviews` (`id`,`restaurant_id`,`member_id`,`score`,`content`,`created_at`,`updated_at`) VALUES (5,1,5,5,'レビュー内容5','2025-04-18 08:34:53','2025-04-18 08:34:53');
INSERT IGNORE INTO `reviews` (`id`,`restaurant_id`,`member_id`,`score`,`content`,`created_at`,`updated_at`) VALUES (6,1,6,1,'レビュー内容6','2025-04-18 08:34:53','2025-04-18 08:34:53');
INSERT IGNORE INTO `reviews` (`id`,`restaurant_id`,`member_id`,`score`,`content`,`created_at`,`updated_at`) VALUES (7,2,7,2,'レビュー内容7','2025-04-18 08:34:53','2025-04-18 08:34:53');
INSERT IGNORE INTO `reviews` (`id`,`restaurant_id`,`member_id`,`score`,`content`,`created_at`,`updated_at`) VALUES (12,1,26,4,'test2による店舗1へのレビュー編集済み','2025-05-12 13:25:02','2025-05-14 09:51:12');
INSERT IGNORE INTO `reviews` (`id`,`restaurant_id`,`member_id`,`score`,`content`,`created_at`,`updated_at`) VALUES (23,3,25,3,'5/25登録aaaabbbbb','2025-05-25 14:36:26','2025-05-25 14:42:38');


--reservations

INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (1,1,1,'2024-04-20 10:30:00',1);
INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (2,2,1,'2024-04-20 10:30:00',1);
INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (3,1,2,'2024-04-21 10:00:00',2);
INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (4,2,2,'2024-04-21 10:30:00',1);
INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (5,1,3,'2024-04-20 10:30:00',2);
INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (7,1,1,'2025-04-29 12:00:00',9);
INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (14,8,28,'2025-05-27 12:00:00',3);
INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (18,17,33,'2025-05-27 12:00:00',2);
INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (19,12,33,'2025-05-30 12:00:00',3);
INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (21,20,33,'2025-05-29 12:00:00',3);
INSERT IGNORE INTO `reservations` (`id`,`restaurant_id`,`member_id`,`date`,`number`) VALUES (22,19,33,'2025-05-30 12:00:00',6);

