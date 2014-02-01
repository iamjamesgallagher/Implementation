# --- Sample dataset

# --- !Ups

insert into dbrow (id,name,sector) values (  1,'Apple Inc.', 'Technology');
insert into dbrow (id,name,sector) values (  2,'Thinking Machines', 'Technology');
insert into dbrow (id,name,sector) values (  3,'RCA', 'Technology');
insert into dbrow (id,name,sector) values (  4,'Netronics', 'Technology');


# --- !Downs


delete from dbrow;
