drop database if exists covid19;
create database if not exists covid19;
use covid19;

create table if not exists globalcovid
(
    lastUpdate     DATE not null,
    confirmedCases int  not null,
    recovered      int  not null,
    deaths         int  not null
);

create table if not exists manageUsers
(
    name       varchar(25) not null,
    email      varchar(30) not null,
    userName   varchar(25) not null,
    password   varchar(25) not null,
    userRole   varchar(50) not null,
    department varchar(50) null,
    CONSTRAINT pk_manage_users PRIMARY KEY (name, userName),
    CONSTRAINT uk_manage_users UNIQUE (contactNo, email, userName)

);

create table if not exists manageHospitals
(
    id                 varchar(15)                      not null primary key,
    name               varchar(50)                      not null,
    city               varchar(50)                      not null,
    district           varchar(50)                      not null,
    capacity           int                              not null,
    director           varchar(50)                      not null,
    directorContactNo  int                              not null,
    hospitalContactNo1 int                              not null,
    hospitalContactNo2 int                              not null,
    faxNo              int                              not null,
    email              varchar(50)                      not null,
    user               varchar(20)                      not null,
    itPerson           varchar(20) default 'not assign' null,
    CONSTRAINT uk_manage_hospital UNIQUE (directorContactNo, hospitalContactNo1, hospitalContactNo2, faxNo, email)
);

create table if not exists manageQuarantineCenters
(
    id               varchar(15)                      not null primary key,
    name             varchar(50)                      not null,
    city             varchar(50)                      not null,
    district         varchar(50)                      not null,
    head             varchar(50)                      not null,
    headContactNo    varchar(50)                      not null,
    centerContactNo1 int                              not null,
    centerContactNo2 int                              not null,
    capacity         int                              not null,
    user             varchar(20)                      not null,
    itPerson         varchar(20) default 'not assign' null,
    CONSTRAINT uk_manage_center UNIQUE (headContactNo, centerContactNo1, centerContactNo2)
);



CREATE TABLE people
(
    id             INT          NOT NULL PRIMARY KEY,
    first_name     VARCHAR(45)  NOT NULL,
    last_name      VARCHAR(45)  NOT NULL,
    address        VARCHAR(100) NOT NULL,
    city           VARCHAR(45)  NOT NULL,
    district       VARCHAR(45)  NOT NULL,
    province       VARCHAR(45)  NOT NULL,
    nic            VARCHAR(15)  NOT NULL,
    contact_number VARCHAR(45)  NOT NULL,
    blood_group    VARCHAR(45)  NOT NULL
);



CREATE TABLE covid_positive
(
    id    INT          NOT NULL PRIMARY KEY,
    date  DATETIME     NOT NULL,
    found VARCHAR(500) NOT NULL,
    CONSTRAINT FOREIGN KEY (id) REFERENCES people (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE patient
(
    id       INT          NOT NULL,
    hospital varchar(15)          NOT NULL,
    date     DATETIME     NOT NULL,
    reason   VARCHAR(500) NOT NULL,
    CONSTRAINT PRIMARY KEY (date, id, hospital),
    CONSTRAINT FOREIGN KEY (id) REFERENCES people (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (hospital) REFERENCES covid19.managehospitals(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE suspected
(
    id     INT          NOT NULL PRIMARY KEY,
    reason VARCHAR(500) NOT NULL,
    date   DATETIME     NOT NULL,
    CONSTRAINT FOREIGN KEY (id) REFERENCES people (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE quarantined
(
    id           INT PRIMARY KEY,
    reason       VARCHAR(500) NOT NULL,
    entered_date DATETIME     NOT NULL,
    center       varchar(15)          NOT NULL,
    CONSTRAINT FOREIGN KEY (id) REFERENCES people (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (center) REFERENCES managequarantinecenters (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE reference
(
    suspected_id INT          NOT NULL,
    reference_id INT          NOT NULL,
    connection   VARCHAR(500) NOT NULL,
    PRIMARY KEY (suspected_id, reference_id),
    CONSTRAINT FOREIGN KEY (suspected_id) REFERENCES people (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (reference_id) REFERENCES people(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE auto_remove
(
    id   INT      NOT NULL PRIMARY KEY,
    date DATETIME NOT NULL,
    CONSTRAINT FOREIGN KEY (id) REFERENCES suspected (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE exit_data
(
    id          INT                                     NOT NULL,
    exit_reason ENUM ('DISCHARGED','TRANSFERRED','DEAD')NOT NULL,
    `from`      varchar(15)                             NOT NULL,
    `to`        VARCHAR(45)                             NOT NULL,
    location    ENUM ('HOSPITAL','QUARANTINE CENTER')   NOT NULL,
    date        DATETIME                                NOT NULL,
    CONSTRAINT PRIMARY KEY (id, `from`, location),
    CONSTRAINT FOREIGN KEY (id) REFERENCES people (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (`from`) REFERENCES managehospitals (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (`from`) REFERENCES managequarantinecenters (id) ON UPDATE CASCADE ON DELETE CASCADE
);

