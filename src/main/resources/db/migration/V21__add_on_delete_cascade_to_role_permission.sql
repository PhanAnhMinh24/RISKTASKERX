ALTER TABLE role_permission DROP FOREIGN KEY role_permission_ibfk_1;

ALTER TABLE role_permission
ADD CONSTRAINT role_permission_ibfk_1
FOREIGN KEY (role_id) REFERENCES roles(id)
ON DELETE CASCADE;
