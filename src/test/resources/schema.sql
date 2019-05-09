CREATE TABLE `attach_request` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `owner` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `attach_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content_type` varchar(255) NOT NULL,
  `original_name` varchar(255) NOT NULL,
  `upload_path` varchar(255) NOT NULL,
  `attach_request_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6gv9mw1rf0btvlmq96iqh7es2` (`attach_request_id`),
  CONSTRAINT `FK6gv9mw1rf0btvlmq96iqh7es2` FOREIGN KEY (`attach_request_id`) REFERENCES `attach_request` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `attach_download` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `updated_at` datetime NOT NULL,
  `count` bigint(20) NOT NULL,
  `attach_file_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKitcny9eotwopaf00lqur4njni` (`attach_file_id`),
  CONSTRAINT `FKitcny9eotwopaf00lqur4njni` FOREIGN KEY (`attach_file_id`) REFERENCES `attach_file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
