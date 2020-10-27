
drop database if exists test;
create database test;
use test;

drop table if exists student;

create table student(
id int primary key not null auto_increment,
stuName varchar(25) not null comment '姓名',
stuNo varchar(25) not null comment '学号',
age tinyint not null comment 'age',
sex tinyint not null comment '性别,0男1女',
address varchar(200) comment '地址',
birthday TIMESTAMP comment '生日',
unique index(stuNo) using btree
)engine=INNODB character set=utf8mb4 collate=utf8mb4_general_ci row_format=dynamic comment '学生表';

insert into student(stuName,stuNo,age,sex,address,birthday)
values('大白','s1000',20,0,'南城','2020-01-01'),
('小黄','s1001',10,0,'东城','2010-01-01'),
('老王','s1002',66,0,'北城','1987-02-01'),
('zxp','s1003',24,0,'通天河','2012-01-01'),
('黄武','s1004',40,0,'天庭','2013-01-01'),
('王麻子','s1005',96,0,'贝菊门','1997-02-01');

select * from student;

