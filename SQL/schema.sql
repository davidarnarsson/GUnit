-- MySQL Script generated by MySQL Workbench
-- 02/21/15 15:12:05
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema gunit
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `gunit` ;

-- -----------------------------------------------------
-- Schema gunit
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gunit` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `gunit` ;

-- -----------------------------------------------------
-- Table `gunit`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gunit`.`User` ;

CREATE TABLE IF NOT EXISTS `gunit`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NULL,
  `points` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gunit`.`TestSuiteResult`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gunit`.`TestSuiteResult` ;

CREATE TABLE IF NOT EXISTS `gunit`.`TestSuiteResult` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `elapsed` FLOAT NOT NULL,
  `tests` INT NOT NULL,
  `errors` INT NOT NULL,
  `skipped` INT NOT NULL,
  `failures` INT NOT NULL,
  `userId` INT NOT NULL,
  `date` DATETIME NULL DEFAULT curdate(),
  PRIMARY KEY (`id`),
  INDEX `fk_TestSuiteResult_User_idx` (`userId` ASC),
  CONSTRAINT `fk_TestSuiteResult_User`
    FOREIGN KEY (`userId`)
    REFERENCES `gunit`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gunit`.`SuiteTestCase`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gunit`.`SuiteTestCase` ;

CREATE TABLE IF NOT EXISTS `gunit`.`SuiteTestCase` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `succeeded` BIT NOT NULL,
  `error` TEXT NOT NULL,
  `className` VARCHAR(512) NOT NULL,
  `elapsed` FLOAT NOT NULL,
  `suiteId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_SuiteTestCase_TestSuiteResult1_idx` (`suiteId` ASC),
  CONSTRAINT `fk_SuiteTestCase_TestSuiteResult1`
    FOREIGN KEY (`suiteId`)
    REFERENCES `gunit`.`TestSuiteResult` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '		';


-- -----------------------------------------------------
-- Table `gunit`.`Badge`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gunit`.`Badge` ;

CREATE TABLE IF NOT EXISTS `gunit`.`Badge` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `image` VARCHAR(256) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gunit`.`UserBadges`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gunit`.`UserBadges` ;

CREATE TABLE IF NOT EXISTS `gunit`.`UserBadges` (
  `userId` INT NOT NULL,
  `badgeId` INT NOT NULL,
  `earnedDate` DATETIME NULL,
  INDEX `fk_UserBadges_User1_idx` (`userId` ASC),
  INDEX `fk_UserBadges_Badge1_idx` (`badgeId` ASC),
  PRIMARY KEY (`userId`, `badgeId`),
  CONSTRAINT `fk_UserBadges_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `gunit`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserBadges_Badge1`
    FOREIGN KEY (`badgeId`)
    REFERENCES `gunit`.`Badge` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gunit`.`JaCoCoResult`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gunit`.`JaCoCoResult` ;

CREATE TABLE IF NOT EXISTS `gunit`.`JaCoCoResult` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `className` VARCHAR(512) NOT NULL,
  `packageName` VARCHAR(128) NOT NULL,
  `groupName` VARCHAR(128) NOT NULL,
  `instructionMissed` INT NOT NULL,
  `instructionCovered` INT NOT NULL,
  `branchMissed` INT NOT NULL,
  `branchCovered` INT NOT NULL,
  `lineMissed` INT NOT NULL,
  `lineCovered` INT NOT NULL,
  `complexityMissed` INT NOT NULL,
  `complexityCovered` INT NOT NULL,
  `methodMissed` INT NOT NULL,
  `methodCovered` INT NOT NULL,
  `userId` INT NOT NULL,
  `date` DATETIME NOT NULL DEFAULT curdate(),
  PRIMARY KEY (`id`),
  INDEX `fk_JaCoCoResult_User1_idx` (`userId` ASC),
  CONSTRAINT `fk_JaCoCoResult_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `gunit`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gunit`.`Rule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gunit`.`Rule` ;

CREATE TABLE IF NOT EXISTS `gunit`.`Rule` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `className` VARCHAR(512) NOT NULL,
  `points` INT NULL DEFAULT 0,
  `successMessage` TEXT NULL,
  `badgeId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Rule_Badge1_idx` (`badgeId` ASC),
  CONSTRAINT `fk_Rule_Badge1`
    FOREIGN KEY (`badgeId`)
    REFERENCES `gunit`.`Badge` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
