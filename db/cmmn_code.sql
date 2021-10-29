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

-- 테이블 scrobotdb.cmmn_code 구조 내보내기
CREATE TABLE IF NOT EXISTS `cmmn_code` (
  `cmmn_code_id` bigint(20) NOT NULL,
  `cmmn_code` varchar(255) DEFAULT NULL,
  `cmmn_code_nm` varchar(255) DEFAULT NULL,
  `prnts_cmmn_code` varchar(255) DEFAULT NULL,
  `use_at` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cmmn_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.cmmn_code:~11 rows (대략적) 내보내기
DELETE FROM `cmmn_code`;
/*!40000 ALTER TABLE `cmmn_code` DISABLE KEYS */;
INSERT INTO `cmmn_code` (`cmmn_code_id`, `cmmn_code`, `cmmn_code_nm`, `prnts_cmmn_code`, `use_at`) VALUES
	(1, 'scrin_se', '화면 구분', '-', 'Y'),
	(2, 'C', '등록', 'scrin_se', 'Y'),
	(3, 'R', '조회', 'scrin_se', 'Y'),
	(4, 'btn_se', '버튼 구분', '-', 'Y'),
	(5, 'C', '등록', 'btn_se', 'Y'),
	(6, 'R', '조회', 'btn_se', 'Y'),
	(7, 'D', '삭제', 'btn_se', 'Y'),
	(8, 'L', '목록', 'scrin_se', 'Y'),
	(9, 'U', '수정', 'scrin_se', 'Y'),
	(10, 'U', '수정', 'btn_se', 'Y'),
	(11, 'CANCEL', '취소', 'btn_se', 'Y');
/*!40000 ALTER TABLE `cmmn_code` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
