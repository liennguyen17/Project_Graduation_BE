-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: db_graduation
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Mã nhật ký/đánh giá',
  `message` varchar(255) DEFAULT NULL COMMENT 'ghi chú',
  `create_by` text COMMENT 'Người tạo',
  `file` text COMMENT 'File đính kèm',
  `create_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày tạo',
  `Topic_id` int DEFAULT NULL COMMENT 'Mã đăng ký',
  PRIMARY KEY (`id`),
  KEY `Topic_id` (`Topic_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`Topic_id`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Bảng quản lý nhật ký/đánh giá với giáo viên và quản lý các file sinh viên đẩy lên';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'Gửi lại cho thầy bản nộp báo cáo về khoa, và giấy xác nhận cơ sở thực tập của e','user1','demodemodemo','2024-02-28 08:44:49',NULL),(2,'Sửa lại bản báo cáo và nộp lại cho thầy','user1','demodemodemo','2024-02-28 08:49:50',NULL),(3,'Sửa lại bản báo cáo và nộp lại cho thầy','user1','demodemodemo','2024-02-28 08:52:49',NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `masterdata`
--

DROP TABLE IF EXISTS `masterdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `masterdata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` text,
  `code` text,
  `name` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Bảng tạo ra các trường định nghĩa để lọc';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `masterdata`
--

LOCK TABLES `masterdata` WRITE;
/*!40000 ALTER TABLE `masterdata` DISABLE KEYS */;
INSERT INTO `masterdata` VALUES (1,'subject','SUB01','Bộ môn công nghệ phần mềm'),(2,'subject','SUB01','Bộ môn mạng và hệ thống thông tin'),(3,'subject','SUB01','Bộ môn trí tuệ nhân tạo'),(4,'subject','SUB01','Bộ môn toán'),(5,'subject','SUB01','Bộ môn công nghệ thông tin'),(6,'subject','SUB01','Bộ môn công nghệ thông tin 02'),(7,'subject','SUB01','Bộ môn công nghệ thông tin 51'),(8,'subject','SUB01','Bộ môn công nghệ thông tin 512'),(9,'subject','SUB01','Bộ môn công nghệ thông tin 5121'),(10,'subject','SUB01','Bộ môn công nghệ thông tin 5121'),(11,'subject','SUB01','Bộ môn công nghệ thông tin 5121');
/*!40000 ALTER TABLE `masterdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Mã tin tức',
  `title` varchar(255) DEFAULT NULL COMMENT 'Tiêu đề tin tức',
  `description` text COMMENT 'Mô tả tin tức',
  `file` text COMMENT 'File đính kèm',
  `create_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày tạo',
  `update_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày cập nhật',
  `year` year DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Bảng tin tức';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'Test web SEM 18','ljfljsajf','saljkfasj','2024-02-26 02:51:30','2024-02-26 02:51:30',2024,'Công nghệ thông tin'),(2,'Test web SEM 188','ljfljsajf','saljkfasj','2024-02-26 02:52:01','2024-02-26 02:52:01',2024,'Công nghệ thông tin'),(3,'Test web hệ thống SEO','ljfljsajf','saljkfasj','2024-02-26 02:52:23','2024-02-26 02:52:23',2024,'Công nghệ thông tin'),(4,'Test web hệ thống SEO1','ljfljsajf','saljkfasj','2024-02-28 03:21:02','2024-02-28 03:21:02',2024,'Công nghệ thông tin'),(8,'Xây dựng web bán hàng','ljfljsajf','saljkfasj','2024-03-07 03:35:40','2024-03-07 03:35:40',2020,'Công nghệ thông tin'),(12,'Xây dựng web bán cây trồng 123','ljfljsajf','saljkfasj','2024-03-08 04:09:25','2024-03-08 04:09:25',2020,'Công nghệ thông tin');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Mã thông báo',
  `User_id` int DEFAULT NULL COMMENT 'Người tạo',
  `title` varchar(255) DEFAULT NULL COMMENT 'Tiêu đề/tên thông báo',
  `description` text COMMENT 'Mô tả thông báo',
  `file` text COMMENT 'File đính kèm',
  `is_read` enum('unread','read') DEFAULT NULL COMMENT 'Trạng thái đọc',
  `update_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày cập nhật',
  `create_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày tạo',
  PRIMARY KEY (`id`),
  KEY `User_id` (`User_id`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Bảng quyền';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,NULL,'Thông báo đăng ký đề tài khóa luận tốt nghiệp khoa công nghệ thông tin','Các sinh đủ các yêu cầu vào trang web để đăng ký đề tài và giáo viên hướng đẫn trước ngày 12/12/2024','áddsfa','read','2024-02-27 04:29:14','2024-02-27 04:29:14'),(2,NULL,'Thông báo đăng ký đề tài khóa luận tốt nghiệp khoa công nghệ thông tin','Các sinh đủ các yêu cầu vào trang web để đăng ký đề tài và giáo viên hướng đẫn trước ngày 12/12/2024','áddsfa','read','2024-02-27 04:33:30','2024-02-27 04:33:30'),(3,NULL,'Thông báo nộp đề tài khóa luận tốt nghiệp khoa công nghệ thông tin 2','sinh viên nộp lại bản đăng ký đề tài gửi cho khoa về văn phòng','file dính kèm','read','2024-02-27 04:37:41','2024-02-27 04:37:06'),(6,NULL,'Thông báo nộp đề tài khóa luận tốt nghiệp khoa công nghệ thông tin 69','sinh viên nộp lại bản đăng ký đề tài gửi cho khoa về văn phòng','file dính kèm','read','2024-02-27 04:41:13','2024-02-27 04:41:13'),(8,NULL,'Thông báo nộp đề tài khóa luận tốt nghiệp khoa công nghệ thông tin t69','sinh viên nộp lại bản đăng ký đề tài gửi cho khoa về văn phòng','file dính kèm','read','2024-02-27 05:11:56','2024-02-27 05:11:56'),(10,NULL,'Thông báo nộp đề tài khóa luận tốt nghiệp khoa công nghệ thông tin t629','sinh viên nộp lại bản đăng ký đề tài gửi cho khoa về văn phòng','file dính kèm','read','2024-03-07 04:10:35','2024-03-07 04:10:35');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT 'Tên quyền',
  `description` varchar(255) DEFAULT NULL COMMENT 'Mô tả quyền',
  `create_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày tạo',
  `update_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày cập nhật',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Bảng quyền';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'Tạo người dùng.','Tạo người dùng trong hệ thống.','2024-03-05 02:19:23','2024-03-05 02:21:22'),(2,'Xóa người dùng.','Xóa người dùng trong hệ thống.','2024-03-05 02:19:55','2024-03-05 02:21:24'),(3,'Cập nhật người dùng.','Cập nhật người dùng trong hệ thống.','2024-03-05 02:20:26','2024-03-05 02:21:26'),(4,'Cập nhật đề tài.','Cập nhật đề tài khóa luận tốt nghiệp.','2024-03-05 02:21:19','2024-03-05 02:21:28'),(5,'Xóa đề tài.','Xóa đề tài khóa luận tốt nghiệp.','2024-03-05 02:22:18','2024-03-05 02:22:21'),(6,'Tạo thông báo.','Tạo thông báo.','2024-03-05 02:23:15','2024-03-05 02:23:18'),(7,'Xóa thông báo.','Xóa thông báo.','2024-03-05 02:23:35','2024-03-05 02:23:37'),(8,'Cập nhật thông báo.','Cập nhật thông báo.','2024-03-05 02:23:56','2024-03-05 02:23:58'),(9,'Tạo nhật ký.','Tạo nhật ký.','2024-03-05 02:24:22','2024-03-05 02:24:24'),(10,'Tạo đề tài.','Tạo đề tài khóa luận tốt nghiệp.','2024-03-05 02:27:42','2024-03-05 02:27:43');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT 'Tên vai trò',
  `description` varchar(255) DEFAULT NULL COMMENT 'Mô tả vai trò',
  `create_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày tạo',
  `update_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày cập nhật',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Bảng vai trò';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'TEACHER','Giáo viên hướng dẫn.','2024-02-28 03:55:19','2024-02-28 03:55:24'),(2,'STUDENT','Sinh viên làm đề tài.','2024-02-28 03:55:27','2024-02-28 03:55:43'),(3,'ADMIN','Người quản trị hệ thống.','2024-02-28 03:55:27','2024-02-28 03:55:43'),(4,'DEPARTMENT_HEAD','Trưởng khoa','2024-02-28 03:55:27','2024-02-28 03:55:43'),(5,'ASSISTANT','Trợ lý công việc','2024-02-28 03:55:27','2024-02-28 03:55:43');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `role_id` int NOT NULL COMMENT 'Mã vai trờ',
  `permission_id` int NOT NULL COMMENT 'Mã quyền',
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `role_permission_permission` (`permission_id`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (1,1),(3,1),(4,1),(5,1),(3,2),(1,3),(3,3),(4,3),(5,3),(1,4),(3,4),(4,4),(5,4),(3,5),(4,5),(5,5),(3,6),(4,6),(5,6),(3,7),(4,7),(5,7),(3,8),(4,8),(5,8),(1,9),(3,9),(2,10);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Mã đăng ký',
  `student_id` int NOT NULL,
  `teacher_id` int NOT NULL,
  `name_topic` varchar(255) DEFAULT NULL COMMENT 'Tên đề tài khóa luận tốt nghiệp đăng ký',
  `status` enum('pending','approved','rejected') DEFAULT NULL COMMENT 'Trạng thái',
  `instructor` float DEFAULT NULL COMMENT 'Điểm giáo viên hướng dẫn',
  `reviewer` float DEFAULT NULL COMMENT 'Điểm giáo viên phản biện',
  `board_members_1` float DEFAULT NULL COMMENT 'Điểm thành viên hội đồng thứ nhất',
  `board_members_2` float DEFAULT NULL COMMENT 'Điểm thành viên hội đồng thứ hai',
  `board_members_3` float DEFAULT NULL COMMENT 'Điểm thành viên hội đồng thứ ba',
  `create_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày tạo',
  `update_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày cập nhật',
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `topic_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`),
  CONSTRAINT `topic_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Bảng đề tài';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (1,19,2,'Xây dựng hệ thống quản lý khóa luận tốt nghiệp','pending',9,8,7,9,8,'2024-03-11 05:06:23','2024-03-11 05:06:23'),(2,21,2,'Xây dựng hệ thống quản lý khóa luận tốt nghiệp','pending',9,8,7,9,8,'2024-03-11 07:06:13','2024-03-11 07:06:13'),(3,20,2,'Xây dựng hệ thống web quản lý kho hàng','pending',9,8,7,9,8,'2024-03-11 07:20:11','2024-03-11 07:20:11'),(4,20,2,'Xây dựng hệ thống quản lý khóa luận tốt nghiệp','pending',9,8,7,9,8,'2024-03-11 07:22:16','2024-03-11 07:22:16'),(5,24,2,'Xây dựng hệ thống quản lý khóa luận tốt nghiệp','pending',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,9,2,'Xây dựng web bán quần áo','pending',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,23,2,'Xây dựng web bán quần áo','pending',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT 'Tên người dùng',
  `username` varchar(255) DEFAULT NULL COMMENT 'Tên đăng nhập',
  `password` varchar(255) DEFAULT NULL COMMENT 'Mật khẩu',
  `dob` date DEFAULT NULL COMMENT 'Ngày sinh',
  `address` varchar(255) DEFAULT NULL COMMENT 'Địa chỉ',
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL COMMENT 'SDT',
  `subject` varchar(255) DEFAULT NULL COMMENT 'Thuộc bộ môn nào',
  `create_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày tạo',
  `update_at` timestamp NULL DEFAULT NULL COMMENT 'Ngày cập nhật',
  `role_id` int DEFAULT NULL COMMENT 'Vai trò user',
  `role` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_role_id_fk` (`role_id`),
  CONSTRAINT `user_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Bảng user';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Nguyen Thi Lien','lien1234','$2a$10$lC.3UAEY9i2o.CcSzd.TDupp2Iu0RH6WuYR0sqlnbZwgnBBc/xTLq','2002-02-17','Bac Ninh','lien@gmail.com','0955366282','CNTT','2020-02-26 02:50:32','2024-02-26 13:38:58',3,'ADMIN'),(2,'Nguyen Thi Lien 01','lien123','$2a$10$lC.3UAEY9i2o.CcSzd.TDupp2Iu0RH6WuYR0sqlnbZwgnBBc/xTLq','2000-12-22','Bac Ninh','lien2@gmail.com','0955366282','CNTT','2021-02-26 02:58:01','2024-02-26 02:58:01',1,'TEACHER'),(3,'Nguyen Thi Lien 02','lien123','$2a$10$lC.3UAEY9i2o.CcSzd.TDupp2Iu0RH6WuYR0sqlnbZwgnBBc/xTLq','2000-12-22','Bac Ninh','lien3@gmail.com','0955366282','CNTT','2023-02-26 02:58:06','2024-02-26 02:58:06',2,'STUDENT'),(5,'Nguyen Thi hoa','hoaa123','$2a$10$lC.3UAEY9i2o.CcSzd.TDupp2Iu0RH6WuYR0sqlnbZwgnBBc/xTLq','2000-12-22','Ninh Binh','lien@gmail.com','0955366277','CNTT','2024-02-26 15:19:32','2024-02-26 15:19:32',5,'ASSISTANT'),(8,'Tram Thi Ngoc','ngocc123','$2a$10$lC.3UAEY9i2o.CcSzd.TDupp2Iu0RH6WuYR0sqlnbZwgnBBc/xTLq','2000-12-22','Cam Ranh','ngocc@gmail.com','0955366277','CNTT','2024-02-28 02:27:47','2024-02-28 02:27:47',1,'STUDENT'),(9,'Tram Thi Ngoc Anh','ngocanh123','$2a$10$lC.3UAEY9i2o.CcSzd.TDupp2Iu0RH6WuYR0sqlnbZwgnBBc/xTLq','2000-12-22','Sai Gon','ngoccanh@gmail.com','0955366277','CNTT','2024-02-28 02:28:42','2024-02-28 02:28:42',2,'STUDENT'),(19,'Nguyen van e','vane1112','$2a$10$lC.3UAEY9i2o.CcSzd.TDupp2Iu0RH6WuYR0sqlnbZwgnBBc/xTLq','2000-12-22','Sai Gon','ngoccanh@gmail.com','0955366277','CNTT','2024-02-28 03:12:10','2024-02-28 03:12:10',1,'STUDENT'),(20,'Nguyen van f','vann1112','$2a$10$lC.3UAEY9i2o.CcSzd.TDupp2Iu0RH6WuYR0sqlnbZwgnBBc/xTLq','2000-12-22','Sai Gon','ngoccanh@gmail.com','0955366277','CNTT','2024-02-28 03:13:47','2024-02-28 03:13:47',2,'STUDENT'),(21,'Nguyen van nam','vannam12','$2a$10$lC.3UAEY9i2o.CcSzd.TDupp2Iu0RH6WuYR0sqlnbZwgnBBc/xTLq','2000-12-22','Ha Noi','ngoccanh@gmail.com','0955366277','CNTT','2024-02-28 09:10:57','2024-02-28 09:10:57',NULL,'STUDENT'),(22,'Nguyen van nam','vannam5','$2a$10$lC.3UAEY9i2o.CcSzd.TDupp2Iu0RH6WuYR0sqlnbZwgnBBc/xTLq','2000-12-22','Ha Noi','ngoccanh@gmail.com','0955366277','CNTT','2024-03-05 04:08:17','2024-03-05 04:08:17',NULL,'STUDENT'),(23,'Nguyen van nu','vannuu','$2a$10$dDv9Gf10yUXSISbOWhPzI.KPG9WDePyNJ9xPV7tFni5NxmwcumKai','2000-12-22','Ha Noi','ngoccanh@gmail.com','0955366277','CNTT','2024-03-05 04:19:00','2024-03-05 04:19:00',NULL,'STUDENT'),(24,'Hoàng Trâm Anh','tramanh1','$2a$10$dDv9Gf10yUXSISbOWhPzI.KPG9WDePyNJ9xPV7tFni5NxmwcumKai',NULL,NULL,'nguyenthilien888899990000@gmail.com','0835701321',NULL,NULL,NULL,NULL,'STUDENT');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-11 16:26:01
