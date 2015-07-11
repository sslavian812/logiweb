SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `cargos`;

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


CREATE TABLE `cargos` (
  `id`           INT(11) NOT NULL AUTO_INCREMENT,
  `order_id`     INT(11)          DEFAULT NULL,
  `denomination` VARCHAR(45)      DEFAULT NULL,
  `weight`       INT(11)          DEFAULT NULL,
  `status`       VARCHAR(15)      DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id_idx` (`order_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
  
  SET FOREIGN_KEY_CHECKS = 1;