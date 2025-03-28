DROP TABLE history_warranty;

CREATE TABLE history_warranty (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    car_model VARCHAR(100) NOT NULL,
    license_plate VARCHAR(9) NOT NULL UNIQUE,
    service_type VARCHAR(50) NOT NULL,
    service_center ENUM('Ho_Chi_Minh', 'Ha_Noi', 'Quy_Nhon') NOT NULL,
    service_date DATE NOT NULL,
    service_cost DECIMAL(10) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);