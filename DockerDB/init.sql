CREATE DATABASE IF NOT EXISTS aliOrders;

USE aliOrders;

CREATE TABLE clients
(
    id_client  binary(16) primary key not null unique,
    login_name varchar(45)            not null,
    password   varchar(225)           not null
);

CREATE TABLE orders
(
    id_order     varchar(36) primary key not null,
    email        varchar(225)    not null,
    order_number varchar(225)    not null,
    description  varchar(225)    not null,
    id_client    binary(16)
);

ALTER TABLE orders
    ADD CONSTRAINT `fk_clents_orders` FOREIGN KEY (`id_client`) REFERENCES `clients` (`id_client`);