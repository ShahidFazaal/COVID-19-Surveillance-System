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
    contactNo  int         not null,
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
    id                 varchar(15) not null primary key,
    name               varchar(50) not null,
    city               varchar(50) not null,
    district           varchar(50) not null,
    capacity           int         not null,
    director           varchar(50) not null,
    directorContactNo  int         not null,
    hospitalContactNo1 int         not null,
    hospitalContactNo2 int         not null,
    faxNo              int         not null,
    email              varchar(50) not null,
    user             varchar(20) not null,
    itPerson         varchar(20) default 'not assign' null,
    CONSTRAINT uk_manage_hospital UNIQUE (directorContactNo, hospitalContactNo1, hospitalContactNo2, faxNo, email)
);

create table if not exists manageQuarantineCenters
(
    id               varchar(15) not null primary key,
    name             varchar(50) not null,
    city             varchar(50) not null,
    district         varchar(50) not null,
    head             varchar(50) not null,
    headContactNo    varchar(50) not null,
    centerContactNo1 int         not null,
    centerContactNo2 int         not null,
    capacity         int         not null,
    user             varchar(20) not null,
    itPerson         varchar(20) default 'not assign' null,
    CONSTRAINT uk_manage_center UNIQUE (headContactNo, centerContactNo1, centerContactNo2)
);

