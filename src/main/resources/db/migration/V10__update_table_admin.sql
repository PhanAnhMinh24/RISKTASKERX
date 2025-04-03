ALTER TABLE admin
ADD COLUMN last_login DATETIME DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN department_name ENUM('Customer', 'Operations', 'Sales', 'IT') NOT NULL;