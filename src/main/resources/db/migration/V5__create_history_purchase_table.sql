ALTER TABLE customers
ADD COLUMN date_of_birth DATE NOT NULL;

CREATE TABLE history_purchase (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    car_model VARCHAR(50) NOT NULL,
    purchase_date DATE NOT NULL,
    payment_method ENUM('Credit card', 'Bank transfer', 'Cash') NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    warranty_months INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

