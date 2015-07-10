SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `drivers`;
DROP TABLE IF EXISTS `driver_statuses`;

CREATE TABLE `drivers` (
  `id`              INT(11) NOT NULL AUTO_INCREMENT,
  `first_name`      VARCHAR(45)      DEFAULT NULL,
  `last_name`       VARCHAR(45)      DEFAULT NULL,
  `personal_number` VARCHAR(45)      DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


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
  
  SET FOREIGN_KEY_CHECKS = 1;