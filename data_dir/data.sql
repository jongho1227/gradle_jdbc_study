use ncs_erp;

select user(), database();

show tables;

insert into department values
(1,'영업',8),(2,'기획',10),(3,'개발',9),(4,'총무',7);

insert into title values
(1,'사장'),(2,'부장'),(3,'과장'),(4,'대리'),(5,'사원');



INSERT INTO employee VALUES 
(4377,'이성래',1,NULL,5000000,1,2,'2017-03-01'),
(3011,'이수민',2,4377,4000000,0,3,'2017-04-01'),
(3426,'박영권',3,4377,3000000,1,1,'2018-01-12'),
(1003,'조민희',3,4377,3000000,0,2,'2018-02-01'),
(1365,'김상원',5,3426,1500000,1,1,'2018-09-01'),
(2106,'김창섭',4,1003,2500000,1,2,'2018-01-01'),
(3427,'최종철',4,3011,1500000,1,2,'2018-12-01');

select * from title;
select * from department;
select * from employee;

insert into department values (4,'총무',5);
insert into employee values (2,'임종호',3,1,1000000000,1,6,'2010-01-01');
update employee set manager=null where empNo=2;
delete from department where deptno=6;

delete from employee where empno=2;