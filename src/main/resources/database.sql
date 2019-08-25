CREATE TABLE locations (
    id      INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    city    VARCHAR(250) NOT NULL,
    address VARCHAR(250) NOT NULL,
    name    VARCHAR(250) NOT NULL
);

INSERT INTO locations VALUES
    (NULL, 'Łódź', 'ul. Bandurskiego 5', 'Atlas Arena');

CREATE TABLE events (
    id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(250) NOT NULL,
    startDate  DATETIME     NOT NULL,
    endDate    DATETIME     NOT NULL,
    locationID INT          NOT NULL,

    FOREIGN KEY (locationID) REFERENCES locations (id)
);

CREATE TABLE tickets (
    id      INT           NOT NULL PRIMARY KEY AUTO_INCREMENT,
    price   DECIMAL(7, 2) NOT NULL,
    eventID INT           NOT NULL,
    number  INT           NOT NULL,

    FOREIGN KEY (eventID) REFERENCES events (id)
);

CREATE TABLE users (
    id               INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    login            VARCHAR(250) NOT NULL,
    name             VARCHAR(250) NOT NULL,
    surname          VARCHAR(250) NOT NULL,
    password         VARCHAR(250) NOT NULL,
    email            VARCHAR(250) NOT NULL,
    registrationDate DATETIME                          DEFAULT NOW(),
    active           BOOLEAN                           DEFAULT 0
);

CREATE TABLE transactions (
    id     INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    userID INT NOT NULL,

    FOREIGN KEY (userID) REFERENCES users (id)
);


CREATE TABLE transactionTicket (
    id            INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    transactionID INT NOT NULL,
    ticketID      INT NOT NULL,
    number        INT NOT NULL,

    FOREIGN KEY (ticketID) REFERENCES tickets (id),
    FOREIGN KEY (transactionID) REFERENCES transactions(id)
);

ALTER TABLE users MODIFY registrationDate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE users MODIFY active tinyint(1) NOT NULL DEFAULT '0';

ALTER TABLE locations ADD COLUMN version INT DEFAULT 0;
