-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ballot`
--

DROP TABLE IF EXISTS `ballot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ballot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `r` varchar(255) DEFAULT NULL,
  `z` varchar(255) DEFAULT NULL,
  `ballotid` int(11) DEFAULT NULL,
  `c1` varchar(255) DEFAULT NULL,
  `c2` varchar(255) DEFAULT NULL,
  `confirmation_code` varchar(255) DEFAULT NULL,
  `r1` varchar(255) DEFAULT NULL,
  `r2` varchar(255) DEFAULT NULL,
  `random` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `v` int(11) DEFAULT NULL,
  `topic_electionid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqhdg86cab5vemfhkca5p166ib` (`topic_electionid`),
  CONSTRAINT `FKqhdg86cab5vemfhkca5p166ib` FOREIGN KEY (`topic_electionid`) REFERENCES `topic` (`electionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ballot`
--

LOCK TABLES `ballot` WRITE;
/*!40000 ALTER TABLE `ballot` DISABLE KEYS */;
/*!40000 ALTER TABLE `ballot` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-05 22:45:54
