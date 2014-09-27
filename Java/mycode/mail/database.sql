create database godown;

use godown;

/* As soon as the goods arrive to database this table is updated  irrespective of godown, this is global data*/
create table goods(
name varchar(50),
quantity int,
weight float,
godown_num int
);

/* when a customer enters his requirement this table is updated , this is just to hold the order */
/* godown allocation is done in next phase */
create table assigned_goods(
name varchar(50),
quantity int,
weight float,
destination_lat float,
destination_lon float,
customer_name varchar(50),
location_name varchar(50)
);

create table godown_info(
godown_num int,
latitude float,
longitude float
);
/* Sample latitude and logitude are considered , taking into account 7 godowns; */
insert into godown_info values (1, 12.3024, 76.6386 );					/* mysore */ 
insert into godown_info values (2, 12.9833, 77.5833 );					/* Bangalore */	
insert into godown_info values (3, 12.5200, 76.9000 );					/* Mandya */					
insert into godown_info values (4, 12.4200, 75.7300 );					/* Madikeri */
insert into godown_info values (5, 14.2300, 75.9000 );					/* Bangalore */
insert into godown_info values (6, 17.3300, 76.8300 );					/* davangere */
insert into godown_info values (7, 12.8 , 76.19 );

create table time_matrix(
godown1 float,
godown2 float,
godown3 float,
godown4 float,
godown5 float,
godown6 float,
godown7 float
);


/* Still need to add few more tables */

