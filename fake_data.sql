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
