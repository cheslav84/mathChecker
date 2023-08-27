SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema equations
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `equations` DEFAULT CHARACTER SET utf8 ;

USE `equations` ;

-- -----------------------------------------------------
-- Table `equations`.`equation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `equations`.`equation` ;

CREATE TABLE IF NOT EXISTS `equations`.`equation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `equality` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_equality` (`equality` ASC),
  UNIQUE INDEX `equality_UNIQUE` (`equality` ASC));

-- -----------------------------------------------------
-- Table `equations`.`root`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `equations`.`root` ;

CREATE TABLE IF NOT EXISTS `equations`.`root` (
  `equation_id` INT NOT NULL,
  `root_number` DECIMAL(16,9),
  PRIMARY KEY (`equation_id`, `root_number`),
  INDEX `idx_root_number` (`root_number` ASC),
  CONSTRAINT `fk_equation_root`
    FOREIGN KEY (`equation_id`)
    REFERENCES `equations`.`equation` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
  );

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
