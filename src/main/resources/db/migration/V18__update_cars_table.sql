ALTER TABLE cars
DROP FOREIGN KEY cars_ibfk_3,
DROP COLUMN seller_id,
DROP COLUMN spare_parts_id;

ALTER TABLE history_purchase
DROP COLUMN sales_representative,
ADD COLUMN seller_id INT,
ADD CONSTRAINT fk_seller FOREIGN KEY (seller_id) REFERENCES admin(id);
