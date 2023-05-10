-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


USE `CSCI5308_6_DEVINT` ;

-- -----------------------------------------------------
-- Table `CSCI5308_6_DEVINT`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_DEVINT`.`User` (
  `id` INT NOT NULL,
  `first_name` TEXT NOT NULL,
  `last_name` TEXT NOT NULL,
  `email` TEXT NOT NULL,
  `password` TEXT NOT NULL,
  `role` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_6_DEVINT`.`Project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_DEVINT`.`Project` (
  `id` INT NOT NULL,
  `name` TEXT NOT NULL,
  `status` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_6_DEVINT`.`Project_has_User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_DEVINT`.`Project_has_User` (
  `project_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`project_id`, `user_id`),
  INDEX `fk_Project_has_User_User1_idx` (`user_id` ASC),
  INDEX `fk_Project_has_User_Project_idx` (`project_id` ASC),
  CONSTRAINT `fk_Project_has_User_Project`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_User_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_6_DEVINT`.`Import`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_DEVINT`.`Import` (
  `id` INT NOT NULL,
  `import_date` DATETIME NOT NULL,
  `file_name` TEXT NOT NULL,
  `record_count` INT NOT NULL,
  `status` TEXT NOT NULL,
  `project_id` INT NOT NULL,
  PRIMARY KEY (`id`, `project_id`),
  INDEX `fk_Import_Project1_idx` (`project_id` ASC) ,
  CONSTRAINT `fk_Import_Project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_6_DEVINT`.`Import_has_User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_DEVINT`.`Import_has_User` (
  `import_id` INT NOT NULL,
  `import_project_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`import_id`, `import_project_id`, `user_id`),
  INDEX `fk_Import_has_User_User1_idx` (`user_id` ASC) ,
  CONSTRAINT `fk_Import_has_User_Import1`
    FOREIGN KEY (`import_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`Import` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Import_has_User_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_6_DEVINT`.`DataTask`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_DEVINT`.`DataTask` (
  `id` INT NOT NULL,
  `content` TEXT NOT NULL,
  `classification_tag` TEXT NOT NULL,
  `project_id` INT NOT NULL,
  `user_id` INT,
  PRIMARY KEY (`id`, `project_id`),
  INDEX `fk_DataTask_Project1_idx` (`project_id` ASC) ,
  CONSTRAINT `fk_DataTask_Project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DataTask_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_6_DEVINT`.`EntityMapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_DEVINT`.`EntityMapping` (
  `start` INT NOT NULL,
  `end` INT NOT NULL,
  `entity` TEXT NOT NULL,
  `datatask_id` INT NOT NULL,
  CONSTRAINT `fk_EntityTag_DataTask1`
    FOREIGN KEY (`datatask_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`DataTask` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_6_DEVINT`.`EntityTag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_DEVINT`.`EntityTag` (
  `id` INT NOT NULL,
  `tag_name` TEXT NOT NULL,
  `project_id` INT NOT NULL,
  PRIMARY KEY (`id`, `project_id`),
  INDEX `fk_EntityTag_Project1_idx` (`project_id` ASC) ,
  CONSTRAINT `fk_EntityTag_Project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_6_DEVINT`.`ClassificationTag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_DEVINT`.`ClassificationTag` (
  `id` INT NOT NULL,
  `tag_name` TEXT NOT NULL,
  `project_id` INT NOT NULL,
  PRIMARY KEY (`id`, `project_id`),
  INDEX `fk_ClassificationTag_Project1_idx` (`project_id` ASC) ,
  CONSTRAINT `fk_ClassificationTag_Project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_6_DEVINT`.`Export`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_6_DEVINT`.`Export` (
  `id` INT NOT NULL,
  `exported_date` DATETIME NOT NULL,
  `status` TEXT NOT NULL,
  `record_count` INT NOT NULL,
  `url` TEXT,
  `project_id` INT NOT NULL,
  PRIMARY KEY (`id`, `project_id`),
  INDEX `fk_Export_Project1_idx` (`project_id` ASC) ,
  CONSTRAINT `fk_Export_Project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `CSCI5308_6_DEVINT`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
