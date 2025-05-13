-- MySQL dump 10.13  Distrib 8.0.41, for Linux (x86_64)
--
-- Host: localhost    Database: xinyanhui
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Admins`
--

DROP TABLE IF EXISTS `Admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Admins` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password_HashwithSalt` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=454 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Appointments`
--

DROP TABLE IF EXISTS `Appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Appointments` (
  `appointment_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `consultant_id` int NOT NULL,
  `appointment_date` date NOT NULL,
  `appointment_time` time NOT NULL,
  `booking_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` enum('booked','canceled','completed') NOT NULL,
  `cancellation_time` datetime DEFAULT NULL,
  `cancellation_reason` text,
  PRIMARY KEY (`appointment_id`),
  KEY `user_id` (`user_id`),
  KEY `consultant_id` (`consultant_id`),
  CONSTRAINT `Appointments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
  CONSTRAINT `Appointments_ibfk_2` FOREIGN KEY (`consultant_id`) REFERENCES `Consultants` (`consultant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6516 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `AppointmentsWithNames`
--

DROP TABLE IF EXISTS `AppointmentsWithNames`;
/*!50001 DROP VIEW IF EXISTS `AppointmentsWithNames`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `AppointmentsWithNames` AS SELECT 
 1 AS `appointment_id`,
 1 AS `user_id`,
 1 AS `consultant_id`,
 1 AS `appointment_date`,
 1 AS `appointment_time`,
 1 AS `booking_date`,
 1 AS `status`,
 1 AS `cancellation_time`,
 1 AS `cancellation_reason`,
 1 AS `consultant_name`,
 1 AS `user_username`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `ConsultantDailyAvailability`
--

DROP TABLE IF EXISTS `ConsultantDailyAvailability`;
/*!50001 DROP VIEW IF EXISTS `ConsultantDailyAvailability`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ConsultantDailyAvailability` AS SELECT 
 1 AS `consultant_id`,
 1 AS `available_date`,
 1 AS `hour_8_available`,
 1 AS `hour_9_available`,
 1 AS `hour_10_available`,
 1 AS `hour_11_available`,
 1 AS `hour_12_available`,
 1 AS `hour_13_available`,
 1 AS `hour_14_available`,
 1 AS `hour_15_available`,
 1 AS `hour_16_available`,
 1 AS `hour_17_available`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `ConsultantSchedules`
--

DROP TABLE IF EXISTS `ConsultantSchedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ConsultantSchedules` (
  `schedule_id` int NOT NULL AUTO_INCREMENT,
  `consultant_id` int NOT NULL,
  `available_date` date NOT NULL,
  `start_time` int NOT NULL,
  `end_time` int NOT NULL,
  `slot_capacity` int DEFAULT '5',
  `status` enum('normal','request','leave') NOT NULL DEFAULT 'normal' COMMENT '正常，申请（请假），请假',
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`schedule_id`),
  KEY `consultant_id` (`consultant_id`),
  KEY `available_date` (`available_date`),
  CONSTRAINT `ConsultantSchedules_ibfk_1` FOREIGN KEY (`consultant_id`) REFERENCES `Consultants` (`consultant_id`),
  CONSTRAINT `ConsultantSchedules_chk_1` CHECK (((`start_time` >= 0) and (`start_time` <= 24))),
  CONSTRAINT `ConsultantSchedules_chk_2` CHECK (((`end_time` >= 0) and (`end_time` <= 24)))
) ENGINE=InnoDB AUTO_INCREMENT=2491 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `trg_cancel_appointments_on_leave` AFTER UPDATE ON `ConsultantSchedules` FOR EACH ROW BEGIN
    -- 只有当 status 从非 leave 变为 leave 时才触发逻辑
    IF NEW.status = 'leave' AND OLD.status != 'leave' THEN
        UPDATE Appointments
        SET status = 'canceled',
            cancellation_time = NOW(),
            cancellation_reason = '咨询师请假'
        WHERE consultant_id = NEW.consultant_id
          AND appointment_time BETWEEN 
              MAKEDATE(YEAR(NEW.available_date), DAYOFYEAR(NEW.available_date)) + INTERVAL NEW.start_time HOUR
              AND MAKEDATE(YEAR(NEW.available_date), DAYOFYEAR(NEW.available_date)) + INTERVAL NEW.end_time HOUR
          AND status NOT IN ('canceled', 'completed'); -- 只取消未完成未取消的预约
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Consultants`
--

DROP TABLE IF EXISTS `Consultants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Consultants` (
  `consultant_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `professional_info` text,
  `password_HashwithSalt` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `supervisor_id` int DEFAULT '24',
  `employed` bit(1) DEFAULT b'1',
  PRIMARY KEY (`consultant_id`),
  KEY `superviser_id` (`supervisor_id`),
  CONSTRAINT `Consultants_ibfk_1` FOREIGN KEY (`supervisor_id`) REFERENCES `Supervisors` (`supervisor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1746712595 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ConsultationSessions`
--

DROP TABLE IF EXISTS `ConsultationSessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ConsultationSessions` (
  `session_id` int NOT NULL AUTO_INCREMENT,
  `appointment_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `consultant_id` int NOT NULL,
  `start_time` datetime NOT NULL,
  `session_status` enum('active','completed','ready') NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `rating` tinyint DEFAULT NULL,
  `feedback` text,
  PRIMARY KEY (`session_id`),
  UNIQUE KEY `appointment_id` (`appointment_id`),
  KEY `user_id` (`user_id`),
  KEY `consultant_id` (`consultant_id`),
  CONSTRAINT `ConsultationSessions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
  CONSTRAINT `ConsultationSessions_ibfk_2` FOREIGN KEY (`consultant_id`) REFERENCES `Consultants` (`consultant_id`),
  CONSTRAINT `ConsultationSessions_ibfk_3` FOREIGN KEY (`appointment_id`) REFERENCES `Appointments` (`appointment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2846 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `ConsultationSessionsWithNames`
--

DROP TABLE IF EXISTS `ConsultationSessionsWithNames`;
/*!50001 DROP VIEW IF EXISTS `ConsultationSessionsWithNames`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ConsultationSessionsWithNames` AS SELECT 
 1 AS `session_id`,
 1 AS `appointment_id`,
 1 AS `user_id`,
 1 AS `consultant_id`,
 1 AS `start_time`,
 1 AS `session_status`,
 1 AS `end_time`,
 1 AS `rating`,
 1 AS `feedback`,
 1 AS `consultant_name`,
 1 AS `user_username`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `CurrentAvailability`
--

DROP TABLE IF EXISTS `CurrentAvailability`;
/*!50001 DROP VIEW IF EXISTS `CurrentAvailability`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `CurrentAvailability` AS SELECT 
 1 AS `consultant_id`,
 1 AS `active_sessions`,
 1 AS `ready_sessions`,
 1 AS `is_available`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `DataBrd_MonthlyTimeRank`
--

DROP TABLE IF EXISTS `DataBrd_MonthlyTimeRank`;
/*!50001 DROP VIEW IF EXISTS `DataBrd_MonthlyTimeRank`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `DataBrd_MonthlyTimeRank` AS SELECT 
 1 AS `consultant_id`,
 1 AS `supervisor_id`,
 1 AS `ym`,
 1 AS `session_count`,
 1 AS `total_minutes`,
 1 AS `global_rank`,
 1 AS `group_rank`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `DataBrd_WeeklyConsultationStats`
--

DROP TABLE IF EXISTS `DataBrd_WeeklyConsultationStats`;
/*!50001 DROP VIEW IF EXISTS `DataBrd_WeeklyConsultationStats`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `DataBrd_WeeklyConsultationStats` AS SELECT 
 1 AS `year_week`,
 1 AS `week_start_date`,
 1 AS `week_end_date`,
 1 AS `total_sessions`,
 1 AS `total_duration_minutes`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `Notification`
--

DROP TABLE IF EXISTS `Notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Notification` (
  `notf_id` int NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `create_time` datetime NOT NULL COMMENT 'Create Time',
  `content` varchar(255) NOT NULL,
  `rec_role` enum('User','Admin','Consultant','Supervisor') NOT NULL COMMENT 'Recipient Role',
  `rec_id` int NOT NULL COMMENT 'Recipient ID',
  `status` enum('New','Read','Deleted') NOT NULL DEFAULT 'New',
  PRIMARY KEY (`notf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PsychologicalEvaluations`
--

DROP TABLE IF EXISTS `PsychologicalEvaluations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PsychologicalEvaluations` (
  `evaluation_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `evaluation_date` datetime NOT NULL,
  `questionnaire_results` json NOT NULL,
  `auto_report` text,
  `risk_level` enum('low','medium','high') NOT NULL,
  `suggestions` text,
  PRIMARY KEY (`evaluation_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `PsychologicalEvaluations_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RecordChatLog`
--

DROP TABLE IF EXISTS `RecordChatLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RecordChatLog` (
  `message_id` int NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `record_id` int NOT NULL COMMENT '对应SupervisorConsultations中记录',
  `sender_type` bit(1) NOT NULL COMMENT '0督导，1咨询师',
  `time` datetime NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `record_id` (`record_id`),
  CONSTRAINT `RecordChatLog_ibfk_1` FOREIGN KEY (`record_id`) REFERENCES `SupervisorConsultations` (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SessionChatLog`
--

DROP TABLE IF EXISTS `SessionChatLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SessionChatLog` (
  `message_id` int NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `session_id` int NOT NULL COMMENT '对应ConsultationSessions中记录',
  `sender_type` bit(1) NOT NULL COMMENT '0用户，1咨询师',
  `time` datetime NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `session_id` (`session_id`),
  CONSTRAINT `SessionChatLog_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `ConsultationSessions` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=395 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SupervisorConsultations`
--

DROP TABLE IF EXISTS `SupervisorConsultations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SupervisorConsultations` (
  `record_id` int NOT NULL AUTO_INCREMENT COMMENT '督导帮助会话的记录id（主键）',
  `consultant_id` int NOT NULL,
  `supervisor_id` int NOT NULL,
  `session_id` int NOT NULL COMMENT '对应咨询会话的id（对应ConsultationSessions表中记录）',
  `request_time` datetime NOT NULL COMMENT '咨询师请求帮助的时间',
  `response_time` datetime DEFAULT NULL COMMENT '督导第一条回复的时间',
  PRIMARY KEY (`record_id`),
  KEY `consultant_id` (`consultant_id`),
  KEY `supervisor_id` (`supervisor_id`),
  KEY `session_id` (`session_id`),
  CONSTRAINT `SupervisorConsultations_ibfk_1` FOREIGN KEY (`consultant_id`) REFERENCES `Consultants` (`consultant_id`),
  CONSTRAINT `SupervisorConsultations_ibfk_2` FOREIGN KEY (`supervisor_id`) REFERENCES `Supervisors` (`supervisor_id`),
  CONSTRAINT `SupervisorConsultations_ibfk_3` FOREIGN KEY (`session_id`) REFERENCES `ConsultationSessions` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `SupervisorConsultationsWithNames`
--

DROP TABLE IF EXISTS `SupervisorConsultationsWithNames`;
/*!50001 DROP VIEW IF EXISTS `SupervisorConsultationsWithNames`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `SupervisorConsultationsWithNames` AS SELECT 
 1 AS `record_id`,
 1 AS `consultant_id`,
 1 AS `supervisor_id`,
 1 AS `session_id`,
 1 AS `request_time`,
 1 AS `response_time`,
 1 AS `consultant_name`,
 1 AS `supervisor_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `SupervisorSchedules`
--

DROP TABLE IF EXISTS `SupervisorSchedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SupervisorSchedules` (
  `schedule_id` int NOT NULL AUTO_INCREMENT,
  `supervisor_id` int NOT NULL,
  `available_date` date NOT NULL,
  `start_time` int NOT NULL,
  `end_time` int NOT NULL,
  `slot_capacity` int DEFAULT '5',
  PRIMARY KEY (`schedule_id`),
  KEY `supervisor_id` (`supervisor_id`),
  CONSTRAINT `SupervisorSchedules_ibfk_1` FOREIGN KEY (`supervisor_id`) REFERENCES `Supervisors` (`supervisor_id`),
  CONSTRAINT `SupervisorSchedules_chk_1` CHECK (((`start_time` >= 0) and (`start_time` <= 24))),
  CONSTRAINT `SupervisorSchedules_chk_2` CHECK (((`end_time` >= 0) and (`end_time` <= 24)))
) ENGINE=InnoDB AUTO_INCREMENT=778 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Supervisors`
--

DROP TABLE IF EXISTS `Supervisors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Supervisors` (
  `supervisor_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `professional_info` text,
  `password_HashwithSalt` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `employed` bit(1) DEFAULT b'1',
  PRIMARY KEY (`supervisor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1746713302 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password_HashwithSalt` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `register_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=286 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `View_ConsultantDailyAppointments`
--

DROP TABLE IF EXISTS `View_ConsultantDailyAppointments`;
/*!50001 DROP VIEW IF EXISTS `View_ConsultantDailyAppointments`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `View_ConsultantDailyAppointments` AS SELECT 
 1 AS `appointment_date`,
 1 AS `consultant_id`,
 1 AS `total_appointments`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `View_ConsultantHourlyAppointments`
--

DROP TABLE IF EXISTS `View_ConsultantHourlyAppointments`;
/*!50001 DROP VIEW IF EXISTS `View_ConsultantHourlyAppointments`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `View_ConsultantHourlyAppointments` AS SELECT 
 1 AS `schedule_id`,
 1 AS `consultant_id`,
 1 AS `available_date`,
 1 AS `start_time`,
 1 AS `end_time`,
 1 AS `appointment_time`,
 1 AS `appointment_count`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `View_DailyAppointments`
--

DROP TABLE IF EXISTS `View_DailyAppointments`;
/*!50001 DROP VIEW IF EXISTS `View_DailyAppointments`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `View_DailyAppointments` AS SELECT 
 1 AS `appointment_date`,
 1 AS `total_appointments`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `View_HourlyAppointments`
--

DROP TABLE IF EXISTS `View_HourlyAppointments`;
/*!50001 DROP VIEW IF EXISTS `View_HourlyAppointments`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `View_HourlyAppointments` AS SELECT 
 1 AS `appointment_date`,
 1 AS `hour_slot`,
 1 AS `appointments_per_hour`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'xinyanhui'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `CheckAppointmentsAndCreateSessions` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`%`*/ /*!50106 EVENT `CheckAppointmentsAndCreateSessions` ON SCHEDULE EVERY 60 MINUTE STARTS '2025-05-06 17:00:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE current_appointment_id INT;
    DECLARE current_user_id INT;
    DECLARE current_consultant_id INT;
    DECLARE current_appointment_date DATE;
    DECLARE current_appointment_time TIME;
    DECLARE cur CURSOR FOR
        SELECT appointment_id, user_id, consultant_id, appointment_date, appointment_time
        FROM Appointments
        WHERE status = 'booked'
          AND CONCAT(appointment_date, ' ', appointment_time) <= (NOW() + INTERVAL 61 MINUTE)
          AND appointment_date = CURDATE();
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO current_appointment_id, current_user_id, current_consultant_id, current_appointment_date, current_appointment_time;
        IF done THEN
            LEAVE read_loop;
        END IF;

        INSERT INTO ConsultationSessions (
            appointment_id,
            user_id,
            consultant_id,
            start_time,
            session_status,
            end_time
        ) VALUES (
            current_appointment_id,
            current_user_id,
            current_consultant_id,
            CONCAT(current_appointment_date, ' ', current_appointment_time),
            'ready',
            DATE_ADD(CONCAT(current_appointment_date, ' ', current_appointment_time), INTERVAL 1 HOUR)
        );
    END LOOP;

    CLOSE cur;
END */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `UpdateAppointmentsFromSessions` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`%`*/ /*!50106 EVENT `UpdateAppointmentsFromSessions` ON SCHEDULE EVERY 5 MINUTE STARTS '2025-04-03 09:30:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_session_id INT;
    DECLARE v_appointment_id INT;
    DECLARE v_start_time DATETIME;
    DECLARE v_end_time DATETIME;
    DECLARE v_user_id INT;
    DECLARE v_consultant_id INT;
    DECLARE new_appointment_id INT;
    
    -- 定义游标，选取所有结束时间小于等于当前时间的会话记录
    DECLARE cur CURSOR FOR 
        SELECT session_id, appointment_id, start_time, end_time, user_id, consultant_id
        FROM ConsultationSessions
        WHERE end_time IS NOT NULL AND end_time <= NOW();
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    OPEN cur;
    
    read_loop: LOOP
        FETCH cur INTO v_session_id, v_appointment_id, v_start_time, v_end_time, v_user_id, v_consultant_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        IF v_appointment_id IS NOT NULL THEN
            -- 如果已有预约记录，则更新预约状态为 'completed'（仅当当前状态为 'booked' 时）
            UPDATE Appointments
            SET status = 'completed'
            WHERE appointment_id = v_appointment_id
              AND status = 'booked';
        ELSE
            -- 如果没有预约记录，则新建一条预约记录：
            -- appointment_date 取会话开始日期，appointment_time 取会话开始时间，
            -- booking_date 取会话结束时间，状态设为 'completed'
            INSERT INTO Appointments (user_id, consultant_id, appointment_date, appointment_time, booking_date, status)
            VALUES (
                v_user_id,
                v_consultant_id,
                DATE(v_start_time),
                TIME(v_start_time),
                v_end_time,
                'completed'
            );
            SET new_appointment_id = LAST_INSERT_ID();
            -- 可选：将新创建的 appointment_id 更新到对应的会话记录中，避免重复插入
            UPDATE ConsultationSessions
            SET appointment_id = new_appointment_id
            WHERE session_id = v_session_id;
        END IF;
    END LOOP;
    
    CLOSE cur;
END */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `UpdateConsultationSessionsStatus` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8mb4 */ ;;
/*!50003 SET character_set_results = utf8mb4 */ ;;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`%`*/ /*!50106 EVENT `UpdateConsultationSessionsStatus` ON SCHEDULE EVERY 5 MINUTE STARTS '2025-05-05 21:00:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
    -- 将 start_time + 30 分钟小于等于当前时间且状态为 'ready' 的记录改为 'completed'
    UPDATE ConsultationSessions
    SET session_status = 'completed'
    WHERE session_status = 'ready'
      AND TIMESTAMPADD(MINUTE, 30, start_time) <= NOW();

END */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'xinyanhui'
--
/*!50003 DROP PROCEDURE IF EXISTS `GenerateAppointments` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `GenerateAppointments`()
BEGIN
    DECLARE curr_date DATE DEFAULT '2025-04-15';
    DECLARE end_date DATE DEFAULT '2025-05-14';
    DECLARE time_slot TIME;

    DECLARE done_time INT DEFAULT 0;
    -- 创建游标用于时间段
    DECLARE time_cursor CURSOR FOR 
        SELECT '08:00:00' UNION SELECT '09:00:00' UNION SELECT '10:00:00' UNION SELECT '11:00:00'
        UNION SELECT '13:00:00' UNION SELECT '14:00:00' UNION SELECT '15:00:00' UNION SELECT '16:00:00';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_time = 1;



    WHILE curr_date <= end_date DO
        OPEN time_cursor;

        read_time: LOOP
            FETCH time_cursor INTO time_slot;
            IF done_time THEN 
                SET done_time = 0;
                LEAVE read_time;
            END IF;

            -- 新增预约（仅当该用户在该时间未有其他预约）
            INSERT INTO Appointments (user_id, consultant_id, appointment_date, appointment_time, status)
            SELECT u.user_id, cs.consultant_id, curr_date, time_slot, 'booked'
            FROM ConsultantSchedules cs
            JOIN (
                SELECT user_id FROM Users 
                WHERE NOT EXISTS (
                    SELECT 1 FROM Appointments a2 
                    WHERE a2.user_id = Users.user_id
                      AND a2.appointment_date = curr_date
                      AND a2.appointment_time = time_slot
                )
                ORDER BY RAND() LIMIT 1
            ) u
            WHERE cs.available_date = curr_date
              AND cs.status = 'normal'
              AND ((HOUR(time_slot) BETWEEN 8 AND 11 AND cs.start_time = 8)
                   OR (HOUR(time_slot) BETWEEN 13 AND 16 AND cs.start_time = 13))
              AND (
                  SELECT COUNT(*) 
                  FROM Appointments a 
                  WHERE a.consultant_id = cs.consultant_id 
                    AND a.appointment_date = curr_date 
                    AND a.appointment_time = time_slot
              ) < (
                  SELECT MAX(slot_capacity)
                  FROM ConsultantSchedules s 
                  WHERE s.consultant_id = cs.consultant_id 
                    AND s.available_date = curr_date 
                    AND s.start_time = cs.start_time
              )
            ORDER BY RAND()
            LIMIT 1;

        END LOOP;

        CLOSE time_cursor;

        -- 复约逻辑
        INSERT INTO Appointments (user_id, consultant_id, appointment_date, appointment_time, status)
        SELECT a.user_id, a.consultant_id, DATE_ADD(a.appointment_date, INTERVAL 7 DAY), a.appointment_time, 'booked'
        FROM Appointments a
        WHERE a.appointment_date = curr_date
          AND RAND() < 0.5
          AND NOT EXISTS (
              SELECT 1 FROM Appointments a2
              WHERE a2.user_id = a.user_id
                AND a2.appointment_date = DATE_ADD(a.appointment_date, INTERVAL 7 DAY)
                AND a2.appointment_time = a.appointment_time
          )
          AND EXISTS (
              SELECT 1 FROM ConsultantSchedules cs
              WHERE cs.consultant_id = a.consultant_id
                AND cs.available_date = DATE_ADD(a.appointment_date, INTERVAL 7 DAY)
                AND ((HOUR(a.appointment_time) BETWEEN 8 AND 11 AND cs.start_time = 8)
                     OR (HOUR(a.appointment_time) BETWEEN 13 AND 16 AND cs.start_time = 13))
                AND cs.status = 'normal'
                AND (
                    SELECT COUNT(*) 
                    FROM Appointments a2 
                    WHERE a2.consultant_id = cs.consultant_id 
                      AND a2.appointment_date = cs.available_date 
                      AND a2.appointment_time = a.appointment_time
                ) < (
                    SELECT MAX(slot_capacity)
                    FROM ConsultantSchedules s 
                    WHERE s.consultant_id = cs.consultant_id 
                      AND s.available_date = cs.available_date 
                      AND s.start_time = cs.start_time
                )
          );

        SET curr_date = DATE_ADD(curr_date, INTERVAL 1 DAY);
    END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GenerateConsultantSchedule` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `GenerateConsultantSchedule`()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE consultant_id INT;
    DECLARE appointment_date DATE;
    DECLARE appointment_time TIME;

    DECLARE cur CURSOR FOR
        SELECT a.consultant_id, a.appointment_date, a.appointment_time
        FROM Appointments a
        WHERE NOT EXISTS (
            SELECT 1 FROM ConsultantSchedules cs
            WHERE cs.consultant_id = a.consultant_id
            AND cs.available_date = a.appointment_date
            AND (
                (a.appointment_time BETWEEN '08:00:00' AND '12:00:00' AND cs.start_time = 8 AND cs.end_time = 12)
                OR (a.appointment_time BETWEEN '13:00:00' AND '17:00:00' AND cs.start_time = 13 AND cs.end_time = 17)
            )
        );

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO consultant_id, appointment_date, appointment_time;
        IF done THEN
            LEAVE read_loop;
        END IF;

        IF appointment_time BETWEEN '08:00:00' AND '12:00:00' THEN
            INSERT INTO ConsultantSchedules (consultant_id, available_date, start_time, end_time)
            VALUES (consultant_id, appointment_date, 8, 12)
            ON DUPLICATE KEY UPDATE status = 'normal';
        ELSEIF appointment_time BETWEEN '13:00:00' AND '17:00:00' THEN
            INSERT INTO ConsultantSchedules (consultant_id, available_date, start_time, end_time)
            VALUES (consultant_id, appointment_date, 13, 17)
            ON DUPLICATE KEY UPDATE status = 'normal';
        END IF;
    END LOOP;

    CLOSE cur;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GenerateConsultantSchedules` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `GenerateConsultantSchedules`()
BEGIN
    -- Step 1: Generate supervisor scheduling patterns: Each supervisor randomly generates 2-3 weekdays (1-7) as "duty days"
    DECLARE done_consultant INT DEFAULT 0;
    DECLARE v_consultant_id INT;
    DECLARE v_supervisor_id INT;
    DECLARE v_pattern_days VARCHAR(50);
    DECLARE day1 INT;
    DECLARE day2 INT;
    DECLARE day3 INT;
    DECLARE pos INT DEFAULT 1;
    DECLARE curr_date DATE;
    DECLARE last_date DATE;
    DECLARE week_day INT;
    DECLARE cnt_morning INT;
    DECLARE cnt_afternoon INT;

    DECLARE cur_consultant CURSOR FOR 
          SELECT consultant_id, supervisor_id FROM Consultants;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_consultant = 1;

    -- Step 2: Generate supervisor scheduling patterns
    DROP TEMPORARY TABLE IF EXISTS temp_supervisor_patterns;
    CREATE TEMPORARY TABLE temp_supervisor_patterns (
        supervisor_id INT PRIMARY KEY,
        pattern_days VARCHAR(50)
    );

    INSERT INTO temp_supervisor_patterns(supervisor_id, pattern_days)
    SELECT supervisor_id,
        CASE 
            WHEN RAND() < 0.7 THEN CONCAT(
                    FLOOR(1 + RAND()*7), ',', FLOOR(1 + RAND()*7)
                )
            ELSE CONCAT(
                    FLOOR(1 + RAND()*7), ',', FLOOR(1 + RAND()*7), ',', FLOOR(1 + RAND()*7)
                )
        END AS pattern_days
    FROM (
        SELECT DISTINCT supervisor_id FROM Consultants
        WHERE supervisor_id IS NOT NULL
    ) t;

    DROP TEMPORARY TABLE IF EXISTS temp_consultant_patterns;
    CREATE TEMPORARY TABLE temp_consultant_patterns (
        consultant_id INT,
        duty_day INT,
        segment_count INT
    );

    OPEN cur_consultant;
    read_consultant: LOOP
        FETCH cur_consultant INTO v_consultant_id, v_supervisor_id;
        IF done_consultant THEN 
            LEAVE read_consultant; 
        END IF;

        SELECT pattern_days INTO v_pattern_days
        FROM temp_supervisor_patterns
        WHERE supervisor_id = v_supervisor_id;

        SET day1 = CAST(SUBSTRING_INDEX(v_pattern_days, ',', 1) AS UNSIGNED);
        SET day2 = CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(v_pattern_days, ',', 2), ',', -1) AS UNSIGNED);
        IF v_pattern_days LIKE '%,%' AND v_pattern_days LIKE '%,%,%' THEN
            SET day3 = CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(v_pattern_days, ',', 3), ',', -1) AS UNSIGNED);
        ELSE
            SET day3 = NULL;
        END IF;

        IF day3 IS NOT NULL THEN
            IF RAND() < 0.5 THEN
                IF RAND() < 0.5 THEN
                    INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day1, 2);
                    INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day2, 1);
                ELSE
                    INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day1, 1);
                    INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day2, 2);
                END IF;
            ELSE
                INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day1, 2);
                INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day2, 2);
            END IF;
        ELSE
            IF RAND() < 0.5 THEN
                IF RAND() < 0.5 THEN
                    INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day1, 2);
                    INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day2, 1);
                ELSE
                    INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day1, 1);
                    INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day2, 2);
                END IF;
            ELSE
                INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day1, 2);
                INSERT INTO temp_consultant_patterns VALUES (v_consultant_id, day2, 2);
            END IF;
        END IF;
    END LOOP;
    CLOSE cur_consultant;

    -- Step 3: Iterate over the date range to generate specific scheduling records
    SET curr_date = '2025-04-14';
    SET last_date = '2025-05-19';

    WHILE curr_date <= last_date DO
        SET week_day = WEEKDAY(curr_date) + 1;

        INSERT INTO ConsultantSchedules (consultant_id, available_date, start_time, end_time, status)
        SELECT cp.consultant_id,
               curr_date,
               CASE 
                  WHEN cp.segment_count = 2 THEN 8
                  ELSE CASE WHEN RAND() < 0.5 THEN 8 ELSE 13 END
               END AS start_time,
               CASE 
                  WHEN cp.segment_count = 2 THEN 12
                  ELSE CASE WHEN RAND() < 0.5 THEN 12 ELSE 17 END
               END AS end_time,
               'normal'
        FROM temp_consultant_patterns cp
        WHERE cp.duty_day = week_day;

        INSERT INTO ConsultantSchedules (consultant_id, available_date, start_time, end_time, status)
        SELECT cp.consultant_id,
               curr_date,
               13,
               17,
               'normal'
        FROM temp_consultant_patterns cp
        WHERE cp.duty_day = week_day
          AND cp.segment_count = 2;

        SET curr_date = DATE_ADD(curr_date, INTERVAL 1 DAY);
    END WHILE;

    -- Step 4: Check for gaps
    SET curr_date = '2025-04-14';

    WHILE curr_date <= last_date DO
        SELECT COUNT(*) INTO cnt_morning 
          FROM ConsultantSchedules
          WHERE available_date = curr_date 
            AND start_time = 8 AND end_time = 12;
        SELECT COUNT(*) INTO cnt_afternoon 
          FROM ConsultantSchedules
          WHERE available_date = curr_date 
            AND start_time = 13 AND end_time = 17;

        IF cnt_morning = 0 THEN
            INSERT INTO ConsultantSchedules (consultant_id, available_date, start_time, end_time, status, note)
            SELECT consultant_id, curr_date, 8, 12, 'normal', '系统补缺'
            FROM Consultants
            ORDER BY RAND() LIMIT 1;
        END IF;
        IF cnt_afternoon = 0 THEN
            INSERT INTO ConsultantSchedules (consultant_id, available_date, start_time, end_time, status, note)
            SELECT consultant_id, curr_date, 13, 17, 'normal', '系统补缺'
            FROM Consultants
            ORDER BY RAND() LIMIT 1;
        END IF;

        SET curr_date = DATE_ADD(curr_date, INTERVAL 1 DAY);
    END WHILE;

    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GenerateRandomLeaveRecords` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `GenerateRandomLeaveRecords`()
BEGIN
    DECLARE random_consultant_id INT;
    DECLARE random_available_date DATE;
    DECLARE random_start_time INT;
    DECLARE random_end_time INT;
    DECLARE random_note VARCHAR(255);
    DECLARE leave_count INT DEFAULT 0;
    
    -- 确保不超过 3 条记录
    WHILE leave_count < 3 DO
        -- 随机选择一个 Consultant
        SET random_consultant_id = (SELECT consultant_id FROM Consultants ORDER BY RAND() LIMIT 1);
        
        -- 随机生成日期（在 3.30-4.10 之间）
        SET random_available_date = DATE_ADD('2025-03-30', INTERVAL FLOOR(RAND() * 12) DAY);
        
        -- 随机生成时间段（8-12 或 13-17）
        IF RAND() < 0.5 THEN
            SET random_start_time = 8;
            SET random_end_time = 12;
        ELSE
            SET random_start_time = 13;
            SET random_end_time = 17;
        END IF;
        
        -- 随机生成请假理由
        SET random_note = CONCAT('Leave for reason #', FLOOR(RAND() * 1000));

        -- 检查是否有重复记录
        IF NOT EXISTS (
            SELECT 1 
            FROM ConsultantSchedules
            WHERE consultant_id = random_consultant_id
              AND available_date = random_available_date
              AND start_time = random_start_time
        ) THEN
            -- 插入新记录
            INSERT INTO ConsultantSchedules (
                consultant_id, available_date, start_time, end_time, status, note
            ) VALUES (
                random_consultant_id, random_available_date, random_start_time, random_end_time, 'leave', random_note
            );
            SET leave_count = leave_count + 1;
        END IF;
    END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `AppointmentsWithNames`
--

/*!50001 DROP VIEW IF EXISTS `AppointmentsWithNames`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `AppointmentsWithNames` AS select `a`.`appointment_id` AS `appointment_id`,`a`.`user_id` AS `user_id`,`a`.`consultant_id` AS `consultant_id`,`a`.`appointment_date` AS `appointment_date`,`a`.`appointment_time` AS `appointment_time`,`a`.`booking_date` AS `booking_date`,`a`.`status` AS `status`,`a`.`cancellation_time` AS `cancellation_time`,`a`.`cancellation_reason` AS `cancellation_reason`,`c`.`name` AS `consultant_name`,`u`.`username` AS `user_username` from ((`Appointments` `a` join `Consultants` `c` on((`a`.`consultant_id` = `c`.`consultant_id`))) join `Users` `u` on((`a`.`user_id` = `u`.`user_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ConsultantDailyAvailability`
--

/*!50001 DROP VIEW IF EXISTS `ConsultantDailyAvailability`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `ConsultantDailyAvailability` AS with recursive `DateRange` as (select (curdate() + interval 1 day) AS `available_date` union all select (`DateRange`.`available_date` + interval 1 day) AS `available_date + INTERVAL 1 DAY
    ` from `DateRange` where (`DateRange`.`available_date` < (curdate() + interval 6 day))), `Hours` as (select 8 AS `hour` union all select 9 AS `9` union all select 10 AS `10` union all select 11 AS `11` union all select 12 AS `12` union all select 13 AS `13` union all select 14 AS `14` union all select 15 AS `15` union all select 16 AS `16` union all select 17 AS `17`), `ConsultantHours` as (select `c`.`consultant_id` AS `consultant_id`,`dr`.`available_date` AS `available_date`,`h`.`hour` AS `hour`,`cs`.`status` AS `status`,`cs`.`start_time` AS `start_time`,`cs`.`end_time` AS `end_time`,`cs`.`slot_capacity` AS `slot_capacity` from (((`Consultants` `c` join `DateRange` `dr`) join `Hours` `h`) join `ConsultantSchedules` `cs` on(((`c`.`consultant_id` = `cs`.`consultant_id`) and (`cs`.`available_date` = `dr`.`available_date`) and (`cs`.`start_time` <= `h`.`hour`) and (`h`.`hour` < `cs`.`end_time`) and (`cs`.`status` = 'normal'))))), `AppointmentsCount` as (select `Appointments`.`consultant_id` AS `consultant_id`,`Appointments`.`appointment_date` AS `appointment_date`,hour(`Appointments`.`appointment_time`) AS `hour`,count(0) AS `appointment_count` from `Appointments` where ((`Appointments`.`appointment_date` between (curdate() + interval 1 day) and (curdate() + interval 6 day)) and (hour(`Appointments`.`appointment_time`) between 8 and 17) and (`Appointments`.`status` = 'booked')) group by `Appointments`.`consultant_id`,`Appointments`.`appointment_date`,hour(`Appointments`.`appointment_time`)), `HourlyAvailability` as (select `ch`.`consultant_id` AS `consultant_id`,`ch`.`available_date` AS `available_date`,`ch`.`hour` AS `hour`,(case when ((`ch`.`status` = 'normal') and (`ch`.`start_time` <= `ch`.`hour`) and (`ch`.`hour` < `ch`.`end_time`) and ((`ac`.`appointment_count` is null) or (`ac`.`appointment_count` < `ch`.`slot_capacity`))) then 1 else 0 end) AS `available` from (`ConsultantHours` `ch` left join `AppointmentsCount` `ac` on(((`ch`.`consultant_id` = `ac`.`consultant_id`) and (`ch`.`available_date` = `ac`.`appointment_date`) and (`ch`.`hour` = `ac`.`hour`))))) select `HourlyAvailability`.`consultant_id` AS `consultant_id`,`HourlyAvailability`.`available_date` AS `available_date`,max((case when (`HourlyAvailability`.`hour` = 8) then `HourlyAvailability`.`available` else 0 end)) AS `hour_8_available`,max((case when (`HourlyAvailability`.`hour` = 9) then `HourlyAvailability`.`available` else 0 end)) AS `hour_9_available`,max((case when (`HourlyAvailability`.`hour` = 10) then `HourlyAvailability`.`available` else 0 end)) AS `hour_10_available`,max((case when (`HourlyAvailability`.`hour` = 11) then `HourlyAvailability`.`available` else 0 end)) AS `hour_11_available`,max((case when (`HourlyAvailability`.`hour` = 12) then `HourlyAvailability`.`available` else 0 end)) AS `hour_12_available`,max((case when (`HourlyAvailability`.`hour` = 13) then `HourlyAvailability`.`available` else 0 end)) AS `hour_13_available`,max((case when (`HourlyAvailability`.`hour` = 14) then `HourlyAvailability`.`available` else 0 end)) AS `hour_14_available`,max((case when (`HourlyAvailability`.`hour` = 15) then `HourlyAvailability`.`available` else 0 end)) AS `hour_15_available`,max((case when (`HourlyAvailability`.`hour` = 16) then `HourlyAvailability`.`available` else 0 end)) AS `hour_16_available`,max((case when (`HourlyAvailability`.`hour` = 17) then `HourlyAvailability`.`available` else 0 end)) AS `hour_17_available` from `HourlyAvailability` group by `HourlyAvailability`.`consultant_id`,`HourlyAvailability`.`available_date` order by `HourlyAvailability`.`consultant_id`,`HourlyAvailability`.`available_date` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ConsultationSessionsWithNames`
--

/*!50001 DROP VIEW IF EXISTS `ConsultationSessionsWithNames`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `ConsultationSessionsWithNames` AS select `cs`.`session_id` AS `session_id`,`cs`.`appointment_id` AS `appointment_id`,`cs`.`user_id` AS `user_id`,`cs`.`consultant_id` AS `consultant_id`,`cs`.`start_time` AS `start_time`,`cs`.`session_status` AS `session_status`,`cs`.`end_time` AS `end_time`,`cs`.`rating` AS `rating`,`cs`.`feedback` AS `feedback`,`c`.`name` AS `consultant_name`,`u`.`username` AS `user_username` from ((`ConsultationSessions` `cs` join `Consultants` `c` on((`cs`.`consultant_id` = `c`.`consultant_id`))) join `Users` `u` on((`cs`.`user_id` = `u`.`user_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `CurrentAvailability`
--

/*!50001 DROP VIEW IF EXISTS `CurrentAvailability`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `CurrentAvailability` AS select `cs`.`consultant_id` AS `consultant_id`,coalesce(sum((case when (`css`.`session_status` = 'active') then 1 else 0 end)),0) AS `active_sessions`,coalesce(sum((case when (`css`.`session_status` = 'ready') then 1 else 0 end)),0) AS `ready_sessions`,(case when ((coalesce(sum((case when (`css`.`session_status` = 'active') then 1 else 0 end)),0) < `cs`.`slot_capacity`) and ((timestampdiff(MINUTE,now(),((now() + interval 1 hour) - interval minute(now()) minute)) >= 30) or (coalesce(sum((case when (`css`.`session_status` = 'ready') then 1 else 0 end)),0) < `cs`.`slot_capacity`))) then 1 else 0 end) AS `is_available` from (`ConsultantSchedules` `cs` left join `ConsultationSessions` `css` on(((`cs`.`consultant_id` = `css`.`consultant_id`) and (cast(`css`.`start_time` as date) = `cs`.`available_date`) and (hour(`css`.`start_time`) between `cs`.`start_time` and (`cs`.`end_time` - 1))))) where ((`cs`.`available_date` = curdate()) and (`cs`.`start_time` <= hour(now())) and (hour(now()) < `cs`.`end_time`) and (`cs`.`status` = 'normal')) group by `cs`.`consultant_id`,`cs`.`slot_capacity` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `DataBrd_MonthlyTimeRank`
--

/*!50001 DROP VIEW IF EXISTS `DataBrd_MonthlyTimeRank`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `DataBrd_MonthlyTimeRank` AS select `c`.`consultant_id` AS `consultant_id`,`c`.`supervisor_id` AS `supervisor_id`,date_format(`cs`.`start_time`,'%Y-%m') AS `ym`,count(0) AS `session_count`,sum(timestampdiff(MINUTE,`cs`.`start_time`,`cs`.`end_time`)) AS `total_minutes`,rank() OVER (PARTITION BY date_format(`cs`.`start_time`,'%Y-%m') ORDER BY sum(timestampdiff(MINUTE,`cs`.`start_time`,`cs`.`end_time`)) desc )  AS `global_rank`,rank() OVER (PARTITION BY date_format(`cs`.`start_time`,'%Y-%m'),`c`.`supervisor_id` ORDER BY sum(timestampdiff(MINUTE,`cs`.`start_time`,`cs`.`end_time`)) desc )  AS `group_rank` from (`ConsultationSessions` `cs` join `Consultants` `c` on((`cs`.`consultant_id` = `c`.`consultant_id`))) where (`cs`.`session_status` = 'completed') group by `c`.`consultant_id`,`c`.`supervisor_id`,`ym` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `DataBrd_WeeklyConsultationStats`
--

/*!50001 DROP VIEW IF EXISTS `DataBrd_WeeklyConsultationStats`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `DataBrd_WeeklyConsultationStats` AS select yearweek(`ConsultationSessions`.`start_time`,1) AS `year_week`,min(cast(`ConsultationSessions`.`start_time` as date)) AS `week_start_date`,max(cast(`ConsultationSessions`.`start_time` as date)) AS `week_end_date`,count(0) AS `total_sessions`,sum(timestampdiff(MINUTE,`ConsultationSessions`.`start_time`,`ConsultationSessions`.`end_time`)) AS `total_duration_minutes` from `ConsultationSessions` where (`ConsultationSessions`.`session_status` = 'completed') group by yearweek(`ConsultationSessions`.`start_time`,1) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `SupervisorConsultationsWithNames`
--

/*!50001 DROP VIEW IF EXISTS `SupervisorConsultationsWithNames`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `SupervisorConsultationsWithNames` AS select `sc`.`record_id` AS `record_id`,`sc`.`consultant_id` AS `consultant_id`,`sc`.`supervisor_id` AS `supervisor_id`,`sc`.`session_id` AS `session_id`,`sc`.`request_time` AS `request_time`,`sc`.`response_time` AS `response_time`,`c`.`name` AS `consultant_name`,`s`.`name` AS `supervisor_name` from ((`SupervisorConsultations` `sc` join `Consultants` `c` on((`sc`.`consultant_id` = `c`.`consultant_id`))) join `Supervisors` `s` on((`sc`.`supervisor_id` = `s`.`supervisor_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `View_ConsultantDailyAppointments`
--

/*!50001 DROP VIEW IF EXISTS `View_ConsultantDailyAppointments`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `View_ConsultantDailyAppointments` AS select `Appointments`.`appointment_date` AS `appointment_date`,`Appointments`.`consultant_id` AS `consultant_id`,count(0) AS `total_appointments` from `Appointments` group by `Appointments`.`appointment_date`,`Appointments`.`consultant_id` order by `Appointments`.`appointment_date`,`Appointments`.`consultant_id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `View_ConsultantHourlyAppointments`
--

/*!50001 DROP VIEW IF EXISTS `View_ConsultantHourlyAppointments`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `View_ConsultantHourlyAppointments` AS select `cs`.`schedule_id` AS `schedule_id`,`cs`.`consultant_id` AS `consultant_id`,`cs`.`available_date` AS `available_date`,`cs`.`start_time` AS `start_time`,`cs`.`end_time` AS `end_time`,`appt_slots`.`appointment_time` AS `appointment_time`,count(`a`.`appointment_id`) AS `appointment_count` from ((`ConsultantSchedules` `cs` join (select '08:00:00' AS `appointment_time` union select '09:00:00' AS `09:00:00` union select '10:00:00' AS `10:00:00` union select '11:00:00' AS `11:00:00` union select '13:00:00' AS `13:00:00` union select '14:00:00' AS `14:00:00` union select '15:00:00' AS `15:00:00` union select '16:00:00' AS `16:00:00`) `appt_slots`) left join `Appointments` `a` on(((`a`.`consultant_id` = `cs`.`consultant_id`) and (`a`.`appointment_date` = `cs`.`available_date`) and (`a`.`appointment_time` = `appt_slots`.`appointment_time`)))) where ((`cs`.`status` = 'normal') and (((`cs`.`start_time` = 8) and (hour(`appt_slots`.`appointment_time`) between 8 and 11)) or ((`cs`.`start_time` = 13) and (hour(`appt_slots`.`appointment_time`) between 13 and 16)))) group by `cs`.`schedule_id`,`cs`.`consultant_id`,`cs`.`available_date`,`cs`.`start_time`,`cs`.`end_time`,`appt_slots`.`appointment_time` order by `cs`.`available_date`,`cs`.`consultant_id`,`appt_slots`.`appointment_time` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `View_DailyAppointments`
--

/*!50001 DROP VIEW IF EXISTS `View_DailyAppointments`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `View_DailyAppointments` AS select `Appointments`.`appointment_date` AS `appointment_date`,count(0) AS `total_appointments` from `Appointments` group by `Appointments`.`appointment_date` order by `Appointments`.`appointment_date` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `View_HourlyAppointments`
--

/*!50001 DROP VIEW IF EXISTS `View_HourlyAppointments`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `View_HourlyAppointments` AS select `Appointments`.`appointment_date` AS `appointment_date`,hour(`Appointments`.`appointment_time`) AS `hour_slot`,count(0) AS `appointments_per_hour` from `Appointments` group by `Appointments`.`appointment_date`,`hour_slot` order by `Appointments`.`appointment_date`,`hour_slot` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-09 23:42:38
