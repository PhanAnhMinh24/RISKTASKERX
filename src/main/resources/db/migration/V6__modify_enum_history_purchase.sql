ALTER TABLE history_purchase
MODIFY COLUMN payment_method ENUM('Credit_card', 'Bank_transfer', 'Cash') NOT NULL;
