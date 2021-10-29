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

-- 테이블 scrobotdb.prjct2 구조 내보내기
CREATE TABLE IF NOT EXISTS `prjct2` (
  `prjct_id` varchar(255) NOT NULL,
  `prjct_nm` varchar(255) DEFAULT NULL,
  `prjct_cn` varchar(4000) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `regist_dt` datetime DEFAULT NULL,
  `updt_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`prjct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.prjct2:~4 rows (대략적) 내보내기
DELETE FROM `prjct2`;
/*!40000 ALTER TABLE `prjct2` DISABLE KEYS */;
INSERT INTO `prjct2` (`prjct_id`, `prjct_nm`, `prjct_cn`, `user_id`, `regist_dt`, `updt_dt`) VALUES
	('07102b27', '수의 프로젝트', '프로젝트 설명', 'soo', '2021-10-09 12:40:21', NULL),
	('3b056bd3', '유저의 프로젝트', '내용 \n설명', 'user', '2021-10-11 18:21:17', NULL),
	('ca41cd7f', '프로젝트(복사본)', '프로젝트 내용 \r\n내요 내용', 'user', '2021-09-27 22:55:11', NULL),
	('d988860d', '수의 프로젝트(복사본)', '프로젝트 설명', 'soo', '2021-10-09 12:41:59', NULL);
/*!40000 ALTER TABLE `prjct2` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
