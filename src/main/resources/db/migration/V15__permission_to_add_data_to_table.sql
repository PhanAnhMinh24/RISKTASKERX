-- ========================
-- V15_permission_to_add_data_to_table.sql
-- ========================

-- Dashboard
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('dashboard', 'Dashboard', 1, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('view_dashboard', 'View Dashboard', 2, 1);

-- Role & Permission
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('role_permission', 'Role&Permission', 3, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_role_permission', 'View Role&Permission', 4, 3),
  ('detail_role_permission', 'Detail Role&Permission', 5, 3),
  ('add_role_permission', 'Add Role&Permission', 6, 3),
  ('edit_role_permission', 'Edit Role&Permission', 7, 3);

-- Customer Management
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('customer_management', 'Customer Management', 8, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_customer_management', 'View Customer Management', 9, 8),
  ('detail_customer_management', 'Detail Customer Management', 10, 8),
  ('add_customer_management', 'Add Customer Management', 11, 8),
  ('edit_customer_management', 'Edit Customer Management', 12, 8);

-- Car Management
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('car_management', 'Car Management', 13, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_car_management', 'View Car Management', 14, 13),
  ('detail_car_management', 'Detail Car Management', 15, 13),
  ('add_car_management', 'Add Car Management', 16, 13),
  ('edit_car_management', 'Edit Car Management', 17, 13);

-- Spare Parts Management
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('spare_parts_management', 'Spare Parts Management', 18, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_spare_parts_management', 'View Spare Parts Management', 19, 18),
  ('detail_spare_parts_management', 'Detail Spare Parts Management', 20, 18),
  ('add_spare_parts_management', 'Add Spare Parts Management', 21, 18),
  ('edit_spare_parts_management', 'Edit Spare Parts Management', 22, 18);

-- Marketing Campaign Management
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('marketing_campaign_management', 'Marketing Campaign Management', 23, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_marketing_campaign_management', 'View Marketing Campaign Management', 24, 23),
  ('detail_marketing_campaign_management', 'Detail Marketing Campaign Management', 25, 23),
  ('add_marketing_campaign_management', 'Add Marketing Campaign Management', 26, 23),
  ('edit_marketing_campaign_management', 'Edit Marketing Campaign Management', 27, 23);

-- Order Management
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('order_management', 'Order Management', 28, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_order_management', 'View Order Management', 29, 28),
  ('detail_order_management', 'Detail Order Management', 30, 28),
  ('add_order_management', 'Add Order Management', 31, 28),
  ('edit_order_management', 'Edit Order Management', 32, 28);

-- Branch Management
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('branch_management', 'Branch Management', 33, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_branch_management', 'View Branch Management', 34, 33),
  ('detail_branch_management', 'Detail Branch Management', 35, 33),
  ('add_branch_management', 'Add Branch Management', 36, 33),
  ('edit_branch_management', 'Edit Branch Management', 37, 33);

-- Notification Management
INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES ('notification_management', 'Notification Management', 38, NULL);

INSERT INTO permission (`key`, `name`, order_number, parent_id)
VALUES
  ('view_notification_management', 'View Notification Management', 39, 38),
  ('detail_notification_management', 'Detail Notification Management', 40, 38),
  ('add_notification_management', 'Add Notification Management', 41, 38),
  ('edit_notification_management', 'Edit Notification Management', 42, 38);
