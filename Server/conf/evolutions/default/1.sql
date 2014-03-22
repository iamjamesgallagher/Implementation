# --- First database schema

# --- !Ups

create table dbrow (
  id                        bigint not null,
  name                      varchar(255),
  ticker                      varchar(255),
   mkcap                      varchar(255),   
 sector						 varchar(255),
 dddd						 varchar(255),




	 
  constraint pk_dbrow primary key (id))
;


create sequence dbrow_seq start with 1000;


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists dbrow;



drop sequence if exists dbrow_seq;



