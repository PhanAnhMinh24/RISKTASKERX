ALTER TABLE history_purchase
MODIFY COLUMN payment_method ENUM('Credit_card', 'Bank_transfer', 'Cash', 'Installment') NOT NULL,
ADD COLUMN vehicle_identification_number VARCHAR(20) NOT NULL UNIQUE;

ALTER TABLE payments
MODIFY COLUMN payment_method ENUM('Credit_card', 'Bank_transfer', 'Cash', 'Installment') NOT NULL;
