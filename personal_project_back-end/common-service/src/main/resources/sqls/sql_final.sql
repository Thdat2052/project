CREATE DATABASE  IF NOT EXISTS `room-management-web` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `room-management-web`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: room-management-web
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `checkout_request`
--

DROP TABLE IF EXISTS `checkout_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkout_request` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ROOM_ID` int NOT NULL,
  `USER_ID` int NOT NULL,
  `REQUEST_DATE` date NOT NULL,
  `STATUS` varchar(50) NOT NULL,
  `REASON` text,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkout_request`
--

LOCK TABLES `checkout_request` WRITE;
/*!40000 ALTER TABLE `checkout_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkout_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contract` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ROOM_ID` int NOT NULL,
  `USER_ID` int NOT NULL,
  `START_DATE` timestamp NULL DEFAULT NULL,
  `END_DATE` timestamp NULL DEFAULT NULL,
  `DEPOSIT` decimal(10,2) NOT NULL,
  `MONTHLY_RENT` decimal(10,2) NOT NULL,
  `STATUS` varchar(50) NOT NULL,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TRANSACTION_ID` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES (1,2,1,'2025-04-11 22:48:40','2025-04-18 22:48:43',12.00,10000000.00,'ACTIVE','2025-04-11 22:49:08','2025-04-19 17:50:38',NULL),(9,2,100,'2025-04-02 08:16:42','2025-10-02 05:00:03',5000.00,2000.00,'ACTIVE','2025-04-19 07:46:03','2025-04-19 07:46:03','515b92c2-abdf-452a-bcbc-4d1567bb5825');
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NOTE` text,
  `TOTAL_PRICE` decimal(10,2) NOT NULL,
  `DUE_DATE` timestamp NULL DEFAULT NULL,
  `PAYMENT_DATE` timestamp NULL DEFAULT NULL,
  `STATUS` varchar(50) NOT NULL,
  `PAYMENT_ID` int DEFAULT NULL,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CONTRACT_ID` int DEFAULT NULL,
  `YEAR_CALCULATE` int DEFAULT NULL,
  `MONTH_CALCULATE` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (1,NULL,10159123.00,NULL,'2025-04-17 17:03:05','PAID',1,'2025-04-17 17:03:05','2025-04-21 17:13:11',1,2025,4),(5,NULL,11423777.00,NULL,'2025-04-17 17:03:05','PAID',NULL,'2025-04-19 17:41:40','2025-04-20 02:50:45',1,2024,4),(6,NULL,125123.00,NULL,'2025-04-17 17:03:05','PAID',NULL,'2025-04-19 17:41:40','2025-04-20 03:10:27',9,2024,4),(7,NULL,152000.00,NULL,'2025-04-17 17:03:05','PAID',NULL,'2025-04-19 17:54:22','2025-04-21 17:13:11',9,2025,4);
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintenance_fee`
--

DROP TABLE IF EXISTS `maintenance_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintenance_fee` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `MAINTENANCE_REQUEST_ID` int NOT NULL,
  `PRICE` decimal(10,2) NOT NULL,
  `SERVICE_ROOM_ID` int NOT NULL,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance_fee`
--

LOCK TABLES `maintenance_fee` WRITE;
/*!40000 ALTER TABLE `maintenance_fee` DISABLE KEYS */;
INSERT INTO `maintenance_fee` VALUES (1,1,123123.00,1,'2025-04-17 18:21:59','2025-04-17 18:21:59');
/*!40000 ALTER TABLE `maintenance_fee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintenance_request`
--

DROP TABLE IF EXISTS `maintenance_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintenance_request` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `STATUS` varchar(50) NOT NULL,
  `REQUEST_DATE` date NOT NULL,
  `DECISION` text,
  `TOTAL_FEE` decimal(10,2) DEFAULT NULL,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CONTRACT_ID` int DEFAULT NULL,
  `REQUEST_DONE_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance_request`
--

LOCK TABLES `maintenance_request` WRITE;
/*!40000 ALTER TABLE `maintenance_request` DISABLE KEYS */;
INSERT INTO `maintenance_request` VALUES (1,'COMPLETED','2025-04-13','không dùng đc',100000.00,NULL,'2025-04-19 16:36:05',1,'2025-04-15 16:34:23'),(2,'IN_PROGRESS','2025-04-13','bao',NULL,NULL,'2025-04-17 18:14:34',1,'2025-04-15 16:34:23'),(3,'IN_PROGRESS','2025-04-13','s',NULL,NULL,'2025-04-17 18:14:34',1,'2025-04-15 16:34:23'),(4,'IN_PROGRESS','2025-04-15','dsas',NULL,'2025-04-15 16:34:23','2025-04-17 18:14:34',1,'2025-04-15 16:34:23');
/*!40000 ALTER TABLE `maintenance_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `METHOD` varchar(50) NOT NULL,
  `STATUS` varchar(50) NOT NULL,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,'VNPAY','ACTIVE','2025-04-17 17:02:34','2025-04-17 17:02:34');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN','2025-04-19 07:06:01','2025-04-27 16:00:56');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NUMBER` varchar(50) NOT NULL,
  `LENGTH` decimal(5,2) DEFAULT NULL,
  `WIDTH` decimal(5,2) DEFAULT NULL,
  `STATUS` varchar(50) NOT NULL,
  `NOTE` text,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `PRICE` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (2,'2',33.00,2.00,'RENTED','33','2025-03-31 16:44:44','2025-04-20 04:02:08','N01',NULL),(5,'34',999.00,999.00,'AVAILABLE','đã ghi chú ','2025-04-02 15:02:30','2025-04-30 00:57:24',NULL,NULL),(7,'12',2.00,2.00,'RENTED','2','2025-04-02 15:19:08','2025-04-20 04:02:08','N5',NULL),(9,'133',23.00,33.00,'AVAILABLE','1','2025-04-02 15:34:44','2025-04-20 04:02:08','N6',NULL),(10,'0',123.00,123.00,'AVAILABLE','123','2025-04-30 01:25:27','2025-04-30 01:25:27',NULL,NULL),(11,'0',12.00,21.00,'AVAILABLE','đ','2025-04-30 01:27:22','2025-04-30 01:27:22',NULL,NULL);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_member`
--

DROP TABLE IF EXISTS `room_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_member` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CONTRACT_ID` int DEFAULT NULL,
  `USER_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_member`
--

LOCK TABLES `room_member` WRITE;
/*!40000 ALTER TABLE `room_member` DISABLE KEYS */;
INSERT INTO `room_member` VALUES (31,'2025-04-13 07:45:10','2025-04-19 07:40:47',1,1),(32,'2025-04-13 07:45:10','2025-04-19 07:40:47',1,2),(33,'2025-04-13 07:45:10','2025-04-19 07:40:47',1,1),(34,'2025-04-13 07:45:10','2025-04-19 07:40:47',1,1),(35,'2025-04-13 07:45:10','2025-04-19 07:40:47',1,1),(36,'2025-04-13 07:45:10','2025-04-19 07:40:47',1,1),(37,'2025-04-13 07:45:10','2025-04-19 07:40:47',1,1),(38,'2025-04-13 07:45:10','2025-04-19 07:40:47',1,1),(39,'2025-04-13 07:45:10','2025-04-19 07:40:47',1,1),(40,'2025-04-13 07:45:10','2025-04-19 07:40:47',1,1),(41,'2025-04-13 07:45:19','2025-04-19 07:40:47',1,1),(42,'2025-04-13 07:45:19','2025-04-19 07:40:47',1,1),(43,'2025-04-13 07:45:19','2025-04-19 07:40:47',1,1),(60,'2025-04-15 16:34:06','2025-04-19 07:40:47',1,1),(61,'2025-04-15 16:34:06','2025-04-19 07:40:47',1,1),(62,'2025-04-15 16:34:06','2025-04-19 07:40:47',1,1),(63,'2025-04-19 07:06:20','2025-04-19 07:40:47',1,1),(64,'2025-04-19 07:06:20','2025-04-19 07:40:47',1,1),(77,'2025-04-19 07:46:03','2025-04-19 07:46:03',9,99),(78,'2025-04-19 07:46:03','2025-04-19 07:46:03',9,100);
/*!40000 ALTER TABLE `room_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) NOT NULL,
  `IS_ACTIVE` tinyint(1) DEFAULT '1',
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `SERVICE_TYPE` varchar(40) DEFAULT NULL,
  `PRICE` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'Water Heater (Bình nước nóng)',1,'2025-04-12 04:52:01','2025-04-19 07:47:18','OTHER',123123.00),(2,'Console (Bảng điều khiển)',1,'2025-04-12 04:52:01','2025-04-19 07:46:57','OTHER',34324.00),(3,'Wifi',1,'2025-04-12 04:52:01','2025-04-19 07:46:57','OTHER',1237867.00),(4,'Air Conditioner (Máy lạnh)',1,'2025-04-12 04:52:01','2025-04-19 07:46:57','OTHER',4545.00),(5,'Refrigerator (Tủ lạnh)',1,'2025-04-12 04:52:01','2025-04-19 07:46:57','OTHER',4545.00),(6,'Washing Machine (Máy giặt)',1,'2025-04-12 04:52:01','2025-04-19 07:46:57','OTHER',4545.00),(7,'Dryer(Máy sấy)',1,'2025-04-12 04:52:01','2025-04-19 07:46:57','OTHER',2414.00),(8,'Dishwasher(Máy rửa chén)',1,'2025-04-12 04:52:02','2025-04-19 07:46:57','OTHER',12414.00);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_room`
--

DROP TABLE IF EXISTS `service_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_room` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `SERVICE_ID` int NOT NULL,
  `QUANTITY` int NOT NULL,
  `PRICE` decimal(10,2) NOT NULL,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` varchar(40) DEFAULT NULL,
  `CONTRACT_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_room`
--

LOCK TABLES `service_room` WRITE;
/*!40000 ALTER TABLE `service_room` DISABLE KEYS */;
INSERT INTO `service_room` VALUES (1,1,1,1000.00,'2023-10-01 00:00:00','2025-04-13 08:47:49','ERROR',1),(2,2,1,2000.00,'2023-10-01 00:00:00','2025-04-13 08:47:49','ERROR',1),(3,3,1,3000.00,'2023-10-01 00:00:00','2025-04-13 08:47:49','ACTIVE',1),(4,4,1,4000.00,'2023-10-01 00:00:00','2025-04-13 08:47:49','ACTIVE',1),(5,5,1,5000.00,'2023-10-01 00:00:00','2025-04-13 08:47:49','ACTIVE',1),(6,6,1,6000.00,'2023-10-01 00:00:00','2025-04-15 16:34:23','ERROR',1),(7,7,1,7000.00,'2023-10-01 00:00:00','2025-04-13 08:47:49','ACTIVE',1),(8,8,1,8000.00,'2023-10-01 00:00:00','2025-04-13 08:47:49','ACTIVE',1),(9,1,1,150000.00,'2025-04-19 07:46:03','2025-04-19 07:46:03',NULL,9);
/*!40000 ALTER TABLE `service_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `FULLNAME` varchar(100) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `CCCD` varchar(20) NOT NULL,
  `PHONE_NUMBER` varchar(20) NOT NULL,
  `PERMANENT_ADDRESS` text,
  `DATE_OF_BIRTH` date DEFAULT NULL,
  `RECORDED_AT` date DEFAULT NULL,
  `LICENSE_PLATE_NUMBER` varchar(20) DEFAULT NULL,
  `NOTE` text,
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `TRANSACTION_ID` varchar(100) DEFAULT NULL,
  `STATUS` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Nguyễn Hoàng Bảo','bao.nh204515@sis.hust.edu.vn','fdsa','0389649693','Nguyễn Đức Cảnh',NULL,NULL,NULL,NULL,'baonh2','$2a$10$iO5tqoLqW34tQp22Xt7EdOIvs2Aho5fAmfGqn1tbQynu2z/wIYnki','2025-03-30 16:43:46','2025-04-15 17:52:04',1,NULL,'ACTIVE'),(6,'Hoàng Bảo','baodaoc1@gmail.com','Nguyễn Hoàng Bảo32','0564568767','Nguyễn Huệ',NULL,NULL,NULL,NULL,'123','$2a$10$iO5tqoLqW34tQp22Xt7EdOIvs2Aho5fAmfGqn1tbQynu2z','2025-03-31 14:33:04','2025-04-15 16:00:14',1,NULL,'ACTIVE'),(8,'Hoàng Bảo','baodaoc1@gmail.com','432432','0564568767','Nguyễn Huệ',NULL,NULL,NULL,NULL,'342','$2a$10$iO5tqoLqW34tQp22Xt7EdOIvs2Aho5fAmfGqn1tbQynu2z','2025-03-31 14:43:45','2025-04-15 16:00:14',1,NULL,'ACTIVE'),(9,'Hoàng Bảo','baodaoc1@gmail.com','xx','0564568767','Nguyễn Huệ',NULL,NULL,NULL,NULL,'x','$2a$10$iO5tqoLqW34tQp22Xt7EdOIvs2Aho5fAmfGqn1tbQynu2z','2025-03-31 14:57:06','2025-04-15 16:00:14',1,NULL,'ACTIVE'),(12,'Hoàng Bảo','baodaoc1@gmail.com','33','0564568767','Nguyễn Huệ',NULL,NULL,NULL,NULL,'123333','$2a$10$iO5tqoLqW34tQp22Xt7EdOIvs2Aho5fAmfGqn1tbQynu2z','2025-03-31 15:00:32','2025-04-15 16:00:14',1,NULL,'ACTIVE'),(61,'Trần Thị Thu','tranthithu@example.com','123456789061','0123456761','456 Elm St','1992-02-02','2023-10-01','61B-67890','Ghi chú 61','tranthithu','password61','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-61','ACTIVE'),(62,'Lê Văn Hùng','levanhung@example.com','123456789062','0123456762','789 Oak St','1985-03-03','2023-10-01','62C-34567','Ghi chú 62','levanhung','password62','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-62','ACTIVE'),(63,'Phạm Thị Hồng','phamthihong@example.com','123456789063','0123456763','321 Pine St','1988-04-04','2023-10-01','63D-45678','Ghi chú 63','phamthihong','password63','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-63','ACTIVE'),(64,'Hoàng Văn Phong','hoangvanphong@example.com','123456789064','0123456764','654 Maple St','1995-05-05','2023-10-01','64E-56789','Ghi chú 64','hoangvanphong','password64','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-64','ACTIVE'),(65,'Nguyễn Thị Tuyết','nguyenthituyet@example.com','123456789065','0123456765','987 Cedar St','1993-06-06','2023-10-01','65F-67890','Ghi chú 65','nguyenthituyet','password65','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-65','ACTIVE'),(66,'Trần Văn Sơn','tranvanson@example.com','123456789066','0123456766','123 Birch St','1987-07-07','2023-10-01','66G-78901','Ghi chú 66','tranvanson','password66','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-66','ACTIVE'),(67,'Lê Thị Thanh','lethithanh@example.com','123456789067','0123456767','456 Walnut St','1991-08-08','2023-10-01','67H-89012','Ghi chú 67','lethithanh','password67','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-67','ACTIVE'),(68,'Phạm Văn Đức','phamvanduc@example.com','123456789068','0123456768','789 Chestnut St','1989-09-09','2023-10-01','68I-90123','Ghi chú 68','phamvanduc','password68','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-68','ACTIVE'),(69,'Hoàng Thị Yến','hoangthiyen@example.com','123456789069','0123456769','321 Spruce St','1994-10-10','2023-10-01','69J-01234','Ghi chú 69','hoangthiyen','password69','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-69','ACTIVE'),(70,'Nguyễn Văn Dũng','nguyenvandung@example.com','123456789070','0123456770','123 Main St','1990-01-01','2023-10-01','70A-12345','Ghi chú 70','nguyenvandung','password70','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-70','ACTIVE'),(71,'Nguyễn Văn An','nguyenvanan@example.com','123456789071','0123456771','456 Elm St','1992-02-02','2023-10-01','71B-23456','Ghi chú 71','nguyenvanan','password71','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-71','ACTIVE'),(72,'Trần Thị Bích','tranthibich@example.com','123456789072','0123456772','789 Oak St','1993-03-03','2023-10-01','72C-34567','Ghi chú 72','tranthibich','password72','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-72','ACTIVE'),(73,'Lê Văn Cường','levancuong@example.com','123456789073','0123456773','123 Pine St','1994-04-04','2023-10-01','73D-45678','Ghi chú 73','levancuong','password73','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-73','ACTIVE'),(74,'Phạm Thị Dung','phamthidung@example.com','123456789074','0123456774','456 Fir St','1995-05-05','2023-10-01','74E-56789','Ghi chú 74','phamthidung','password74','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-74','ACTIVE'),(75,'Nguyễn Văn Đạt','nguyenvandat@example.com','123456789075','0123456775','789 Willow St','1996-06-06','2023-10-01','75F-67890','Ghi chú 75','nguyenvandat','password75','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-75','ACTIVE'),(76,'Trần Thị Hạnh','tranthihanh@example.com','123456789076','0123456776','123 Poplar St','1997-07-07','2023-10-01','76G-78901','Ghi chú 76','tranthihanh','password76','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-76','ACTIVE'),(78,'Phạm Thị Lan','phamthilan@example.com','123456789078','0123456778','789 Redwood St','1999-09-09','2023-10-01','78I-90123','Ghi chú 78','phamthilan','password78','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-78','ACTIVE'),(79,'Nguyễn Văn Minh','nguyenvanminh@example.com','123456789079','0123456779','123 Sequoia St','2000-10-10','2023-10-01','79J-01234','Ghi chú 79','nguyenvanminh','password79','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-79','ACTIVE'),(80,'Trần Thị Ngọc','tranthingoc@example.com','123456789080','0123456780','456 Magnolia St','2001-11-11','2023-10-01','80K-12345','Ghi chú 80','tranthingoc','password80','2023-10-01 00:00:00','2023-10-01 00:00:00',1,'transaction-80','ACTIVE'),(81,'Hoang Bao','baonh2@gmail.com','123456789','0987654321',NULL,NULL,NULL,NULL,NULL,'baonh22','$2a$10$iO5tqoLqW34tQp22Xt7EdOIvs2Aho5fAmfGqn1tbQynu2z/wIYnki','2025-04-15 15:37:32','2025-04-15 16:02:16',1,NULL,NULL),(83,'Nguyen Van   b ','nguyenvana@example.com','123456789012','0909123456','123 Street, Hanoi',NULL,NULL,NULL,'Main tenant','nguyedbvbvf','$2a$10$cTPdImCvCbrV.gZd8nMuseNbuqmmsLXNLh.lolvPN8hoCZ3sIRywe',NULL,NULL,1,'794282eb-fb7f-4f22-914f-ff4f65b1bafb',NULL),(84,'Tran Thi Bdf','tranthib@example.com','987654321098','0918234567','456 Road, HCMC',NULL,NULL,NULL,'Co-tenant','trxcxcib','$2a$10$KPWLC6Z6uM54iUNBKisHVu5RrwanA5MRfm/uyGK5i8iB1RjjfkY8e',NULL,NULL,1,'794282eb-fb7f-4f22-914f-ff4f65b1bafb',NULL),(87,'Nguyen Van   b ','1234@example.com','123456789012','0909123456','123 Street, Hanoi',NULL,NULL,NULL,'Main tenant','142','$2a$10$FmUeA4Mj6PfNJ0KIzY1k5.iy8s3wd3z8SIPW6M1aZGscd7Oc78snO',NULL,NULL,1,'e9e56b5f-6d8c-416c-8a21-b98ab46626dc',NULL),(88,'Tran Thi Bdf','aaa@example.com','987654321098','0918234567','456 Road, HCMC',NULL,NULL,NULL,'Co-tenant','123123b','$2a$10$o36hfBDgcNQ8sozwXOdc5Oj/12VMZznAfloVuPh9gDp27WpnoKHXS',NULL,NULL,1,'e9e56b5f-6d8c-416c-8a21-b98ab46626dc',NULL),(89,'Nguyen Van   b ','1234@example.com','123456789012','0909123456','123 Street, Hanoi',NULL,NULL,NULL,'Main tenant','142d','$2a$10$LpLxa7MCQtzzfrz2yK5reuzjnxNiCIn7dsMr/8jg3avd3ncdygLi6',NULL,NULL,1,'68a597bb-b4bb-4b46-a092-ff943d9a254e',NULL),(90,'Tran Thi Bdf','aaa@example.com','987654321098','0918234567','456 Road, HCMC',NULL,NULL,NULL,'Co-tenant','123123db','$2a$10$7o6SchFa0STwEOiN4pkliunZxjT6F1fd6/Gqu6g5HzECnU1njMOtq',NULL,NULL,1,'68a597bb-b4bb-4b46-a092-ff943d9a254e',NULL),(99,'Nguyen Van   b ','1234@example.com','123456789012','0909123456','123 Street, Hanoi',NULL,NULL,NULL,'Main tenant','992d','$2a$10$gWWw3thrruWibTd/R8Cl6ubTIPvtJ3vWxZ7OUwuqbEik0Udtoo/J6',NULL,NULL,1,'515b92c2-abdf-452a-bcbc-4d1567bb5825',NULL),(100,'Tran Thi Bdf','aaa@example.com','987654321098','0918234567','456 Road, HCMC',NULL,NULL,NULL,'Co-tenant','1999fb','$2a$10$LCChEr1EZ9HknI9SZndb3uyc7VagpzWNPWgD4CBFspmOp8s9s7you',NULL,NULL,1,'515b92c2-abdf-452a-bcbc-4d1567bb5825',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `USER_ID` int NOT NULL,
  `ROLE_ID` int NOT NULL,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,1,'2025-04-19 07:06:19','2025-04-24 17:31:41'),(2,84,1,'2025-04-19 07:06:19','2025-04-19 07:06:19'),(3,87,1,'2025-04-19 07:23:07','2025-04-19 07:23:07'),(4,88,1,'2025-04-19 07:23:07','2025-04-19 07:23:07'),(5,89,1,'2025-04-19 07:24:54','2025-04-19 07:24:54'),(6,90,1,'2025-04-19 07:24:54','2025-04-19 07:24:54'),(15,99,1,'2025-04-19 07:46:03','2025-04-19 07:46:03'),(16,100,1,'2025-04-19 07:46:03','2025-04-19 07:46:03');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utility_index`
--

DROP TABLE IF EXISTS `utility_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utility_index` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ROOM_ID` int NOT NULL,
  `ELECTRIC_OLD_INDEX` int NOT NULL,
  `ELECTRIC_NEW_INDEX` int NOT NULL,
  `ELECTRIC_USAGE` int NOT NULL,
  `WATER_OLD_INDEX` int NOT NULL,
  `WATER_NEW_INDEX` int NOT NULL,
  `WATER_USAGE` int NOT NULL,
  `CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MONTH_MEASURE` int DEFAULT NULL,
  `YEAR_MEASURE` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utility_index`
--

LOCK TABLES `utility_index` WRITE;
/*!40000 ALTER TABLE `utility_index` DISABLE KEYS */;
INSERT INTO `utility_index` VALUES (1,2,100,150,50,50,70,20,'2025-04-13 11:25:21','2025-04-17 18:26:03',4,2024),(2,3,150,200,50,70,90,20,'2025-04-13 11:25:21','2025-04-20 03:57:29',5,2024),(3,3,200,250,50,90,110,20,'2025-04-13 11:25:21','2025-04-20 03:57:29',6,2024),(4,2,250,300,50,110,130,20,'2025-04-13 11:25:21','2025-04-20 03:57:29',7,2024),(5,2,300,350,50,130,150,20,'2025-04-13 11:25:21','2025-04-20 03:57:29',8,2024),(6,4,350,400,50,150,170,20,'2025-04-13 11:25:21','2025-04-20 03:57:29',9,2024),(7,4,400,450,50,170,190,20,'2025-04-13 11:25:21','2025-04-20 03:57:29',10,2024),(8,1,450,500,50,190,210,20,'2025-04-13 11:25:21','2025-04-13 11:25:21',11,2024),(9,1,500,550,50,210,230,20,'2025-04-13 11:25:21','2025-04-13 11:25:21',12,2024),(10,1,550,600,50,230,250,20,'2025-04-13 11:25:21','2025-04-13 11:25:21',1,2025),(11,1,600,650,50,250,270,20,'2025-04-13 11:25:21','2025-04-13 11:25:21',2,2025),(12,1,650,700,50,270,290,20,'2025-04-13 11:25:21','2025-04-13 11:25:21',3,2025),(13,1,700,750,50,290,310,20,'2025-04-13 11:25:21','2025-04-13 11:25:21',4,2025);
/*!40000 ALTER TABLE `utility_index` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-30 10:57:13
