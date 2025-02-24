CREATE DATABASE IF NOT EXISTS aliOrders;

USE aliOrders;

CREATE TABLE clients
(
    id_client  binary(16) primary key not null unique,
    login_name varchar(45)            not null,
    password   varchar(225)           not null,
    role       varchar(45)            not null
);

CREATE TABLE orders
(
    id_order     varchar(36) primary key not null,
    email        varchar(225)            not null,
    order_number varchar(225)            not null,
    description  varchar(225)            not null,
    id_client    binary(16)
);

CREATE TABLE actions
(
    id_action  bigint primary key not null auto_increment,
    action     varchar(225)       not null,
    method_name varchar(225)       not null,
    result     varchar(225)       not null
);

ALTER TABLE orders
    ADD CONSTRAINT `fk_clents_orders` FOREIGN KEY (`id_client`) REFERENCES `clients` (`id_client`);

INSERT INTO `aliOrders`.`clients` (`id_client`, `login_name`, `password`, `role`)
VALUES (UUID_TO_BIN('49ec45e2-5026-4dcb-9423-b34bd7e9a845'),
        'user1',
        '$argon2i$v=19$m=65536,t=20,p=1$7E9CNI4YuDsXiAY6zfN/pA$vZBdYIB8tn41ZqKONErHVbzeoY7n2yRXnw53oGosz/0',
        'ADMIN');
