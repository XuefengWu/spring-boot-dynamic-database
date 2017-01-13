--liquibase formatted sql

--changeset xfwu:2
insert into CUSTOMER (id, firstName,lastName) values (1, 'gold1 first', 'gold1 last');
insert into CUSTOMER (id, firstName,lastName) values (2, 'gold2 first', 'gold2 last');