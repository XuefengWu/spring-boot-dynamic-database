--liquibase formatted sql

--changeset xfwu:1
DROP TABLE IF EXISTS CUSTOMER;
CREATE TABLE CUSTOMER(
    id int,
    firstName varchar(255),
    lastName varchar(255)
    );
--rollback drop table CUSTOMER;
