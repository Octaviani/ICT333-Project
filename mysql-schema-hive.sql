-- MySQL dump 10.15  Distrib 10.0.18-MariaDB, for Linux (x86_64)
CREATE DATABASE IF NOT EXISTS Hive;
USE Hive;


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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Document`
--

LOCK TABLES `Document` WRITE;
UNLOCK TABLES;


DROP TABLE IF EXISTS `Annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Annotation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CreatedDate` datetime NOT NULL,
  `FileId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `FileName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FileAnnoFk` (`FileId`),
  KEY `UserAnnoFk` (`UserId`),
  CONSTRAINT `FileAnnoFk` FOREIGN KEY (`FileId`) REFERENCES `VersionInfo` (`ID`),
  CONSTRAINT `UserAnnoFk` FOREIGN KEY (`UserId`) REFERENCES `User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FileInfo`
--

DROP TABLE IF EXISTS `FileInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FileInfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FileName` varchar(767) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `FileNameKey` (`FileName`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `History`
--

DROP TABLE IF EXISTS `History`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `History` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SearchDate` datetime NOT NULL,
  `Keyword` text,
  `UserId` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `UserFk` (`UserId`),
  CONSTRAINT `UserFk` FOREIGN KEY (`UserId`) REFERENCES `User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (2,'MyAdmin','myLastName','ac5a5dd7658194dcdc9695bed9ff5a75',1,1,'admin@dataHive.com','2015-05-25 10:03:16','Developer','Mr.',1,'Perth','346345643','http://104.199.149.190','I am the administrator for the System','admin');
UNLOCK TABLES;

-- Table structure for table `VersionInfo`
--

DROP TABLE IF EXISTS `VersionInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VersionInfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UploadDate` datetime NOT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `CrcHash` varchar(767) DEFAULT NULL,
  `Description` longtext,
  `Author` varchar(1024) DEFAULT NULL,
  `UserId` int(11) NOT NULL,
  `FileInfoId` int(11) DEFAULT NULL,
  `BoxViewID` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CrcHashUQ` (`CrcHash`),
  KEY `UserFileFK` (`UserId`),
  KEY `FileInfoId` (`FileInfoId`),
  CONSTRAINT `UserFileFK` FOREIGN KEY (`UserId`) REFERENCES `User` (`ID`),
  CONSTRAINT `VersionInfo_ibfk_1` FOREIGN KEY (`FileInfoId`) REFERENCES `FileInfo` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ViewHistory`
--

DROP TABLE IF EXISTS `ViewHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ViewHistory` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DateViewed` datetime NOT NULL,
  `UserId` int(11) NOT NULL,
  `FileId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FileViewFk` (`FileId`),
  KEY `UserViewFK` (`UserId`),
  CONSTRAINT `FileViewFk` FOREIGN KEY (`FileId`) REFERENCES `VersionInfo` (`ID`),
  CONSTRAINT `UserViewFK` FOREIGN KEY (`UserId`) REFERENCES `User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-04 13:40:42
