CREATE TABLE user (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE role (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    roles_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, roles_id)
);



