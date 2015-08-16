-- ----------------------------------------------------------------------------
-- All tables creation
-- ----------------------------------------------------------------------------
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `trucks`;

CREATE TABLE `trucks` (
  `id`                  INT(11) NOT NULL       AUTO_INCREMENT,
  `crew_size`           INT(11)                DEFAULT '1',
  `registration_number` VARCHAR(45) UNIQUE     DEFAULT NULL,
  `capacity`            INT(11)                DEFAULT NULL,
  `status`              VARCHAR(15)            DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 56
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `drivers`;

CREATE TABLE `drivers` (
  `id`              INT(11)     NOT NULL AUTO_INCREMENT,
  `first_name`      VARCHAR(45) NOT NULL,
  `last_name`       VARCHAR(45) NOT NULL,
  `personal_number` VARCHAR(45) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `driver_statuses`;

CREATE TABLE `driver_statuses` (
  `id`                   INT(11)     NOT NULL AUTO_INCREMENT,
  `status`               VARCHAR(15) NOT NULL,
  `current_truck`        INT(11)              DEFAULT NULL,
  `driver_id_for_status` INT(11)     NOT NULL,
  `last_working_month`   INT(11)     NOT NULL,
  `working_hours`        FLOAT     NOT NULL,
  PRIMARY KEY (`id`),
  KEY `current_truck_idx` (`current_truck`),
  KEY `driver_id_idx` (`driver_id_for_status`),
  CONSTRAINT `current_truck` FOREIGN KEY (`current_truck`) REFERENCES `trucks` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `driver_id_for_status` FOREIGN KEY (`driver_id_for_status`) REFERENCES `drivers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id`               INT(11)               NOT NULL      AUTO_INCREMENT,
  `order_identifier` VARCHAR(45) UNIQUE    NOT NULL,
  `status`           VARCHAR(15)           NOT NULL,
  `truck`            INT(11)                             DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `truck_idx` (`truck`),
  CONSTRAINT `truck` FOREIGN KEY (`truck`) REFERENCES `trucks` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `cargos`;

CREATE TABLE `cargos` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `order_id`         INT(11)     NOT NULL,
  `denomination`     VARCHAR(45) NOT NULL,
  `cargo_identifier` VARCHAR(45) NOT NULL,
  `weight`           INT(11)     NOT NULL,
  `status`           VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id_idx` (`order_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `shifts`;

CREATE TABLE `shifts` (
  `id`                  INT(11)     NOT NULL      AUTO_INCREMENT,
  `driver_id_for_shift` INT(11)     NOT NULL,
  `shift_begin`         DATETIME(6) NOT NULL,
  `shift_end`           DATETIME(6)               DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `driver_id_idx` (`driver_id_for_shift`),
  CONSTRAINT `driver_id_for_shift` FOREIGN KEY (`driver_id_for_shift`) REFERENCES `drivers` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


SET FOREIGN_KEY_CHECKS = 1;