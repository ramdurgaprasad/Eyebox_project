DROP TABLE IF EXISTS signup;
DROP TABLE IF EXISTS profile;

CREATE TABLE signup (
`id` INT NOT NULL,
`user_Name` VARCHAR(255) NOT NULL,
`mobile_Number` BIGINT(12) NOT NULL,
`password` VARCHAR(45) NOT NULL,
`location` VARCHAR(45) NOT NULL,
`gender` VARCHAR(45) NOT NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `mobile_Number_UNIQUE` (`mobile_Number` ASC) VISIBLE);

#######
CREATE TABLE `profile` (
`id` INT NOT NULL,
`name` VARCHAR(265) NOT NULL,
`gmail` VARCHAR(255) NOT NULL,
`mobile` VARCHAR(45) NOT NULL,
`location` VARCHAR(45) NOT NULL,
`gender` VARCHAR(45) NOT NULL,
`image` LONGBLOB NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `gmail_UNIQUE` (`gmail` ASC) VISIBLE,
UNIQUE INDEX `mobile_UNIQUE` (`mobile` ASC) VISIBLE);