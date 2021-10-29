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

-- 테이블 scrobotdb.scrin_group 구조 내보내기
CREATE TABLE IF NOT EXISTS `scrin_group` (
  `scrin_group_id` varchar(255) NOT NULL,
  `eng_abrv_nm` varchar(255) DEFAULT NULL,
  `prjct_id` varchar(255) DEFAULT NULL,
  `scrin_group_nm` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`scrin_group_id`),
  KEY `FK7d2nyfvv7pdxli389i1hx3jq` (`prjct_id`),
  CONSTRAINT `FK7d2nyfvv7pdxli389i1hx3jq` FOREIGN KEY (`prjct_id`) REFERENCES `prjct2` (`prjct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.scrin_group:~3 rows (대략적) 내보내기
DELETE FROM `scrin_group`;
/*!40000 ALTER TABLE `scrin_group` DISABLE KEYS */;
INSERT INTO `scrin_group` (`scrin_group_id`, `eng_abrv_nm`, `prjct_id`, `scrin_group_nm`) VALUES
	('821eb9a6', 'mber_manage', '3b056bd3', '회원 관리'),
	('88a4ce72', 'mber_manage', 'ca41cd7f', '회원 관리'),
	('e934edcd', 'bbsctt_manage', 'ca41cd7f', '게시글 관리'),
	('eb805f1a', 'bbsctt_manage', '3b056bd3', '게시글 관리');
/*!40000 ALTER TABLE `scrin_group` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
