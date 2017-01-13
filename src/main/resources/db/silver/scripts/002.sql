--liquibase formatted sql

--changeset xfwu:2
insert into CUSTOMER (id, firstName,lastName) values (1, 'silver1 first', 'silver1 last');
insert into CUSTOMER (id, firstName,lastName) values (2, 'silver2 first', 'silver2 last');