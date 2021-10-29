-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.2.10-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.3.0.6344
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 테이블 scrobotdb.prjct_trget_sys_mapng 구조 내보내기
CREATE TABLE IF NOT EXISTS `prjct_trget_sys_mapng` (
  `prjct_trget_sys_mapng_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prjct_id` varchar(255) DEFAULT NULL,
  `trget_sys_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`prjct_trget_sys_mapng_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.prjct_trget_sys_mapng:~0 rows (대략적) 내보내기
DELETE FROM `prjct_trget_sys_mapng`;
/*!40000 ALTER TABLE `prjct_trget_sys_mapng` DISABLE KEYS */;
INSERT INTO `prjct_trget_sys_mapng` (`prjct_trget_sys_mapng_id`, `prjct_id`, `trget_sys_id`) VALUES
	(3, 'ca41cd7f', 't1'),
	(4, '3b056bd3', 't1');
/*!40000 ALTER TABLE `prjct_trget_sys_mapng` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
