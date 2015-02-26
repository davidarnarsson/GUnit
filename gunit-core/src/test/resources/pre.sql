-- MySQL Workbench Synchronization
-- Generated: 2015-02-26 20:04
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: davida

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE DATABASE IF NOT EXISTS `gunit_test`;

CREATE TABLE IF NOT EXISTS `gunit_test`.`User` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NULL DEFAULT NULL,
  `points` INT(11) NOT NULL DEFAULT 0,
  `lastWrittenTest` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  `totalWrittenTests` INT(11) NOT NULL DEFAULT 0,
  `lastInstructionCoverage` FLOAT(11) NOT NULL DEFAULT 0,
  `lastBranchCoverage` FLOAT(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `gunit_test`.`TestSuiteResult` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `elapsed` FLOAT(11) NOT NULL,
  `tests` INT(11) NOT NULL,
  `errors` INT(11) NOT NULL,
  `skipped` INT(11) NOT NULL,
  `failures` INT(11) NOT NULL,
  `sessionId` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `sessionId`),
  INDEX `fk_TestSuiteResult_Session1_idx` (`sessionId` ASC),
  CONSTRAINT `fk_TestSuiteResult_Session1`
    FOREIGN KEY (`sessionId`)
    REFERENCES `gunit_test`.`Session` (`sessionId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `gunit_test`.`SuiteTestCase` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `succeeded` BIT(1) NOT NULL,
  `error` TEXT NULL DEFAULT NULL,
  `className` VARCHAR(512) NOT NULL,
  `elapsed` FLOAT(11) NOT NULL,
  `suiteId` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_SuiteTestCase_TestSuiteResult1_idx` (`suiteId` ASC),
  CONSTRAINT `fk_SuiteTestCase_TestSuiteResult1`
    FOREIGN KEY (`suiteId`)
    REFERENCES `gunit_test`.`TestSuiteResult` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT ='';

CREATE TABLE IF NOT EXISTS `gunit_test`.`Badge` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `image` VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `gunit_test`.`UserBadges` (
  `userId` INT(11) NOT NULL,
  `badgeId` INT(11) NOT NULL,
  `earnedDate` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  `fromRuleId` INT(11) NULL DEFAULT NULL,
  `sessionId` INT(11) NULL DEFAULT NULL,
  INDEX `fk_UserBadges_User1_idx` (`userId` ASC),
  INDEX `fk_UserBadges_Badge1_idx` (`badgeId` ASC),
  PRIMARY KEY (`userId`, `badgeId`, `earnedDate`),
  INDEX `fk_UserBadges_FromRule_idx` (`fromRuleId` ASC),
  INDEX `fk_UserBadges_FromSession_idx` (`sessionId` ASC),
  CONSTRAINT `fk_UserBadges_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `gunit_test`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserBadges_Badge1`
    FOREIGN KEY (`badgeId`)
    REFERENCES `gunit_test`.`Badge` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserBadges_FromRule`
    FOREIGN KEY (`fromRuleId`)
    REFERENCES `gunit_test`.`Rule` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserBadges_FromSession`
    FOREIGN KEY (`sessionId`)
    REFERENCES `gunit_test`.`Session` (`sessionId`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `gunit_test`.`JaCoCoResult` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `className` VARCHAR(512) NOT NULL,
  `packageName` VARCHAR(128) NOT NULL,
  `groupName` VARCHAR(128) NOT NULL,
  `instructionMissed` INT(11) NOT NULL,
  `instructionCovered` INT(11) NOT NULL,
  `branchMissed` INT(11) NOT NULL,
  `branchCovered` INT(11) NOT NULL,
  `lineMissed` INT(11) NOT NULL,
  `lineCovered` INT(11) NOT NULL,
  `complexityMissed` INT(11) NOT NULL,
  `complexityCovered` INT(11) NOT NULL,
  `methodMissed` INT(11) NOT NULL,
  `methodCovered` INT(11) NOT NULL,
  `sessionId` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `sessionId`),
  INDEX `fk_JaCoCoResult_Session1_idx` (`sessionId` ASC),
  CONSTRAINT `fk_JaCoCoResult_Session1`
    FOREIGN KEY (`sessionId`)
    REFERENCES `gunit_test`.`Session` (`sessionId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `gunit_test`.`Rule` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `className` VARCHAR(512) NOT NULL,
  `points` INT(11) NULL DEFAULT 0,
  `successMessage` TEXT NULL DEFAULT NULL,
  `badgeId` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Rule_Badge1_idx` (`badgeId` ASC),
  CONSTRAINT `fk_Rule_Badge1`
    FOREIGN KEY (`badgeId`)
    REFERENCES `gunit_test`.`Badge` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS `gunit_test`.`Session` (
  `sessionId` INT(11) NOT NULL AUTO_INCREMENT,
  `userId` INT(11) NOT NULL,
  `date` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  `branchCoverage` FLOAT(11) NULL DEFAULT NULL,
  `lineCoverage` FLOAT(11) NULL DEFAULT NULL,
  `instructionCoverage` FLOAT(11) NULL DEFAULT NULL,
  `newTests` INT(11) NULL DEFAULT 0,
  `pointsCollected` INT(11) NULL DEFAULT 0,
  `badgesEarned` INT(11) NULL DEFAULT 0,
  `sessionStatus` INT(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`sessionId`),
  INDEX `fk_Session_User1_idx` (`userId` ASC),
  CONSTRAINT `fk_Session_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `gunit_test`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
