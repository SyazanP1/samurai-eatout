CREATE TABLE IF NOT EXISTS categories
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR (20) NOT NULL
);
CREATE TABLE IF NOT EXISTS restaurants
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   category_id INT,
   name VARCHAR (50) NOT NULL,
   image_name VARCHAR (20) NOT NULL,
   address VARCHAR (50) NOT NULL,
   fetures VARCHAR (200),
   FOREIGN KEY (category_id) REFERENCES categories (id)
);
CREATE TABLE IF NOT EXISTS authorities
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR (20) NOT NULL
);
CREATE TABLE IF NOT EXISTS members
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   authority_id INT NOT NULL,
   email VARCHAR (30) NOT NULL,
   password VARCHAR (30) NOT NULL,
   name VARCHAR (30) NOT NULL,
   enable BOOLEAN NOT NULL,
   FOREIGN KEY (authority_id) REFERENCES authorities (id)
);
CREATE TABLE IF NOT EXISTS reviews
(
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   restaurant_id INT NOT NULL,
   member_id INT NOT NULL,
   score INT NOT NULL,
   content TEXT NOT NULL,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   UNIQUE
   (
      restaurant_id,
      member_id
   ),
   FOREIGN KEY (restaurant_id) REFERENCES restaurants (id),
   FOREIGN KEY (member_id) REFERENCES members (id)
);
CREATE TABLE IF NOT EXISTS reservations
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
restaurant_id INT NOT NULL,
member_id INT NOT NULL,
date DATETIME NOT NULL,
number INT NOT NULL,
FOREIGN KEY (restaurant_id) REFERENCES restaurants (id),
FOREIGN KEY (member_id) REFERENCES members (id)
);
CREATE TABLE IF NOT EXISTS certifications
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
member_id INT NOT NULL,
token VARCHAR(255) NOT NULL,
FOREIGN KEY (member_id) REFERENCES members (id)
);