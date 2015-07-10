SET FOREIGN_KEY_CHECKS = 0;

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

SET FOREIGN_KEY_CHECKS = 1;