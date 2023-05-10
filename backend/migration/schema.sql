-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema CSCI5308_6_PRODUCTION
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema CSCI5308_6_PRODUCTION
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CSCI5308_6_PRODUCTION`;
USE `CSCI5308_6_PRODUCTION` ;

-- -----------------------------------------------------
-- Table `CSCI5308_6_PRODUCTION`.`ClassificationTag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_PRODUCTION`.`ClassificationTag` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tag_name` VARCHAR(45) NOT NULL,
  `project_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `CSCI5308_6_PRODUCTION`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_PRODUCTION`.`User` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` TEXT NOT NULL,
  `last_name` TEXT NOT NULL,
  `email` TEXT NOT NULL,
  `password` TEXT NOT NULL,
  `role` TEXT NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `CSCI5308_6_PRODUCTION`.`Project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_PRODUCTION`.`Project` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` TEXT NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `CSCI5308_6_PRODUCTION`.`DataTask`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_PRODUCTION`.`DataTask` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `classification_id` INT(11) NULL DEFAULT NULL,
  `project_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_DataTask_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DataTask_2`
    FOREIGN KEY (`classification_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`ClassificationTag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DataTask_Project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `CSCI5308_6_PRODUCTION`.`EntityTag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_PRODUCTION`.`EntityTag` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tag_name` TEXT NOT NULL,
  `project_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_EntityTag_Project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `CSCI5308_6_PRODUCTION`.`EntityMapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_PRODUCTION`.`EntityMapping` (
  `start` INT(11) NOT NULL,
  `end` INT(11) NOT NULL,
  `entity_id` INT(11) NOT NULL,
  `datatask_id` INT(11) NOT NULL,
  CONSTRAINT `EntityMapping_ibfk_1`
    FOREIGN KEY (`entity_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`EntityTag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EntityTag_DataTask1`
    FOREIGN KEY (`datatask_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`DataTask` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `CSCI5308_6_PRODUCTION`.`Export`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_PRODUCTION`.`Export` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `export_date` DATETIME NOT NULL,
  `status` TEXT NOT NULL,
  `record_count` INT(11) NOT NULL,
  `url` TEXT NULL DEFAULT NULL,
  `project_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Export_Project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `CSCI5308_6_PRODUCTION`.`Import`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_PRODUCTION`.`Import` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `import_date` DATETIME NOT NULL,
  `file_name` TEXT NOT NULL,
  `record_count` INT(11) NOT NULL,
  `status` TEXT NOT NULL,
  `project_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Import_Project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `CSCI5308_6_PRODUCTION`.`Import_has_User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_PRODUCTION`.`Import_has_User` (
  `import_id` INT(11) NOT NULL,
  `import_project_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  CONSTRAINT `Import_has_User_ibfk_1`
    FOREIGN KEY (`import_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`Import` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Import_has_User_1`
    FOREIGN KEY (`import_project_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Import_has_User_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `CSCI5308_6_PRODUCTION`.`Project_has_User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_PRODUCTION`.`Project_has_User` (
  `project_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  CONSTRAINT `fk_Project_has_User_Project`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`Project` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_User_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `CSCI5308_6_PRODUCTION`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
