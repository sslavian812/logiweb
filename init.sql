-- ----------------------------------------------------------------------------
-- All tables creation
-- ----------------------------------------------------------------------------

DROP TABLE IF EXISTS `trucks`;

CREATE TABLE `trucks` (
  `id`                  INT(11) NOT NULL AUTO_INCREMENT,
  `crew_size`           INT(11)          DEFAULT '1',
  `registration_number` VARCHAR(45)      DEFAULT NULL,
  `capacity`            INT(11)          DEFAULT NULL,
  `status`              VARCHAR(15)      DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 56
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `drivers`;

CREATE TABLE `drivers` (
  `id`              INT(11) NOT NULL AUTO_INCREMENT,
  `first_name`      VARCHAR(45)      DEFAULT NULL,
  `last_name`       VARCHAR(45)      DEFAULT NULL,
  `personal_number` VARCHAR(45)      DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `driver_statuses`;

CREATE TABLE `driver_statuses` (
  `id`                   INT(11) NOT NULL AUTO_INCREMENT,
  `status`               VARCHAR(15)      DEFAULT NULL,
  `current_truck`        INT(11)          DEFAULT NULL,
  `driver_id_for_status` INT(11)          DEFAULT NULL,
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
  `id`               INT(11) NOT NULL AUTO_INCREMENT,
  `order_identifier` VARCHAR(45)      DEFAULT NULL,
  `status`           VARCHAR(15)      DEFAULT NULL,
  `truck`            INT(11)          DEFAULT NULL,
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
  `id`           INT(11) NOT NULL AUTO_INCREMENT,
  `order_id`     INT(11)          DEFAULT NULL,
  `denomination` VARCHAR(45)      DEFAULT NULL,
  `weight`       INT(11)          DEFAULT NULL,
  `status`       VARCHAR(15)      DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id_idx` (`order_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `shifts`;

CREATE TABLE `shifts` (
  `id`                  INT(11) NOT NULL AUTO_INCREMENT,
  `driver_id_for_shift` INT(11)          DEFAULT NULL,
  `shift_begin`         DATETIME(6)      DEFAULT NULL,
  `shift_end`           DATETIME(6)      DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `driver_id_idx` (`driver_id_for_shift`),
  CONSTRAINT `driver_id_for_shift` FOREIGN KEY (`driver_id_for_shift`) REFERENCES `drivers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8

-- ---------------------------------------------------------------------------
-- some data
-- ---------------------------------------------------------------------------

  SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO `trucks` (`id`, `crew_size`, `registration_number`, `capacity`, `status`) VALUES
  (1, 2, 'ab12345', 5000, 'INTACT'),
  (2, 3, 'cd67890', 10000, 'INTACT'),
  (3, 1, 'ef11111', 2000, 'BROKEN');


INSERT INTO `drivers` (`id`, `first_name`, `last_name`, `personal_number`) VALUES
  (1, 'Dumas', 'Alexandre', 'a'),
  (2, 'Pushkin', 'Alexander', 'b'),
  (3, 'Tolstoi', 'Lev', 'c'),
  (4, 'Defoe', 'Daniel', 'd'),
  (5, 'Wilde', 'Oscar', 'e'),
  (6, 'Belaev', 'Alexander', 'f');

INSERT INTO `driver_statuses` (`id`, `status`, `current_truck`, `driver_id_for_status`) VALUES
  (1, 'PRIMARY', 1, 1),
  (2, 'AUXILIARY', 1, 2),
  (3, 'PRIMARY', 2, 3),
  (4, 'AUXILIARY', 2, 4),
  (5, 'AUXILIARY', 2, 5),
  (6, 'REST', NULL, 6);


INSERT INTO `orders` (`id`, `order_identifier`, `status`, `truck`) VALUES
  (1, 'aa', 'IN_PROGRESS', 1),
  (2, 'bb', 'IN_PROGRESS', 2);


INSERT INTO `cargos` (`id`, `order_id`, `denomination`, `weight`, `status`) VALUES
  (1, 1, 'secret cargo 1', 1000, 'PREPARED'),
  (2, 2, 'secret cargo 2', 2000, 'SHIPPED');

SET FOREIGN_KEY_CHECKS = 1;

