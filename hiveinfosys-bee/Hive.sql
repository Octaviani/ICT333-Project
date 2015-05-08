-- MySQL dump 10.15  Distrib 10.0.17-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: Hive
-- ------------------------------------------------------
-- Server version	10.0.17-MariaDB-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Annotation`
--

DROP TABLE IF EXISTS `Annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Annotation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CreatedDate` datetime NOT NULL,
  `FileId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `FileName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FileAnnoFk` (`FileId`),
  KEY `UserAnnoFk` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Annotation`
--

LOCK TABLES `Annotation` WRITE;
/*!40000 ALTER TABLE `Annotation` DISABLE KEYS */;
/*!40000 ALTER TABLE `Annotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `File`
--

DROP TABLE IF EXISTS `File`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `File` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FileName` varchar(150) NOT NULL,
  `UploadDate` datetime NOT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `DataType` varchar(20) DEFAULT NULL,
  `Author` varchar(50) DEFAULT NULL,
  `FilePath` varchar(500) NOT NULL,
  `UserId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UserFileFK` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `File`
--

LOCK TABLES `File` WRITE;
/*!40000 ALTER TABLE `File` DISABLE KEYS */;
/*!40000 ALTER TABLE `File` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `History`
--

DROP TABLE IF EXISTS `History`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `History` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SearchDate` datetime NOT NULL,
  `Keyword` varchar(100) NOT NULL,
  `UserId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UserFk` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `History`
--

LOCK TABLES `History` WRITE;
/*!40000 ALTER TABLE `History` DISABLE KEYS */;
/*!40000 ALTER TABLE `History` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` char(50) NOT NULL,
  `LastName` char(50) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Admin` tinyint(1) DEFAULT NULL,
  `Sensitiv` tinyint(1) DEFAULT NULL,
  `Email` varchar(200) DEFAULT NULL,
  `LastLogin` datetime DEFAULT NULL,
  `UserName` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UserName_UNIQUE` (`UserName`),
  UNIQUE KEY `Id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'Admin','Adminsur','5f4dcc3b5aa765d61d8327deb882cf99',1,1,'admin@hive.com','2015-05-04 12:04:20','admin');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ViewHistory`
--

DROP TABLE IF EXISTS `ViewHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ViewHistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `DateViewed` datetime NOT NULL,
  `UserId` int(11) NOT NULL,
  `FileId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FileViewFk` (`FileId`),
  KEY `UserViewFK` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ViewHistory`
--

LOCK TABLES `ViewHistory` WRITE;
/*!40000 ALTER TABLE `ViewHistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `ViewHistory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-06  1:45:05
