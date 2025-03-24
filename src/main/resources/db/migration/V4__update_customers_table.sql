ALTER TABLE customers
ADD COLUMN tier ENUM('Silver', 'Diamond', 'Bronze') NOT NULL DEFAULT 'Bronze',
ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT FALSE,
DROP COLUMN loyalty_points;