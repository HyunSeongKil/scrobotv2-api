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

-- 테이블 scrobotdb.menu2 구조 내보내기
CREATE TABLE IF NOT EXISTS `menu2` (
  `menu_id` varchar(255) NOT NULL,
  `menu_nm` varchar(255) DEFAULT NULL,
  `menu_ordr_value` int(11) DEFAULT NULL,
  `prjct_id` varchar(255) DEFAULT NULL,
  `prnts_menu_id` varchar(255) DEFAULT NULL,
  `scrin_id` varchar(255) DEFAULT NULL,
  `url_nm` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`menu_id`),
  KEY `FK5f3iy3pqnxestwlvmfjwncrd4` (`prjct_id`),
  CONSTRAINT `FK5f3iy3pqnxestwlvmfjwncrd4` FOREIGN KEY (`prjct_id`) REFERENCES `prjct2` (`prjct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.menu2:~7 rows (대략적) 내보내기
DELETE FROM `menu2`;
/*!40000 ALTER TABLE `menu2` DISABLE KEYS */;
INSERT INTO `menu2` (`menu_id`, `menu_nm`, `menu_ordr_value`, `prjct_id`, `prnts_menu_id`, `scrin_id`, `url_nm`) VALUES
	('0e96d9ba', '목록', 4, 'ca41cd7f', '757c47fd', 'Lbc4c12cc', NULL),
	('113a4b82', '목록', 7, '3b056bd3', '8504e573', 'L0d416d99', NULL),
	('721c3923', '회원 관리', 2, 'ca41cd7f', '-', '', NULL),
	('757c47fd', '게시판 관리', 0, 'ca41cd7f', '-', '', NULL),
	('7f7268c9', '기타 관리', 3, 'ca41cd7f', '-', '', NULL),
	('8504e573', '회원 관리', 5, '3b056bd3', '-', '', NULL),
	('969af855', '등록', 1, 'ca41cd7f', '757c47fd', '70da2235', NULL),
	('c73ed6d3', '등록', 6, '3b056bd3', '8504e573', 'C407d1b5f', NULL);
/*!40000 ALTER TABLE `menu2` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
