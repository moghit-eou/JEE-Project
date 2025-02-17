CREATE DATABASE IF NOT EXISTS restaurant;

USE restaurant;

DROP TABLE IF EXISTS receipt_has_dish;
DROP TABLE IF EXISTS cart_has_dish;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS receipt;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS category;


    CREATE TABLE role (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(32) NOT NULL UNIQUE
    );

    CREATE TABLE user (
        id INT AUTO_INCREMENT PRIMARY KEY,
        login VARCHAR(32) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        role_id INT NOT NULL DEFAULT 1,
        create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

        FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE ON UPDATE CASCADE
    );

    CREATE TABLE category(
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(32) NOT NULL UNIQUE
    );

    CREATE TABLE dish (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL UNIQUE,
        category_id INT NOT NULL,
        price INT NOT NULL,
        weight INT NOT NULL,
        description VARCHAR(1023),

        FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE
    );

    CREATE TABLE status (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(32) NOT NULL UNIQUE
    );

    CREATE TABLE receipt (
        id INT AUTO_INCREMENT PRIMARY KEY,
        user_id INT NOT NULL,
        status_id INT NOT NULL DEFAULT 1,
        total INT,
        manager_id INT,
        create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

        FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (manager_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (status_id) REFERENCES status(id) ON DELETE CASCADE ON UPDATE CASCADE
    );

    CREATE TABLE receipt_has_dish (
        receipt_id INT NOT NULL,
        dish_id INT NOT NULL,
        count INT NOT NULL DEFAULT 1,
        price INT,

        UNIQUE KEY (receipt_id, dish_id),
        FOREIGN KEY (receipt_id) REFERENCES receipt(id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (dish_id) REFERENCES dish(id) ON DELETE CASCADE ON UPDATE CASCADE
    );

    CREATE TABLE cart_has_dish (
        user_id INT NOT NULL,
        dish_id INT NOT NULL UNIQUE,
        count INT NOT NULL DEFAULT 1,

        FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (dish_id) REFERENCES dish(id) ON DELETE CASCADE ON UPDATE CASCADE
    );

-- ==========TRIGGERS==============
DROP TRIGGER IF EXISTS receipt_has_dish_set_price;

DELIMITER //
CREATE DEFINER = CURRENT_USER TRIGGER restaurant.receipt_has_dish_set_price BEFORE INSERT ON receipt_has_dish FOR EACH ROW
BEGIN
    SET NEW.price = (SELECT price FROM dish WHERE dish.id = NEW.dish_id);
    UPDATE receipt
    SET total = NEW.price * NEW.count + ifnull(total, 0) 
	WHERE id = NEW.receipt_id;
END//
DELIMITER ;

-- ==========INSERTING============= 
INSERT INTO role (name) VALUE ('client');
INSERT INTO role (name) VALUE ('manager');

-- login: admin pass: admin role: admin
INSERT INTO user (login, password, role_id) VALUE ('admin', 'admin', 2);

INSERT INTO status (name) VALUE ('new');
INSERT INTO status (name) VALUE ('approved');
INSERT INTO status (name) VALUE ('cooking');
INSERT INTO status (name) VALUE ('delivering');
INSERT INTO status (name) VALUE ('received');

INSERT INTO category (id, name)
VALUES (1, 'Pizza');

INSERT INTO dish (category_id, name, price, weight, description)
VALUES
    (1, 'Pizza Margherita', 30, 400, 'A classic pizza with tomato and cheese'),
    (1, 'Four Seasons Pizza', 50, 450, 'A pizza topped with a variety of ingredients for each season'),
    (1, 'Royal Pizza', 70, 500, 'A rich pizza with ham, mushrooms, and olives'),
    (1, 'Vegetarian Pizza', 75, 420, 'A pizza for lovers of fresh vegetables'),
    (1, 'Chicken Pizza', 60, 470, 'A pizza topped with chicken pieces and a special sauce'),
    (1, 'Seafood Pizza', 60, 480, 'A pizza with fresh seafood, perfect for fish lovers'),
    (1, 'Moroccan Pizza', 70, 460, 'A spicy pizza inspired by traditional Moroccan flavors');



INSERT INTO category (id, name)
VALUES
    (2, 'Tacos');

INSERT INTO dish (category_id, name, price, weight, description)
VALUES
    (2, 'Chicken Taco', 30, 220, 'A taco with grilled chicken, fresh salsa, and avocado'),
    (2, 'Beef Taco', 35, 230, 'A taco filled with seasoned beef, cheese, and lettuce'),
    (2, 'Fish Taco', 40, 210, 'A taco with crispy fish, tangy slaw, and lime crema'),
    (2, 'Vegetarian Taco', 28, 200, 'A taco loaded with fresh veggies, beans, and guacamole'),
    (2, 'Shrimp Taco', 45, 240, 'A taco with marinated shrimp, mango salsa, and cabbage'),
    (2, 'Pork Carnitas Taco', 50, 250, 'A taco with tender pulled pork, pickled onions, and cilantro'),
    (2, 'Barbacoa Taco', 48, 260, 'A taco with slow-cooked beef, chipotle sauce, and cotija cheese');

-- Insert into category table
INSERT INTO category (id, name)
VALUES
    (3, 'Pasta');

-- Insert into dish table
INSERT INTO dish (category_id, name, price, weight, description)
VALUES
    (3, 'Italian Carbonara', 80, 350, 'Classic Italian pasta with eggs, pancetta, and Parmesan cheese'),
    (3, 'Italian Pesto Pasta', 75, 280, 'Pasta tossed in a fresh basil pesto sauce, topped with pine nuts'),
    (3, 'Italian Lasagna', 90, 500, 'Layered pasta with rich meat sauce, béchamel, and melted mozzarella'),
    (3, 'Fettuccine Alfredo', 70, 320, 'Pasta with a creamy Alfredo sauce and Parmesan cheese'),
    (3, 'Spaghetti Bolognese', 85, 380, 'Spaghetti with a rich meat-based tomato sauce, topped with Parmesan'),
    (3, 'Penne Arrabbiata', 65, 270, 'Penne pasta in a spicy tomato sauce with garlic and red chili flakes'),
    (3, 'Seafood Linguine', 95, 400, 'Linguine pasta served with a mix of fresh seafood and garlic butter sauce');

-- Insert into category table
INSERT INTO category (id, name)
VALUES
    (4, 'Drinks');

-- Insert into dish table
INSERT INTO dish (category_id, name, price, weight, description)
VALUES
    (4, 'Freshly Squeezed Orange Juice', 45, 250, '100% pure juice from fresh oranges, pressed to order'),
    (4, 'Fresh Lemon Juice', 40, 250, 'Fresh lemon juice, mineral water, and a touch of sugar or honey'),
    (4, 'Tropical Pineapple Juice', 50, 250, 'Juice from ripe pineapples, rich in exotic flavors'),
    (4, 'Creamy Mango Juice', 55, 250, 'Fresh mango juice for a velvety texture'),
    (4, 'Espresso', 25, 100, 'A rich and intense shot of coffee'),
    (4, 'Cappuccino', 35, 150, 'Espresso topped with steamed milk and a layer of frothy foam'),
    (4, 'Tropical Bliss Smoothie', 60, 350, 'Mango, pineapple, coconut milk, and a touch of lime. An explosion of exotic flavors');


INSERT INTO category (id, name)
VALUES
    (5, 'Desserts');

-- Insert Dishes
INSERT INTO dish (category_id, name, price, weight, description)
VALUES
    (5, 'Moroccan Pastilla with Almond Cream', 50, 200, 'A delicate filo pastry filled with almond cream and cinnamon powder'),
    (5, 'Baklava Selection', 45, 180, 'Layers of crispy pastry, nuts, and honey syrup for a sweet indulgence'),
    (5, 'Chebakia Delight', 35, 150, 'Traditional Moroccan sesame cookies coated in honey and served with mint tea'),
    (5, 'Chocolate Fondant', 65, 220, 'Warm chocolate cake with a gooey center, served with vanilla ice cream'),
    (5, 'Lemon Tart', 55, 180, 'A buttery tart filled with tangy lemon cream, topped with fluffy meringue'),
    (5, 'Red Velvet Cake', 60, 230, 'Moist red velvet sponge cake topped with rich cream cheese frosting'),
    (5, 'Apple Crumble', 50, 200, 'Warm baked apple filling with a crumbly buttery topping, served with ice cream'),
    (5, 'Tiramisu', 55, 210, 'Classic Italian dessert with layers of coffee-soaked ladyfingers and mascarpone cream');
