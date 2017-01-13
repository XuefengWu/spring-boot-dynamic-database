--liquibase formatted sql

--changeset xfwu:2
insert into CUSTOMER (id, firstName,lastName) values (1, 'bronze1 first', 'bronze1 last');
insert into CUSTOMER (id, firstName,lastName) values (2, 'bronze2 first', 'bronze2 last');