




show create table trucks;
show create table drivers;
show create table driver_statuses;
show create table orders;
show create table cargos;
show create table shifts;

select * from drivers;
select * from driver_statuses;
select * from orders;


select * from trucks;

update driver_statuses set status='PRIMARY' where id=1;

SET FOREIGN_KEY_CHECKS = 1;
UPDATE drivers SET personal_number='vasia' WHERE id=1;
  
  
  
  