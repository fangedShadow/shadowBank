create table shadowbank.custAcc(
custID int AUTO_INCREMENT PRIMARY KEY,
custUserName varchar(255) UNIQUE,
custPassword varchar(255),
custName varchar(255),
custAddress varchar(255)
);

create table shadowbank.bankAcc(
accID int AUTO_INCREMENT PRIMARY KEY,
amount int,
custID int,
FOREIGN KEY(custID) REFERENCES custAcc(custID)
);

create table shadowbank.empAcc(
empID int AUTO_INCREMENT PRIMARY KEY,
empUserName varchar(255) UNIQUE,
empPassword varchar(255),
empName varchar(255)
);

create table shadowbank.custEmpApp(
appID int AUTO_INCREMENT PRIMARY KEY,
custID int,
empID int,
appTime varchar(255),
FOREIGN KEY(custID) REFERENCES custAcc(custID),
FOREIGN KEY(empID) REFERENCES empAcc(empID)
);

create table shadowbank.availdate(
date varchar(255),
dateID int primary key AUTO_INCREMENT
);
create table shadowbank.availtime(
time varchar(255),
timeID int primary key AUTO_INCREMENT
);

drop table shadowbank.comMsg;
create table shadowbank.comMsg(
custID int,
empID int,
message text,
FOREIGN KEY(custID) REFERENCES custAcc(custID),
FOREIGN KEY(empID) REFERENCES empAcc(empID)
);

create table shadowbank.BArequest(
requestID int AUTO_INCREMENT PRIMARY KEY,
custID int,
amount int,
aproval boolean,
FOREIGN KEY(custID) REFERENCES custAcc(custID)
);

create table shadowbank.adminAcc(
adminID int AUTO_INCREMENT PRIMARY KEY,
adminUserName varchar(255) UNIQUE,
adminPassword varchar(255),
adminName varchar(255)
);


