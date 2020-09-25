-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: dcom_api
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (9,'500 anh em'),(10,'Ancient aliens'),(20,'Cao Bá Hưng'),(23,'Chuyện tình lãng mạn'),(3,'Clip funvl'),(15,'Comment hài'),(2,'Cách gặp ma'),(8,'Faptv'),(24,'Giang hồ'),(21,'Hài Quang Thắng'),(19,'Ji Suk Jin'),(16,'Nhạc Remix hay'),(18,'Pháo hoa Tết'),(6,'Running man'),(12,'Siêu nhân'),(14,'Thánh cuồng'),(11,'Video cảm động'),(13,'Video kinh dị'),(4,'Xác ướp'),(22,'Xăm trổ'),(7,'Ảnh bựa VL'),(1,'Ảnh troll'),(17,'Ảnh ấn tượng'),(5,'Ảo tưởng sức mạnh');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `confirmation_token`
--

DROP TABLE IF EXISTS `confirmation_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `confirmation_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modified_at` datetime DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `confirmation_token` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `expiration_date` datetime DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKah4p1rycwibwm6s9bsyeckq51` (`user_id`),
  CONSTRAINT `FKah4p1rycwibwm6s9bsyeckq51` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmation_token`
--

LOCK TABLES `confirmation_token` WRITE;
/*!40000 ALTER TABLE `confirmation_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `confirmation_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modified_at` datetime DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `count` int NOT NULL,
  `status` int NOT NULL,
  `url_image` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7ky67sgi7k0ayf22652f7763r` (`user_id`),
  CONSTRAINT `FK7ky67sgi7k0ayf22652f7763r` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'2020-09-21 22:15:23',NULL,'2020-09-21 22:15:23',NULL,'test05.7760924123972766',0,0,'https://i.memeful.com/media/post/GMEZxyR_700wa_0.gif',1),(2,'2020-09-21 22:15:23',NULL,'2020-09-21 22:15:23',NULL,'test13.5542547608934405',0,0,'https://i.memeful.com/media/post/ER5rbyd_700wa_0.gif',1),(3,'2020-09-21 22:15:23',NULL,'2020-09-21 22:15:23',NULL,'test25.251652653487086',0,0,'https://i.memeful.com/media/post/lMzzGBM_700wa_0.gif',1),(4,'2020-09-21 22:15:23',NULL,'2020-09-21 22:15:23',NULL,'test31.418755775118128',0,0,'https://i.memeful.com/media/post/edvgX8w_700wa_0.gif',1),(5,'2020-09-21 22:15:23',NULL,'2020-09-21 22:15:23',NULL,'test47.9320551881719235',0,0,'https://i.memeful.com/media/post/odgmQGM_700wa_0.gif',1),(6,'2020-09-21 22:15:23',NULL,'2020-09-21 22:15:23',NULL,'test58.574898303519598',0,0,'https://i.memeful.com/media/post/edvEj5R_700wa_0.gif',1),(7,'2020-09-21 22:15:23',NULL,'2020-09-21 22:15:23',NULL,'test66.835086389096709',0,0,'https://i.memeful.com/media/post/kRp6z2w_700wa_0.gif',1),(8,'2020-09-21 22:15:23',NULL,'2020-09-21 22:15:23',NULL,'test77.5451437808894255',0,0,'https://i.memeful.com/media/post/mdGW0Vd_700wa_0.gif',1),(9,'2020-09-21 22:15:23',NULL,'2020-09-21 22:15:23',NULL,'test82.740270419468851',0,0,'https://i.memeful.com/media/post/NwrGA7M_700wa_0.gif',1),(10,'2020-09-21 22:15:23',NULL,'2020-09-21 22:15:23',NULL,'test98.5062015435967',0,0,'https://i.memeful.com/media/post/YMKmXGd_700wa_0.gif',1),(16,'2020-09-24 23:51:14','admin','2020-09-24 23:51:14','admin','afsdaf',0,0,'Screenshot (2).png',1);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_category`
--

DROP TABLE IF EXISTS `post_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_category` (
  `post_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`,`category_id`),
  KEY `FKqly0d5oc4npxdig2fjfoshhxg` (`category_id`),
  CONSTRAINT `FKqly0d5oc4npxdig2fjfoshhxg` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKqr4dx4cx1lh4jfjchabytcakl` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_category`
--

LOCK TABLES `post_category` WRITE;
/*!40000 ALTER TABLE `post_category` DISABLE KEYS */;
INSERT INTO `post_category` VALUES (9,2),(10,2),(8,3),(4,4),(5,5),(2,6),(7,6),(3,8),(6,8),(1,9);
/*!40000 ALTER TABLE `post_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_MODERATOR'),(3,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKt7e7djp752sqn6w22i6ocqy6q` (`role_id`),
  CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKt7e7djp752sqn6w22i6ocqy6q` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(1,2),(1,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modified_at` datetime DEFAULT NULL,
  `modified_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `gender` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `is_enabled` bit(1) NOT NULL,
  `last_login` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(120) COLLATE utf8mb4_bin DEFAULT NULL,
  `permission` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `profile_picture` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `profile_views` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `username` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `you_viewed` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `your_viewed` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2020-09-21 22:15:22',NULL,'2020-09-21 22:15:22',NULL,'Chủ nghĩa tự do','lminh9812@gmail.com',NULL,NULL,_binary '',NULL,NULL,'$2a$10$rPcZxy74NG9Qn6rrohIHh.3IsAwlhSMA.7KYxhPrL9vNOksRYY7xa',NULL,NULL,NULL,'admin','3','2');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-26  2:03:00
