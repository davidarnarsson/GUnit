-- MySQL dump 10.13  Distrib 5.6.23, for osx10.8 (x86_64)
--
-- Host: localhost    Database: gunit
-- ------------------------------------------------------
-- Server version	5.6.23

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
-- Table structure for table `JaCoCoResult`
--

DROP TABLE IF EXISTS `JaCoCoResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JaCoCoResult` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(512) NOT NULL,
  `packageName` varchar(128) NOT NULL,
  `groupName` varchar(128) NOT NULL,
  `instructionMissed` int(11) NOT NULL,
  `instructionCovered` int(11) NOT NULL,
  `branchMissed` int(11) NOT NULL,
  `branchCovered` int(11) NOT NULL,
  `lineMissed` int(11) NOT NULL,
  `lineCovered` int(11) NOT NULL,
  `complexityMissed` int(11) NOT NULL,
  `complexityCovered` int(11) NOT NULL,
  `methodMissed` int(11) NOT NULL,
  `methodCovered` int(11) NOT NULL,
  `sessionId` int(11) NOT NULL,
  PRIMARY KEY (`id`,`sessionId`),
  KEY `fk_JaCoCoResult_Session1_idx` (`sessionId`),
  CONSTRAINT `fk_JaCoCoResult_Session1` FOREIGN KEY (`sessionId`) REFERENCES `Session` (`sessionId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1135 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JaCoCoResult`
--


--
-- Table structure for table `NewsFeed`
--

DROP TABLE IF EXISTS `NewsFeed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NewsFeed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `regardingUserId` int(11) DEFAULT NULL,
  `regardingBadgeId` int(11) DEFAULT NULL,
  `messageType` int(11) DEFAULT NULL,
  `message` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NewsFeed`
--

LOCK TABLES `NewsFeed` WRITE;
/*!40000 ALTER TABLE `NewsFeed` DISABLE KEYS */;
/*!40000 ALTER TABLE `NewsFeed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RuleResult`
--

DROP TABLE IF EXISTS `RuleResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RuleResult` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `points` int(11) NOT NULL,
  `sessionId` int(11) NOT NULL,
  `ruleId` int(11) NOT NULL,
  `message` text,
  PRIMARY KEY (`id`),
  KEY `fk_ruleId_idx` (`ruleId`),
  KEY `fk_sessionId_idx` (`sessionId`),
  CONSTRAINT `fk_ruleId` FOREIGN KEY (`ruleId`) REFERENCES `Rule` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessionId` FOREIGN KEY (`sessionId`) REFERENCES `Session` (`sessionId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RuleResult`
--

--
-- Table structure for table `Session`
--

DROP TABLE IF EXISTS `Session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Session` (
  `sessionId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `branchCoverage` float DEFAULT NULL,
  `lineCoverage` float DEFAULT NULL,
  `instructionCoverage` float DEFAULT NULL,
  `newTests` int(11) DEFAULT '0',
  `pointsCollected` int(11) DEFAULT '0',
  `badgesEarned` int(11) DEFAULT '0',
  `sessionStatus` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sessionId`),
  KEY `fk_Session_User1_idx` (`userId`),
  CONSTRAINT `fk_Session_User1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Session`
--


--
-- Table structure for table `SuiteTestCase`
--

DROP TABLE IF EXISTS `SuiteTestCase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SuiteTestCase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `succeeded` bit(1) NOT NULL,
  `error` text,
  `className` varchar(512) NOT NULL,
  `elapsed` float NOT NULL,
  `suiteId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_SuiteTestCase_TestSuiteResult1_idx` (`suiteId`),
  CONSTRAINT `fk_SuiteTestCase_TestSuiteResult1` FOREIGN KEY (`suiteId`) REFERENCES `TestSuiteResult` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=731 DEFAULT CHARSET=utf8 COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SuiteTestCase`
--

--
-- Table structure for table `TestSuiteResult`
--

DROP TABLE IF EXISTS `TestSuiteResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestSuiteResult` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `elapsed` float NOT NULL,
  `tests` int(11) NOT NULL,
  `errors` int(11) NOT NULL,
  `skipped` int(11) NOT NULL,
  `failures` int(11) NOT NULL,
  `sessionId` int(11) NOT NULL,
  PRIMARY KEY (`id`,`sessionId`),
  KEY `fk_TestSuiteResult_Session1_idx` (`sessionId`),
  CONSTRAINT `fk_TestSuiteResult_Session1` FOREIGN KEY (`sessionId`) REFERENCES `Session` (`sessionId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=266 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestSuiteResult`
--


--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `points` int(11) NOT NULL DEFAULT '0',
  `lastWrittenTest` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `totalWrittenTests` int(11) NOT NULL DEFAULT '0',
  `lastInstructionCoverage` float NOT NULL DEFAULT '0',
  `lastBranchCoverage` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

--
-- Table structure for table `UserBadges`
--

DROP TABLE IF EXISTS `UserBadges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserBadges` (
  `userId` int(11) NOT NULL,
  `badgeId` int(11) NOT NULL,
  `earnedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fromRuleId` int(11) DEFAULT NULL,
  `sessionId` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`,`badgeId`,`earnedDate`),
  KEY `fk_UserBadges_User1_idx` (`userId`),
  KEY `fk_UserBadges_Badge1_idx` (`badgeId`),
  KEY `fk_UserBadges_FromRule_idx` (`fromRuleId`),
  KEY `fk_UserBadges_FromSession_idx` (`sessionId`),
  CONSTRAINT `fk_UserBadges_Badge1` FOREIGN KEY (`badgeId`) REFERENCES `Badge` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserBadges_FromRule` FOREIGN KEY (`fromRuleId`) REFERENCES `rule` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserBadges_FromSession` FOREIGN KEY (`sessionId`) REFERENCES `Session` (`sessionId`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `fk_UserBadges_User1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserBadges`
--


--
-- Table structure for table `badge`
--

DROP TABLE IF EXISTS `badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `badge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `image` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `badge`
--

LOCK TABLES `badge` WRITE;
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` VALUES (1,'Próf-á-dag!','http://gunit.axlabond.in/images/testaday.png');
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rule`
--

DROP TABLE IF EXISTS `rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(512) NOT NULL,
  `points` int(11) DEFAULT '0',
  `successMessage` text,
  `badgeId` int(11) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `fk_Rule_Badge1_idx` (`badgeId`),
  CONSTRAINT `fk_Rule_Badge1` FOREIGN KEY (`badgeId`) REFERENCES `badge` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rule`
--

LOCK TABLES `rule` WRITE;
/*!40000 ALTER TABLE `rule` DISABLE KEYS */;
INSERT INTO `rule` VALUES (1,'edu.chl.gunit.core.gamification.rules.TestADayRule',20,'Að skrifa eitt einingapróf á dag kemur skapinu í lag!',1,'Skrifa nýtt einingapróf daglega.'),(2,'edu.chl.gunit.core.gamification.rules.InstructionCoverageRule',0,NULL,NULL,NULL),(3,'edu.chl.gunit.core.gamification.rules.BranchCoverageRule',0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `rule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-09 11:03:29
