use servicedb;

create table flight(
id int AUTO_INCREMENT PRIMARY KEY,
destination varchar(100),
origin varchar(100),
price decimal(8,3) 
);

create table discount(
id int AUTO_INCREMENT PRIMARY KEY,
code varchar(20) UNIQUE,
discount decimal(8,3),
exp_date varchar(100) 
);