-- MySQL dump 10.15  Distrib 10.0.18-MariaDB, for Linux (x86_64)
CREATE DATABASE IF NOT EXISTS Hive;
USE Hive;

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` char(255) DEFAULT NULL,
  `LastName` varchar(1024) DEFAULT NULL,
  `Pword` varchar(100) NOT NULL,
  `Admin` tinyint(1) DEFAULT NULL,
  `Sensitiv` tinyint(1) DEFAULT NULL,
  `Email` varchar(1024) DEFAULT NULL,
  `LastLogin` datetime DEFAULT NULL,
  `Role` char(255) DEFAULT NULL,
  `Title` char(4) DEFAULT NULL,
  `Gender` tinyint(1) DEFAULT NULL,
  `Location` char(255) DEFAULT NULL,
  `Telephone` varchar(1024) DEFAULT NULL,
  `Website` varchar(1024) DEFAULT NULL,
  `Bio` text,
  `UserName` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (2,'Dian','Octaviani','ac5a5dd7658194dcdc9695bed9ff5a75',1,1,'admin@dataHive.com','2015-05-25 10:03:16','Developer','Mr.',1,'Perth','346345643','http://104.199.149.190','Hi','admin');
UNLOCK TABLES;

DROP TABLE IF EXISTS `Document`;
CREATE TABLE `Document` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UploadDate` datetime NOT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `hash` varchar(767) DEFAULT NULL,
  `Description` longtext,
  `Author` varchar(1024) DEFAULT NULL,
  `UserId` int(11) NOT NULL,
  `FileName` varchar(1024) DEFAULT NULL,
  `BoxViewID` varchar(1024) DEFAULT NULL,
  `FilePath` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `hashUQ` (`hash`),
  KEY `fk_Document_1_idx` (`UserId`),
  CONSTRAINT `fk_Document_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=latin1;

LOCK TABLES `Document` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `ViewHistory`;
CREATE TABLE `ViewHistory` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DateViewed` datetime NOT NULL,
  `UserId` int(11) NOT NULL,
  `FileId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `UserViewFK` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
