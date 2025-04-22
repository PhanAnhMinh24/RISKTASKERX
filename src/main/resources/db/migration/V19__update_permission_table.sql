-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Clear existing permission data
TRUNCATE TABLE permission;

-- Enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- =========================================
-- Dashboard
-- =========================================
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('dashboard', 'Dashboard', 1, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('view_dashboard', 'View Dashboard', 2, 1);

-- =========================================
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('admin_management', 'Admin Management', 3, NULL);

-- Admin Management
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_admin_list', 'View Admin List', 4, 3),
  ('view_admin_details', 'View Admin Details', 5, 3),
  ('add_new_admin', 'Add New Admin', 6, 3),
  ('edit_admin', 'Edit Admin', 7, 3),
  ('delete_admin', 'Delete Admin', 8, 3);

-- Role Management Details
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('role_management', 'Role Management', 9, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_role_list', 'View Role List', 10, 9),
  ('view_role_details', 'View Role Details', 11, 9),
  ('add_new_role', 'Add New Role', 12, 9),
  ('edit_role', 'Edit Role', 13, 9),
  ('delete_role', 'Delete Role', 14, 9);

-- =========================================
-- Customer Management
-- =========================================
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('customer_management', 'Customer Management', 15, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_customer_list', 'View Customer List', 16, 15),
  ('view_detail_customer', 'View Customer Details', 17, 15),
  ('edit_customer', 'Edit Customer', 18, 15);

-- =========================================
-- Car Management
-- =========================================
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('car_management', 'Car Management', 19, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_car_list', 'View Car List', 20, 19),
  ('view_car_detail', 'View Car Detail', 21, 19),
  ('add_new_car', 'Add New Car', 22, 19),
  ('edit_car', 'Edit Car', 23, 19);

-- =========================================
-- Spare Parts Management
-- =========================================
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('spare_parts_management', 'Spare Parts Management', 24, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_spare_parts_list', 'View Spare Parts List', 25, 24),
  ('view_spare_parts_detail', 'View Spare Parts Detail', 26, 24),
  ('add_new_spare_parts', 'Add New Spare Parts', 27, 24),
  ('edit_spare_parts', 'Edit Spare Parts', 28, 24);

-- =========================================
-- Marketing Campaign Management
-- =========================================
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('marketing_campaign_management', 'Marketing Campaign Management', 29, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_marketing_campaign_management', 'View Marketing Campaign List', 30, 29),
  ('view_marketing_campaign_details', 'View Marketing Campaign Details', 31, 29),
  ('add_new_marketing_campaign', 'Add New Marketing Campaign', 32, 29),
  ('edit_marketing_campaign', 'Edit Marketing Campaign', 33, 29);

-- =========================================
-- Order Management
-- =========================================
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('order_management', 'Order Management', 34, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_order_list', 'View Order List', 35, 34),
  ('detail_order_management', 'View Order Detail', 36, 34),
  ('add_new_order', 'Add New Order', 37, 34),
  ('edit_order', 'Edit Order', 38, 34);

-- =========================================
-- Branch Management
-- =========================================
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('branch_management', 'Branch Management', 39, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_branch_management', 'View Branch List', 40, 39),
  ('detail_branch_list', 'View Branch Details', 41, 39),
  ('add_new_branch', 'Add New Branch', 42, 39),
  ('edit_branch', 'Edit Branch', 43, 39);

-- =========================================
-- Notification Management
-- =========================================
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('notification_management', 'Notification Management', 44, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_notification_list', 'View Notification List', 45, 44),
  ('view_notification_detail_management', 'View Notification Details', 46, 44),
  ('add_new_notification', 'Add New Notification', 47, 44),
  ('edit_notification', 'Edit Notification', 48, 44);
