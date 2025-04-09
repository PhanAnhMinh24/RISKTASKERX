CREATE TABLE admin_tokens (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID token',
  admin_id INT NOT NULL COMMENT 'Liên kết tới admin',
  access_token VARCHAR(500) NOT NULL COMMENT 'Token truy cập',
  refresh_token VARCHAR(500) NOT NULL COMMENT 'Token làm mới',
  expires_at DATETIME NOT NULL COMMENT 'Thời điểm token hết hạn',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo token',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
  FOREIGN KEY (admin_id) REFERENCES admin(id)
);
