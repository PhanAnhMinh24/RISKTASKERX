CREATE TABLE installments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    payments_id INT NOT NULL,
    payment_method ENUM('Credit_card', 'Bank_transfer', 'Cash'),
    installment_amount INT,
    installment_plan INT,
    remaining_installment_months INT,
    monthly_payment INT,
    due_date DATE,
    payment_date DATE,
    FOREIGN KEY (payments_id) REFERENCES payments(id)
);

ALTER TABLE payments
DROP COLUMN installment_amount,
DROP COLUMN installment_plan,
DROP COLUMN remaining_installment_months,
DROP COLUMN monthly_payment,
DROP COLUMN due_date;
