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

-- 테이블 scrobotdb.trget_sys 구조 내보내기
CREATE TABLE IF NOT EXISTS `trget_sys` (
  `trget_sys_id` varchar(255) NOT NULL,
  `db_driver_nm` varchar(255) DEFAULT NULL,
  `db_nm` varchar(255) DEFAULT NULL,
  `db_password_nm` varchar(255) DEFAULT NULL,
  `db_ty_nm` varchar(255) DEFAULT NULL,
  `db_url_nm` varchar(255) DEFAULT NULL,
  `db_user_nm` varchar(255) DEFAULT NULL,
  `trget_sys_nm` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`trget_sys_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.trget_sys:~0 rows (대략적) 내보내기
DELETE FROM `trget_sys`;
/*!40000 ALTER TABLE `trget_sys` DISABLE KEYS */;
INSERT INTO `trget_sys` (`trget_sys_id`, `db_driver_nm`, `db_nm`, `db_password_nm`, `db_ty_nm`, `db_url_nm`, `db_user_nm`, `trget_sys_nm`) VALUES
	('t1', 'com.mysql.cj.jdbc.Driver', 'scrobotdb_trget', 'password', 'MySQL', 'jdbc:mysql://localhost:3306/scrobotdb_trget', 'root', 'mysql_t1');
/*!40000 ALTER TABLE `trget_sys` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
