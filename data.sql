-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.5.9-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.3.0.6337
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- scrobotdb 데이터베이스 구조 내보내기 
DROP DATABASE IF EXISTS `scrobotdb`;
CREATE DATABASE IF NOT EXISTS `scrobotdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `scrobotdb`;

-- 테이블 scrobotdb.bbs 구조 내보내기
DROP TABLE IF EXISTS `bbs`;
CREATE TABLE IF NOT EXISTS `bbs` (
  `bbs_pk` varchar(255) NOT NULL,
  `bbs_nm` varchar(255) DEFAULT NULL,
  `bbs_cn` varchar(255) NOT NULL COMMENT 'bbs_cn',
  PRIMARY KEY (`bbs_pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.bbs:~2 rows (대략적) 내보내기
DELETE FROM `bbs`;
/*!40000 ALTER TABLE `bbs` DISABLE KEYS */;
INSERT INTO `bbs` (`bbs_pk`, `bbs_nm`, `bbs_cn`) VALUES
	('03aeca17', 'aaaa', ''),
	('42185a84', 'ABCD', '');
/*!40000 ALTER TABLE `bbs` ENABLE KEYS */;

-- 테이블 scrobotdb.cmmn_code 구조 내보내기
DROP TABLE IF EXISTS `cmmn_code`;
CREATE TABLE IF NOT EXISTS `cmmn_code` (
  `cmmn_code_id` bigint(20) NOT NULL,
  `cmmn_code` varchar(255) DEFAULT NULL,
  `cmmn_code_nm` varchar(255) DEFAULT NULL,
  `prnts_cmmn_code` varchar(255) DEFAULT NULL,
  `use_at` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cmmn_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.cmmn_code:~3 rows (대략적) 내보내기
DELETE FROM `cmmn_code`;
/*!40000 ALTER TABLE `cmmn_code` DISABLE KEYS */;
INSERT INTO `cmmn_code` (`cmmn_code_id`, `cmmn_code`, `cmmn_code_nm`, `prnts_cmmn_code`, `use_at`) VALUES
	(1, 'use_at', '사용 여부', '-', 'Y'),
	(2, 'Y', '예', 'use_at', 'Y'),
	(3, 'N', '아니오', 'use_at', 'Y');
/*!40000 ALTER TABLE `cmmn_code` ENABLE KEYS */;

-- 테이블 scrobotdb.comm_cd 구조 내보내기
DROP TABLE IF EXISTS `comm_cd`;
CREATE TABLE IF NOT EXISTS `comm_cd` (
  `comm_cd_pk` varchar(255) NOT NULL,
  PRIMARY KEY (`comm_cd_pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.comm_cd:~0 rows (대략적) 내보내기
DELETE FROM `comm_cd`;
/*!40000 ALTER TABLE `comm_cd` DISABLE KEYS */;
/*!40000 ALTER TABLE `comm_cd` ENABLE KEYS */;

-- 테이블 scrobotdb.compn 구조 내보내기
DROP TABLE IF EXISTS `compn`;
CREATE TABLE IF NOT EXISTS `compn` (
  `compn_id` varchar(255) NOT NULL,
  `compn_nm` varchar(255) DEFAULT NULL,
  `eng_abrv_nm` varchar(255) DEFAULT NULL,
  `hngl_abrv_nm` varchar(255) DEFAULT NULL,
  `compn_cn` varchar(4000) DEFAULT NULL,
  `scrin_id` varchar(255) DEFAULT NULL,
  `compn_se_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`compn_id`),
  KEY `FKbv2xyoigeh3bqxc1lqt735inh` (`scrin_id`),
  CONSTRAINT `FKbv2xyoigeh3bqxc1lqt735inh` FOREIGN KEY (`scrin_id`) REFERENCES `scrin` (`scrin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.compn:~22 rows (대략적) 내보내기
DELETE FROM `compn`;
/*!40000 ALTER TABLE `compn` DISABLE KEYS */;
INSERT INTO `compn` (`compn_id`, `compn_nm`, `eng_abrv_nm`, `hngl_abrv_nm`, `compn_cn`, `scrin_id`, `compn_se_code`) VALUES
	('c1', 'null', 'bbs_nm', '게시 명', '<input type="text" style="position:absolute; top:250px; left:400px"/>', 'ac5601d5', 'INPUT'),
	('c10', '', '', '', '<button type="button" class="btn btn-primary" data-btn-se="CANCEL" style="position:absolute; left:400px; top:500px;">cancel</button>', 'ac5601d5', 'BUTTON'),
	('c12', NULL, NULL, NULL, '<table class="table list" style="position:absolute; ">\r\n  <thead>\r\n    <tr>\r\n      <th data-eng-abrv-nm="bbs_nm"></th>\r\n      <th data-eng-abrv-nm="bbs_cn"></th>\r\n      <th data-eng-abrv-nm="use_at"></th>\r\n    </tr>\r\n  </thead>\r\n  \r\n  <tbody>\r\n    <tr data-template="true">\r\n      <td data-eng-abrv-nm="bbs_nm"></td>\r\n      <td data-eng-abrv-nm="bbs_cn"></td>\r\n      <td data-eng-abrv-nm="use_at" data-prnts-cmmn-code="use_at"></td>\r\n    </tr>\r\n  </tbody>\r\n</table>', 's1', 'TABLE'),
	('c13', NULL, NULL, NULL, '<span style="position:absolute; top:250px; left:300px;">게시 명</span>', 'ac5601d5', 'SPAN'),
	('c14', NULL, NULL, NULL, '<span style="position:absolute; top:250px; left:300px;" >게시 명</span>', 's2', 'SPAN'),
	('c15', NULL, 'bbs_nm', '게시명', '<input type="text" style="position:absolute; top:250px; left:400px;" readonly disabled >', 's2', 'INPUT'),
	('c16', NULL, '', '', '<span style="position:absolute; top:350px; left:300px">게시 설명</span>', 's2', 'SPAN'),
	('c17', NULL, 'bbs_cn', '게시 설명', '<input type="text" style="position:absolute; top:350px; left:400px;" readonly disabled >', 's2', 'INPUT'),
	('c18', NULL, NULL, NULL, '<span style="position:absolute; top:350px; left:300px;">게시 설명</span>', 'ac5601d5', 'SPAN'),
	('c19', NULL, NULL, NULL, '<button type="button" class="btn btn-primary" data-btn-se="CANCEL" style="position:absolute; top:500px; left:400px;">취소</button>', 'dc712f06', 'BUTTON'),
	('c2', '', '', '', '<button type="button" class="btn btn-primary" data-btn-se="C" style="position:absolute;  top:500px;" >save</button>', 'ac5601d5', 'BUTTON'),
	('c20', NULL, 'bbs_cn', '게시 설명', '<input type="text" style="position:absolute; top:350px; left:400px; ">', 'dc712f06', 'INPUT'),
	('c21', NULL, '', '', '<span style="position:absolute; top:250px; left:300px; ">게시 명</span>', 'dc712f06', 'SPAN'),
	('c22', NULL, '', '', '<span style="position:absolute; top:350px; left:300px; ">게시 설명</span>', 'dc712f06', 'SPAN'),
	('c23', NULL, 'use_at', '사용', '<select data-prnts-cmmn-code="use_at" style="position:absolute; top:450px; left:400px;"></select>', 'ac5601d5', 'SELECT'),
	('c24', NULL, '', '', '<span style="position:absolute; top:450px; left:300px;">사용</span>', 'ac5601d5', 'SPAN'),
	('c3', '', 'bbs_cn', '게시 설명', '<input type="text" style="position:absolute; top:350px; left:400px ">', 'ac5601d5', 'INPUT'),
	('c4', NULL, NULL, NULL, '<button type="button" class="btn btn-primary" data-btn-se="U" style="position:absolute; top:500px; left:300px;">수정</button>', 'dc712f06', 'BUTTON'),
	('c5', NULL, 'bbs_nm', '게시 명', '<input type="text" style="position:absolute; top:250px; left:400px; ">', 'dc712f06', 'INPUT'),
	('c6', NULL, NULL, NULL, '<button type="button" class="btn btn-primary" data-btn-se="C" style="position:absolute;top:500px;">regist</button>', 's1', 'BUTTON'),
	('c7', NULL, NULL, NULL, '<button type="button" class="btn btn-primary" data-btn-se="U" style="position:absolute;top:500px;">update</button>', 's2', 'BUTTON'),
	('c8', NULL, NULL, NULL, '<button type="button" class="btn btn-primary" data-btn-se="D" style="position:absolute;top:500px; left:500px;">delete</button>', 's2', 'BUTTON'),
	('c9', NULL, NULL, NULL, '<button type="button" class="btn btn-primary" data-btn-se="L" style="position:absolute;top:500px; left:600px;">list</button>', 's2', 'BUTTON');
/*!40000 ALTER TABLE `compn` ENABLE KEYS */;

-- 테이블 scrobotdb.menu2 구조 내보내기
DROP TABLE IF EXISTS `menu2`;
CREATE TABLE IF NOT EXISTS `menu2` (
  `menu_id` varchar(255) NOT NULL,
  `menu_nm` varchar(255) DEFAULT NULL,
  `prnts_menu_id` varchar(255) DEFAULT NULL,
  `url_nm` varchar(255) DEFAULT NULL,
  `scrin_id` varchar(255) DEFAULT NULL,
  `menu_ordr_value` int(11) DEFAULT NULL,
  `prjct_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`menu_id`),
  KEY `FK5f3iy3pqnxestwlvmfjwncrd4` (`prjct_id`),
  CONSTRAINT `FK5f3iy3pqnxestwlvmfjwncrd4` FOREIGN KEY (`prjct_id`) REFERENCES `prjct2` (`prjct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.menu2:~0 rows (대략적) 내보내기
DELETE FROM `menu2`;
/*!40000 ALTER TABLE `menu2` DISABLE KEYS */;
INSERT INTO `menu2` (`menu_id`, `menu_nm`, `prnts_menu_id`, `url_nm`, `scrin_id`, `menu_ordr_value`, `prjct_id`) VALUES
	('m1', '게시판', '-', 'null', 'ac5601d5', 0, 'cffcf0ad');
/*!40000 ALTER TABLE `menu2` ENABLE KEYS */;

-- 테이블 scrobotdb.prjct2 구조 내보내기
DROP TABLE IF EXISTS `prjct2`;
CREATE TABLE IF NOT EXISTS `prjct2` (
  `prjct_id` varchar(255) NOT NULL,
  `prjct_nm` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`prjct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.prjct2:~1 rows (대략적) 내보내기
DELETE FROM `prjct2`;
/*!40000 ALTER TABLE `prjct2` DISABLE KEYS */;
INSERT INTO `prjct2` (`prjct_id`, `prjct_nm`, `user_id`) VALUES
	('cffcf0ad', 'abcd', 'vaiv');
/*!40000 ALTER TABLE `prjct2` ENABLE KEYS */;

-- 테이블 scrobotdb.prjct_trget_sys_mapng 구조 내보내기
DROP TABLE IF EXISTS `prjct_trget_sys_mapng`;
CREATE TABLE IF NOT EXISTS `prjct_trget_sys_mapng` (
  `prjct_trget_sys_mapng_id` int(11) NOT NULL,
  `prjct_id` varchar(255) DEFAULT NULL,
  `trget_sys_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`prjct_trget_sys_mapng_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.prjct_trget_sys_mapng:~1 rows (대략적) 내보내기
DELETE FROM `prjct_trget_sys_mapng`;
/*!40000 ALTER TABLE `prjct_trget_sys_mapng` DISABLE KEYS */;
INSERT INTO `prjct_trget_sys_mapng` (`prjct_trget_sys_mapng_id`, `prjct_id`, `trget_sys_id`) VALUES
	(1, 'cffcf0ad', '63a2de08');
/*!40000 ALTER TABLE `prjct_trget_sys_mapng` ENABLE KEYS */;

-- 테이블 scrobotdb.scrin 구조 내보내기
DROP TABLE IF EXISTS `scrin`;
CREATE TABLE IF NOT EXISTS `scrin` (
  `scrin_id` varchar(255) NOT NULL,
  `scrin_nm` varchar(255) DEFAULT NULL,
  `scrin_group_id` varchar(255) DEFAULT NULL,
  `scrin_se_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`scrin_id`),
  KEY `FK8cfaakh99646e168dlo07ws3k` (`scrin_group_id`),
  CONSTRAINT `FK8cfaakh99646e168dlo07ws3k` FOREIGN KEY (`scrin_group_id`) REFERENCES `scrin_group` (`scrin_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.scrin:~4 rows (대략적) 내보내기
DELETE FROM `scrin`;
/*!40000 ALTER TABLE `scrin` DISABLE KEYS */;
INSERT INTO `scrin` (`scrin_id`, `scrin_nm`, `scrin_group_id`, `scrin_se_code`) VALUES
	('ac5601d5', 'bbs regist', '1f066d30', 'C'),
	('dc712f06', 'bbs update', '1f066d30', 'U'),
	('s1', 'bbs list', '1f066d30', 'L'),
	('s2', 'bbs read', '1f066d30', 'R');
/*!40000 ALTER TABLE `scrin` ENABLE KEYS */;

-- 테이블 scrobotdb.scrin_group 구조 내보내기
DROP TABLE IF EXISTS `scrin_group`;
CREATE TABLE IF NOT EXISTS `scrin_group` (
  `scrin_group_id` varchar(255) NOT NULL,
  `scrin_group_nm` varchar(255) DEFAULT NULL,
  `prjct_id` varchar(255) DEFAULT NULL,
  `eng_abrv_nm` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`scrin_group_id`),
  KEY `FK7d2nyfvv7pdxli389i1hx3jq` (`prjct_id`),
  CONSTRAINT `FK7d2nyfvv7pdxli389i1hx3jq` FOREIGN KEY (`prjct_id`) REFERENCES `prjct2` (`prjct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.scrin_group:~2 rows (대략적) 내보내기
DELETE FROM `scrin_group`;
/*!40000 ALTER TABLE `scrin_group` DISABLE KEYS */;
INSERT INTO `scrin_group` (`scrin_group_id`, `scrin_group_nm`, `prjct_id`, `eng_abrv_nm`) VALUES
	('1f066d30', 'bbs', 'cffcf0ad', 'bbs'),
	('45c090cf', 'common code', 'cffcf0ad', 'comm_cd');
/*!40000 ALTER TABLE `scrin_group` ENABLE KEYS */;

-- 테이블 scrobotdb.trget_sys 구조 내보내기
DROP TABLE IF EXISTS `trget_sys`;
CREATE TABLE IF NOT EXISTS `trget_sys` (
  `trget_sys_id` varchar(255) NOT NULL,
  `trget_sys_nm` varchar(255) DEFAULT NULL,
  `db_ty_nm` varchar(255) DEFAULT NULL,
  `db_driver_nm` varchar(255) DEFAULT NULL,
  `db_url_nm` varchar(255) DEFAULT NULL,
  `db_user_nm` varchar(255) DEFAULT NULL,
  `db_password_nm` varchar(255) DEFAULT NULL,
  `db_nm` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`trget_sys_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.trget_sys:~1 rows (대략적) 내보내기
DELETE FROM `trget_sys`;
/*!40000 ALTER TABLE `trget_sys` DISABLE KEYS */;
INSERT INTO `trget_sys` (`trget_sys_id`, `trget_sys_nm`, `db_ty_nm`, `db_driver_nm`, `db_url_nm`, `db_user_nm`, `db_password_nm`, `db_nm`) VALUES
	('63a2de08', NULL, 'MySQL', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/scrobotdb_trget', 'root', 'password', 'scrobotdb_trget');
/*!40000 ALTER TABLE `trget_sys` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
