CREATE SCHEMA IF NOT EXISTS smart_care;
SET SCHEMA smart_care;

CREATE TABLE `treatment_plan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `treatment_action` varchar(45) DEFAULT NULL,
  `subject_patient` varchar(45) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `recurrence_pattern` varchar(45) DEFAULT NULL,
  `recurrence_periodicity` int DEFAULT NULL,
  `recurrence_start_time` time DEFAULT NULL,
  `recurrence_end_time` time DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `treatment_task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `treatment_action` varchar(45) DEFAULT NULL,
  `subject_patient` varchar(45) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


