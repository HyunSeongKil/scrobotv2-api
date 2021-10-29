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

-- 테이블 scrobotdb.scrin 구조 내보내기
CREATE TABLE IF NOT EXISTS `scrin` (
  `scrin_id` varchar(255) NOT NULL,
  `scrin_group_id` varchar(255) DEFAULT NULL,
  `scrin_nm` varchar(255) DEFAULT NULL,
  `scrin_se_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`scrin_id`),
  KEY `FK8cfaakh99646e168dlo07ws3k` (`scrin_group_id`),
  CONSTRAINT `FK8cfaakh99646e168dlo07ws3k` FOREIGN KEY (`scrin_group_id`) REFERENCES `scrin_group` (`scrin_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.scrin:~10 rows (대략적) 내보내기
DELETE FROM `scrin`;
/*!40000 ALTER TABLE `scrin` DISABLE KEYS */;
INSERT INTO `scrin` (`scrin_id`, `scrin_group_id`, `scrin_nm`, `scrin_se_code`) VALUES
	('70da2235', 'e934edcd', '등록', 'C'),
	('C407d1b5f', '821eb9a6', '회원 등록', 'C'),
	('c519e14e', NULL, '게시판 조회', 'R'),
	('Ccfdbee63', '88a4ce72', '회원 등록', 'C'),
	('L0d416d99', '821eb9a6', '회원 목록', 'L'),
	('Lbc4c12cc', 'e934edcd', '게시글 목록', 'L'),
	('R1702888b', 'e934edcd', '게시글 조회', 'R'),
	('R681e6b70', '821eb9a6', '회원 조회', 'R'),
	('U19cd3e5b', NULL, '게시글 수정', 'U'),
	('U26a2c041', '821eb9a6', '회원 수정', 'U'),
	('U9ea6ca57', 'e934edcd', '게시글 수정', 'U');
/*!40000 ALTER TABLE `scrin` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
