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
