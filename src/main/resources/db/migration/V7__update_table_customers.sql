ALTER TABLE customers
MODIFY COLUMN tier ENUM('Silver', 'Diamond', 'Bronze', 'Gold') NOT NULL DEFAULT 'Bronze';