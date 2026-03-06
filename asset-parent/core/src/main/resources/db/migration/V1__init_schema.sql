-- Asset Management System Database Migration
-- V1: Initial schema

-- ----------------------------
-- 1. User table
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
    `username` VARCHAR(50) NOT NULL COMMENT 'Username',
    `password` VARCHAR(200) NOT NULL COMMENT 'Password',
    `real_name` VARCHAR(50) COMMENT 'Real name',
    `phone` VARCHAR(20) COMMENT 'Phone number (encrypted)',
    `id_card` VARCHAR(100) COMMENT 'ID card number (encrypted)',
    `email` VARCHAR(100) COMMENT 'Email',
    `dept_id` BIGINT COMMENT 'Department ID',
    `role_code` VARCHAR(50) COMMENT 'Role code (admin/asset_manager/employee)',
    `status` TINYINT DEFAULT 1 COMMENT 'Status (0: disabled, 1: normal)',
    `is_deleted` TINYINT DEFAULT 0 COMMENT 'Logical delete (0: not deleted, 1: deleted)',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    `create_by` BIGINT COMMENT 'Creator ID',
    `update_by` BIGINT COMMENT 'Updater ID',
    `version` INT DEFAULT 0 COMMENT 'Version for optimistic lock',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_dept_id` (`dept_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System user table';

-- ----------------------------
-- 2. Department table
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
    `dept_name` VARCHAR(100) NOT NULL COMMENT 'Department name',
    `parent_id` BIGINT DEFAULT 0 COMMENT 'Parent department ID',
    `dept_code` VARCHAR(50) COMMENT 'Department code',
    `sort` INT DEFAULT 0 COMMENT 'Sort order',
    `leader` VARCHAR(50) COMMENT 'Department leader',
    `phone` VARCHAR(20) COMMENT 'Contact phone',
    `status` TINYINT DEFAULT 1 COMMENT 'Status (0: disabled, 1: normal)',
    `is_deleted` TINYINT DEFAULT 0 COMMENT 'Logical delete',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    `create_by` BIGINT COMMENT 'Creator ID',
    `update_by` BIGINT COMMENT 'Updater ID',
    `version` INT DEFAULT 0 COMMENT 'Version',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_dept_code` (`dept_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Department table';

-- ----------------------------
-- 3. Asset category table (tree structure)
-- ----------------------------
DROP TABLE IF EXISTS `asset_category`;
CREATE TABLE `asset_category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
    `category_name` VARCHAR(100) NOT NULL COMMENT 'Category name',
    `parent_id` BIGINT DEFAULT 0 COMMENT 'Parent category ID',
    `category_code` VARCHAR(50) COMMENT 'Category code',
    `level` INT DEFAULT 1 COMMENT 'Category level (1, 2, 3...)',
    `sort` INT DEFAULT 0 COMMENT 'Sort order',
    `icon` VARCHAR(200) COMMENT 'Category icon',
    `status` TINYINT DEFAULT 1 COMMENT 'Status (0: disabled, 1: normal)',
    `is_deleted` TINYINT DEFAULT 0 COMMENT 'Logical delete',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    `create_by` BIGINT COMMENT 'Creator ID',
    `update_by` BIGINT COMMENT 'Updater ID',
    `version` INT DEFAULT 0 COMMENT 'Version',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_category_code` (`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Asset category table';

-- ----------------------------
-- 4. Asset information table
-- ----------------------------
DROP TABLE IF EXISTS `asset_info`;
CREATE TABLE `asset_info` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
    `asset_code` VARCHAR(50) NOT NULL COMMENT 'Asset code (unique)',
    `asset_name` VARCHAR(200) NOT NULL COMMENT 'Asset name',
    `category_id` BIGINT NOT NULL COMMENT 'Category ID',
    `brand` VARCHAR(100) COMMENT 'Brand',
    `model` VARCHAR(100) COMMENT 'Model',
    `serial_number` VARCHAR(100) COMMENT 'Serial number',
    `status` TINYINT DEFAULT 1 COMMENT 'Asset status (1: in use, 2: idle, 3: maintenance, 4: scrapped)',
    `user_id` BIGINT COMMENT 'Current user ID',
    `dept_id` BIGINT COMMENT 'Department ID',
    `location` VARCHAR(200) COMMENT 'Storage location',
    `purchase_date` DATE COMMENT 'Purchase date',
    `purchase_price` DECIMAL(15,2) COMMENT 'Purchase price',
    `warranty_end_date` DATE COMMENT 'Warranty end date',
    `supplier` VARCHAR(200) COMMENT 'Supplier',
    `remark` VARCHAR(500) COMMENT 'Remark',
    `qr_code` VARCHAR(500) COMMENT 'QR code URL',
    `is_deleted` TINYINT DEFAULT 0 COMMENT 'Logical delete',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    `create_by` BIGINT COMMENT 'Creator ID',
    `update_by` BIGINT COMMENT 'Updater ID',
    `version` INT DEFAULT 0 COMMENT 'Version',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_asset_code` (`asset_code`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_dept_id` (`dept_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Asset information table';

-- ----------------------------
-- 5. Asset operation log table
-- ----------------------------
DROP TABLE IF EXISTS `asset_operation_log`;
CREATE TABLE `asset_operation_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
    `asset_id` BIGINT NOT NULL COMMENT 'Asset ID',
    `operation_type` VARCHAR(50) NOT NULL COMMENT 'Operation type (receive/return/transfer/maintenance)',
    `operator_id` BIGINT COMMENT 'Operator ID',
    `from_user_id` BIGINT COMMENT 'From user ID',
    `to_user_id` BIGINT COMMENT 'To user ID',
    `from_dept_id` BIGINT COMMENT 'From department ID',
    `to_dept_id` BIGINT COMMENT 'To department ID',
    `remark` VARCHAR(500) COMMENT 'Remark',
    `is_deleted` TINYINT DEFAULT 0 COMMENT 'Logical delete',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    `create_by` BIGINT COMMENT 'Creator ID',
    `update_by` BIGINT COMMENT 'Updater ID',
    `version` INT DEFAULT 0 COMMENT 'Version',
    PRIMARY KEY (`id`),
    KEY `idx_asset_id` (`asset_id`),
    KEY `idx_operation_type` (`operation_type`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Asset operation log table';

-- ----------------------------
-- 6. Inventory task table
-- ----------------------------
DROP TABLE IF EXISTS `inventory_task`;
CREATE TABLE `inventory_task` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
    `task_name` VARCHAR(200) NOT NULL COMMENT 'Task name',
    `task_code` VARCHAR(50) NOT NULL COMMENT 'Task code',
    `start_time` DATETIME NOT NULL COMMENT 'Start time',
    `end_time` DATETIME NOT NULL COMMENT 'End time',
    `task_scope` JSON COMMENT 'Task scope (departments and users)',
    `task_type` TINYINT DEFAULT 1 COMMENT 'Task type (1: full, 2: partial)',
    `status` TINYINT DEFAULT 1 COMMENT 'Status (1: pending, 2: in progress, 3: completed, 4: cancelled)',
    `remark` VARCHAR(500) COMMENT 'Remark',
    `creator_id` BIGINT COMMENT 'Creator ID',
    `is_deleted` TINYINT DEFAULT 0 COMMENT 'Logical delete',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    `create_by` BIGINT COMMENT 'Creator ID',
    `update_by` BIGINT COMMENT 'Updater ID',
    `version` INT DEFAULT 0 COMMENT 'Version',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_task_code` (`task_code`),
    KEY `idx_status` (`status`),
    KEY `idx_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Inventory task table';

-- ----------------------------
-- 7. Inventory detail table
-- ----------------------------
DROP TABLE IF EXISTS `inventory_detail`;
CREATE TABLE `inventory_detail` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
    `task_id` BIGINT NOT NULL COMMENT 'Task ID',
    `asset_id` BIGINT NOT NULL COMMENT 'Asset ID',
    `book_status` TINYINT COMMENT 'Book status (asset status at inventory start)',
    `actual_status` TINYINT COMMENT 'Actual status (status after inventory)',
    `inventory_result` TINYINT COMMENT 'Inventory result (1: consistent, 2: found, 3: missing, 4: damaged)',
    `inventory_time` DATETIME COMMENT 'Inventory time',
    `inventory_user_id` BIGINT COMMENT 'Inventory user ID',
    `location` VARCHAR(200) COMMENT 'Actual location',
    `remark` VARCHAR(500) COMMENT 'Remark',
    `photo_url` VARCHAR(500) COMMENT 'Photo URL',
    `is_deleted` TINYINT DEFAULT 0 COMMENT 'Logical delete',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    `create_by` BIGINT COMMENT 'Creator ID',
    `update_by` BIGINT COMMENT 'Updater ID',
    `version` INT DEFAULT 0 COMMENT 'Version',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_task_asset` (`task_id`, `asset_id`),
    KEY `idx_task_id` (`task_id`),
    KEY `idx_asset_id` (`asset_id`),
    KEY `idx_inventory_user_id` (`inventory_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Inventory detail table';

-- ----------------------------
-- 8. System operation log table
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
    `operator_id` BIGINT COMMENT 'Operator ID',
    `operator_name` VARCHAR(50) COMMENT 'Operator name',
    `operation_module` VARCHAR(50) COMMENT 'Operation module',
    `operation_type` VARCHAR(50) COMMENT 'Operation type',
    `operation_desc` VARCHAR(500) COMMENT 'Operation description',
    `request_method` VARCHAR(10) COMMENT 'Request method',
    `request_url` VARCHAR(500) COMMENT 'Request URL',
    `request_params` TEXT COMMENT 'Request parameters',
    `request_ip` VARCHAR(50) COMMENT 'Request IP',
    `response_result` TEXT COMMENT 'Response result',
    `status` TINYINT DEFAULT 1 COMMENT 'Status (1: success, 0: failed)',
    `error_msg` TEXT COMMENT 'Error message',
    `cost_time` BIGINT COMMENT 'Cost time (ms)',
    `is_deleted` TINYINT DEFAULT 0 COMMENT 'Logical delete',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    PRIMARY KEY (`id`),
    KEY `idx_operator_id` (`operator_id`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_operation_module` (`operation_module`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System operation log table';

-- ----------------------------
-- Insert initial data
-- ----------------------------
-- Insert default admin user (password: Admin@123)
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `role_code`, `status`) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'System Admin', 'admin', 1);

-- Insert default departments
INSERT INTO `sys_department` (`dept_name`, `dept_code`, `parent_id`, `sort`) VALUES
('Headquarters', 'HQ', 0, 1),
('IT Department', 'IT', 1, 1),
('Finance Department', 'FIN', 1, 2),
('HR Department', 'HR', 1, 3);

-- Insert default asset categories
INSERT INTO `asset_category` (`category_name`, `parent_id`, `category_code`, `level`, `sort`) VALUES
('Electronic Equipment', 0, 'EE', 1, 1),
('Computer', 1, 'CMP', 2, 1),
('Monitor', 1, 'MON', 2, 2),
('Printer', 1, 'PRT', 2, 3),
('Furniture', 0, 'FUR', 1, 2),
('Office Desk', 5, 'DES', 2, 1),
('Office Chair', 5, 'CHA', 2, 2);
