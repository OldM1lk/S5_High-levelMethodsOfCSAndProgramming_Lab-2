DROP TABLE IF EXISTS permissions;
DROP TABLE IF EXISTS resources;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    login VARCHAR(50) PRIMARY KEY,
    salt VARCHAR(50) NOT NULL,
    password_hash VARCHAR(64) NOT NULL
);

CREATE TABLE resources (
    name VARCHAR(50) PRIMARY KEY,
    max_volume INT NOT NULL,
    parent_name VARCHAR(50),
    FOREIGN KEY (parent_name) REFERENCES resources(name)
);

CREATE TABLE permissions (
    user_login VARCHAR(50) NOT NULL,
    resource_name VARCHAR(50) NOT NULL,
    action VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_login, resource_name, action),
    FOREIGN KEY (user_login) REFERENCES users(login),
    FOREIGN KEY (resource_name) REFERENCES resources(name)
);
