-- categories
INSERT IGNORE INTO categories VALUES (1,'和食',true);
INSERT IGNORE INTO categories VALUES (2,'中華',true);
INSERT IGNORE INTO categories VALUES (3,'洋食',true);
INSERT IGNORE INTO categories VALUES (4,'イタリア料理',true);
INSERT IGNORE INTO categories VALUES (5,'フランス料理',true);

-- restaurants
INSERT IGNORE INTO restaurants VALUES (1,1,'店舗名その1','image1.jpg','住所その1','店舗の特徴について1');
INSERT IGNORE INTO restaurants VALUES (2,2,'店舗名その2','image2.jpg','住所その2','店舗の特徴について2');
INSERT IGNORE INTO restaurants VALUES (3,3,'店舗名その3','image3.jpg','住所その3','店舗の特徴について3');
INSERT IGNORE INTO restaurants VALUES (4,4,'店舗名その4','image4.jpg','住所その4','店舗の特徴について4');
INSERT IGNORE INTO restaurants VALUES (5,5,'店舗名その5','image5.jpg','住所その5','店舗の特徴について5');
INSERT IGNORE INTO restaurants VALUES (6,5,'店舗名その6','image6.jpg','住所その6','店舗の特徴について6');
INSERT IGNORE INTO restaurants VALUES (7,5,'店舗名その7','image7.jpg','住所その7','店舗の特徴について7');
INSERT IGNORE INTO restaurants VALUES (8,1,'店舗名その8','image8.jpg','住所その8','店舗の特徴について8');
INSERT IGNORE INTO restaurants VALUES (9,1,'店舗名その9','image9.jpg','住所その9','店舗の特徴について9');
INSERT IGNORE INTO restaurants VALUES (10,1,'店舗名その10','image10.jpg','住所その10','店舗の特徴について10');
INSERT IGNORE INTO restaurants VALUES (11,1,'店舗名その11','image11.jpg','住所その11','店舗の特徴について11');
INSERT IGNORE INTO restaurants VALUES (12,1,'店舗名その12','image12.jpg','住所その12','店舗の特徴について12');
INSERT IGNORE INTO restaurants VALUES (13,1,'店舗名その13','image13.jpg','住所その13','店舗の特徴について13');
INSERT IGNORE INTO restaurants VALUES (14,1,'店舗名その14','image14.jpg','住所その14','店舗の特徴について14');
INSERT IGNORE INTO restaurants VALUES (15,1,'店舗名その15','image15.jpg','住所その15','店舗の特徴について15');
INSERT IGNORE INTO restaurants VALUES (16,1,'店舗名その16','image16.jpg','住所その16','店舗の特徴について16');
INSERT IGNORE INTO restaurants VALUES (17,3,'店舗名その17','image17.jpg','住所その17','店舗の特徴について17');
INSERT IGNORE INTO restaurants VALUES (18,3,'店舗名その18','image18.jpg','住所その18','店舗の特徴について18');
INSERT IGNORE INTO restaurants VALUES (19,3,'店舗名その19','image19.jpg','住所その19','店舗の特徴について19');
INSERT IGNORE INTO restaurants VALUES (20,3,'店舗名その20','image20.jpg','住所その20','店舗の特徴について20');
INSERT IGNORE INTO restaurants VALUES (21,3,'店舗名その21','image21.jpg','住所その21','店舗の特徴について21');
INSERT IGNORE INTO restaurants VALUES (22,3,'店舗名その22','image22.jpg','住所その22','店舗の特徴について22');
INSERT IGNORE INTO restaurants VALUES (23,3,'店舗名その23','image23.jpg','住所その23','店舗の特徴について23');
INSERT IGNORE INTO restaurants VALUES (24,3,'店舗名その24','image24.jpg','住所その24','店舗の特徴について24');
INSERT IGNORE INTO restaurants VALUES (25,3,'店舗名その25','image25.jpg','住所その25','店舗の特徴について25');
INSERT IGNORE INTO restaurants VALUES (26,3,'店舗名その26','image26.jpg','住所その26','店舗の特徴について26');
INSERT IGNORE INTO restaurants VALUES (27,3,'店舗名その27','image27.jpg','住所その27','店舗の特徴について27');
INSERT IGNORE INTO restaurants VALUES (28,4,'店舗名その28','image28.jpg','住所その28','店舗の特徴について28');
INSERT IGNORE INTO restaurants VALUES (29,4,'店舗名その29','image29.jpg','住所その29','店舗の特徴について29');
INSERT IGNORE INTO restaurants VALUES (30,4,'店舗名その30','image30.jpg','住所その30','店舗の特徴について30');

--authorities
INSERT IGNORE INTO authorities VALUES (1,'FREE');
INSERT IGNORE INTO authorities VALUES (2,'PAID');
INSERT IGNORE INTO authorities VALUES (9,'ADMIN');

--members
INSERT IGNORE INTO members (id, authority_id, email, password, name, enable) VALUES (1,1,'1@a.co.jp','a','無料会員',TRUE);
INSERT IGNORE INTO members (id, authority_id, email, password, name, enable) VALUES (2,1,'2@a.co.jp','a','無料会員',TRUE);
INSERT IGNORE INTO members (id, authority_id, email, password, name, enable) VALUES (3,2,'3@a.co.jp','a','有料会員',TRUE);
INSERT IGNORE INTO members (id, authority_id, email, password, name, enable) VALUES (4,2,'4@a.co.jp','a','有料会員',TRUE);
INSERT IGNORE INTO members (id, authority_id, email, password, name, enable) VALUES (5,2,'5@a.co.jp','a','有料会員',TRUE);
INSERT IGNORE INTO members (id, authority_id, email, password, name, enable) VALUES (6,2,'6@a.co.jp','a','有料会員',TRUE);
INSERT IGNORE INTO members (id, authority_id, email, password, name, enable) VALUES (7,2,'7@a.co.jp','a','有料会員',TRUE);
INSERT IGNORE INTO members (id, authority_id, email, password, name, enable) VALUES (8,9,'8@a.co.jp','a','管理者',TRUE);

--reviews
INSERT IGNORE INTO reviews (id, restaurant_id, member_id, score, content) VALUES (1,1,1,1,'レビュー内容1');
INSERT IGNORE INTO reviews (id, restaurant_id, member_id, score, content) VALUES (2,1,2,2,'レビュー内容2');
INSERT IGNORE INTO reviews (id, restaurant_id, member_id, score, content) VALUES (3,1,3,3,'レビュー内容3');
INSERT IGNORE INTO reviews (id, restaurant_id, member_id, score, content) VALUES (4,1,4,4,'レビュー内容4');
INSERT IGNORE INTO reviews (id, restaurant_id, member_id, score, content) VALUES (5,1,5,5,'レビュー内容5');
INSERT IGNORE INTO reviews (id, restaurant_id, member_id, score, content) VALUES (6,1,6,1,'レビュー内容6');
INSERT IGNORE INTO reviews (id, restaurant_id, member_id, score, content) VALUES (7,2,7,2,'レビュー内容7');

--reservations
INSERT IGNORE INTO reservations VALUES (1,1,1,'2024-4-20 10:30:00',1);
INSERT IGNORE INTO reservations VALUES (2,2,1,'2024-4-20 10:30:00',1);
INSERT IGNORE INTO reservations VALUES (3,1,2,'2024-4-21 10:00:00',2);
INSERT IGNORE INTO reservations VALUES (4,2,2,'2024-4-21 10:30:00',1);
INSERT IGNORE INTO reservations VALUES (5,1,3,'2024-4-20 10:30:00',2);
