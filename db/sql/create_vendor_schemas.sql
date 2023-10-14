CREATE TABLE IF NOT EXISTS vendor (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  owner_id bigint NOT NULL,
  address_id bigint NOT NULL,
  name varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  email varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  phone varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY owner_id_idx (owner_id),
  KEY address_id_idx (address_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS worker (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  vendor_id bigint NOT NULL,
  name varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  email varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  phone varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  is_active BOOL NOT NULL DEFAULT TRUE,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY vendor_id_idx (vendor_id),
  KEY is_active_idx (is_active)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;