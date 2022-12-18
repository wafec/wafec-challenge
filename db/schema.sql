CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_id VARCHAR(20) NOT NULL,
    name VARCHAR(500) NOT NULL
);

CREATE TABLE role_memberships (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_id VARCHAR(20) NOT NULL,
    user_id VARCHAR(20) NOT NULL,
    team_id VARCHAR(20) NOT NULL,

    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

INSERT IGNORE INTO roles (role_id, name) VALUES ("Developer", "85bdb907-8f78-402e-aff6-5c65e8021c51");
INSERT IGNORE INTO roles (role_id, name) VALUES ("Product Owner", "d2346425-097e-410e-8a5c-a266b59adda3");
INSERT IGNORE INTO roles (role_id, name) VALUES ("Tester", "89c76aec-0755-4139-af3b-2579bf277574");