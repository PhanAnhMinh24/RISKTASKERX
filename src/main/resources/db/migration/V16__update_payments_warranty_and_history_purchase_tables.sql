DROP TABLE history_purchase;
DROP TABLE warranty;
DROP TABLE payments;

CREATE TABLE payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    payment_method ENUM('Credit_card', 'Bank_transfer', 'Cash') NOT NULL,
    payment_option ENUM('Full_payment', 'Installment') NOT NULL,
    invoice VARCHAR(20) NOT NULL,
    price FLOAT NOT NULL,
    initial_payment FLOAT,
    installment_amount FLOAT,
    installment_plan INT,
    remaining_installment_months INT,
    monthly_payment FLOAT,
    due_date DATE,
    payment_date DATE,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE warranty (
    id INT PRIMARY KEY AUTO_INCREMENT,
    car_id INT NOT NULL,
    customer_id INT NOT NULL,
    started_date DATE NOT NULL,
    expired_date DATE NOT NULL,
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE history_purchase (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    car_id INT NOT NULL,
    payment_id INT NOT NULL,
    warranty_id INT NOT NULL,
    vehicle_identification_number VARCHAR(20),
    purchase_date DATE NOT NULL,
    sales_representative VARCHAR(100),
    service_center ENUM('Ho_Chi_Minh', 'Ha_Noi', 'Quy_Nhon'),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (payment_id) REFERENCES payments(id),
    FOREIGN KEY (warranty_id) REFERENCES warranty(id)
);

ALTER TABLE orders
DROP COLUMN total_price;
