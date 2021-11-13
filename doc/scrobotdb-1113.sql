-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.5.9-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.3.0.6369
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

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
INSERT INTO `bbs` (`bbs_pk`, `bbs_nm`, `bbs_cn`) VALUES
	('03aeca17', 'aaaa', ''),
	('42185a84', 'ABCD', '');

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

-- 테이블 데이터 scrobotdb.cmmn_code:~8 rows (대략적) 내보내기
DELETE FROM `cmmn_code`;
INSERT INTO `cmmn_code` (`cmmn_code_id`, `cmmn_code`, `cmmn_code_nm`, `prnts_cmmn_code`, `use_at`) VALUES
	(1, 'use_at', '사용 여부', '-', 'Y'),
	(2, 'Y', '예', 'use_at', 'Y'),
	(3, 'N', '아니오', 'use_at', 'Y'),
	(4, 'scrin_se', '화면 구분', '-', 'Y'),
	(5, 'C', '등록', 'scrin_se', 'Y'),
	(6, 'R', '조회', 'scrin_se', 'Y'),
	(7, 'U', '수정', 'scrin_se', 'Y'),
	(8, 'D', '삭제', 'scrin_se', 'Y'),
	(9, 'L', '목록', 'scrin_se', 'Y');

-- 테이블 scrobotdb.comm_cd 구조 내보내기
DROP TABLE IF EXISTS `comm_cd`;
CREATE TABLE IF NOT EXISTS `comm_cd` (
  `comm_cd_pk` varchar(255) NOT NULL,
  PRIMARY KEY (`comm_cd_pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.comm_cd:~0 rows (대략적) 내보내기
DELETE FROM `comm_cd`;

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
  `ordr_value` int(11) DEFAULT 0,
  `regist_dt` datetime DEFAULT NULL,
  `updt_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`compn_id`),
  KEY `FKbv2xyoigeh3bqxc1lqt735inh` (`scrin_id`),
  CONSTRAINT `FKbv2xyoigeh3bqxc1lqt735inh` FOREIGN KEY (`scrin_id`) REFERENCES `scrin` (`scrin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.compn:~4 rows (대략적) 내보내기
DELETE FROM `compn`;
INSERT INTO `compn` (`compn_id`, `compn_nm`, `eng_abrv_nm`, `hngl_abrv_nm`, `compn_cn`, `scrin_id`, `compn_se_code`, `ordr_value`, `regist_dt`, `updt_dt`) VALUES
	('2ee7f295', 'dummy', '', '', '<h1 id="id_1633435461036" style="position: absolute; width: 200px; height: 50px; border: none; left: 37px; top: 85px; font-family: system-ui, -apple-system, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, &quot;Liberation Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; color: rgb(33, 37, 41); background-color: rgb(0, 255, 255); font-size: 40px;" data-tag-name="h1" data-eng-abrv-nm="" data-hngl-abrv-nm="" class="">제목1000</h1>', 's1', 'h1', 1, '2021-11-06 18:09:55', '2021-11-06 18:09:55'),
	('2f060680', 'dummy', '', '', '<div id="id_1633435456554" style="width: 713px; height: 509px; background-color: whitesmoke; position: absolute; border: none; left: 32px; top: 47px; color: rgb(33, 37, 41); font-family: system-ui, -apple-system, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, &quot;Liberation Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; font-size: 16px;" data-tag-name="div" data-eng-abrv-nm="" data-hngl-abrv-nm="" class=""></div>', 's1', 'div', 0, '2021-11-06 18:09:55', '2021-11-06 18:09:55'),
	('7c2aaca5', 'dummy', '', '', '<div id="id_1633435464947_wrapper" class="wrapper" style="position: absolute; width: 150px; height: 30px; border: none; left: 260px; top: 145px;" data-tag-name="input"><input type="text" id="id_1633435464947" style="width: 145px; height: 25px; padding: 0.5em; background-color: rgb(239, 239, 239);" readonly="" focus="" data-eng-abrv-nm="" data-hngl-abrv-nm=""></div>', 's1', 'input', 3, '2021-11-06 18:09:55', '2021-11-06 18:09:55'),
	('875eb1da', 'dummy', '', '', '<span id="id_1633435463080" style="width: 99px; height: 41px; position: absolute; background-color: rgb(239, 239, 239); border: none; left: 82px; top: 177px; font-family: system-ui, -apple-system, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, &quot;Liberation Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; color: rgb(33, 37, 41); font-size: 20px;" data-tag-name="span" data-eng-abrv-nm="" data-hngl-abrv-nm="" class="">레이블000</span>', 's1', 'span', 2, '2021-11-06 18:09:55', '2021-11-06 18:09:55');

-- 테이블 scrobotdb.mb 구조 내보내기
DROP TABLE IF EXISTS `mb`;
CREATE TABLE IF NOT EXISTS `mb` (
  `mb_id` varchar(255) NOT NULL,
  `last_login_dt` datetime(6) DEFAULT NULL,
  `mb_nm` varchar(255) DEFAULT NULL,
  `password` varchar(1000) DEFAULT NULL,
  `regist_dt` datetime(6) DEFAULT NULL,
  `sttus_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mb_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.mb:~0 rows (대략적) 내보내기
DELETE FROM `mb`;
INSERT INTO `mb` (`mb_id`, `last_login_dt`, `mb_nm`, `password`, `regist_dt`, `sttus_code`) VALUES
	('user', '2021-10-08 23:02:05.000000', '유저', 'FenzETluBHgKCH2/Z3lGXA==', '2021-10-08 21:43:11.000000', NULL);

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

-- 테이블 데이터 scrobotdb.menu2:~4 rows (대략적) 내보내기
DELETE FROM `menu2`;
INSERT INTO `menu2` (`menu_id`, `menu_nm`, `prnts_menu_id`, `url_nm`, `scrin_id`, `menu_ordr_value`, `prjct_id`) VALUES
	('af40392d', '게시판 등록', 'ee5e7fa0', NULL, 's1', 0, 'c27cbd76'),
	('ee5e7fa0', '게시판 관리', '-', NULL, '', 2, 'c27cbd76'),
	('f529b1d5', '게시판 목록', 'ee5e7fa0', NULL, 's2', 1, 'c27cbd76');

-- 테이블 scrobotdb.prjct2 구조 내보내기
DROP TABLE IF EXISTS `prjct2`;
CREATE TABLE IF NOT EXISTS `prjct2` (
  `prjct_id` varchar(255) NOT NULL,
  `prjct_nm` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `regist_dt` datetime DEFAULT NULL,
  `updt_dt` datetime DEFAULT NULL,
  `prjct_cn` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`prjct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.prjct2:~1 rows (대략적) 내보내기
DELETE FROM `prjct2`;
INSERT INTO `prjct2` (`prjct_id`, `prjct_nm`, `user_id`, `regist_dt`, `updt_dt`, `prjct_cn`) VALUES
	('c27cbd76', '프로젝트 - 수정', 'user', '2021-09-28 21:45:16', '2021-09-28 22:04:23', '내용 - 수정');

-- 테이블 scrobotdb.prjct_trget_sys_mapng 구조 내보내기
DROP TABLE IF EXISTS `prjct_trget_sys_mapng`;
CREATE TABLE IF NOT EXISTS `prjct_trget_sys_mapng` (
  `prjct_trget_sys_mapng_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prjct_id` varchar(255) DEFAULT NULL,
  `trget_sys_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`prjct_trget_sys_mapng_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.prjct_trget_sys_mapng:~5 rows (대략적) 내보내기
DELETE FROM `prjct_trget_sys_mapng`;
INSERT INTO `prjct_trget_sys_mapng` (`prjct_trget_sys_mapng_id`, `prjct_id`, `trget_sys_id`) VALUES
	(2, '111503c4', '63a2de08'),
	(3, '70fdfa29', '63a2de08'),
	(4, '06f5045c', '63a2de08'),
	(5, '62c4474b', '63a2de08'),
	(6, '0ad84aba', '63a2de08'),
	(11, 'c27cbd76', '63a2de08');

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

-- 테이블 데이터 scrobotdb.scrin:~2 rows (대략적) 내보내기
DELETE FROM `scrin`;
INSERT INTO `scrin` (`scrin_id`, `scrin_nm`, `scrin_group_id`, `scrin_se_code`) VALUES
	('07c8c8c6', '게시판 수정', 'sg1', 'U'),
	('s1', '게시판 등록', 'sg1', 'C');

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
INSERT INTO `scrin_group` (`scrin_group_id`, `scrin_group_nm`, `prjct_id`, `eng_abrv_nm`) VALUES
	('sg1', '게시판', 'c27cbd76', 'bbs');

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

-- 테이블 데이터 scrobotdb.trget_sys:~0 rows (대략적) 내보내기
DELETE FROM `trget_sys`;
INSERT INTO `trget_sys` (`trget_sys_id`, `trget_sys_nm`, `db_ty_nm`, `db_driver_nm`, `db_url_nm`, `db_user_nm`, `db_password_nm`, `db_nm`) VALUES
	('63a2de08', '마이에스큐엘 서버', 'MySQL', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/scrobotdb_trget', 'root', 'password', 'scrobotdb_trget');

-- 테이블 scrobotdb.user 구조 내보내기
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` varchar(255) NOT NULL,
  `last_login_dt` datetime(6) DEFAULT NULL,
  `password` varchar(1000) DEFAULT NULL,
  `regist_dt` datetime(6) DEFAULT NULL,
  `sttus_code` varchar(255) DEFAULT NULL,
  `user_nm` varchar(255) DEFAULT NULL,
  `telno` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.user:~1 rows (대략적) 내보내기
DELETE FROM `user`;
INSERT INTO `user` (`user_id`, `last_login_dt`, `password`, `regist_dt`, `sttus_code`, `user_nm`, `telno`) VALUES
	('a', '2021-11-13 13:47:31.000000', 'gG6eOOxIlMaF0vTskaNiKQ==', '2021-11-13 13:47:11.000000', NULL, 'a', '000'),
	('user', '2021-11-06 20:35:03.000000', 'FenzETluBHgKCH2/Z3lGXA==', '2021-10-08 23:10:55.000000', NULL, '우저', NULL);

-- 테이블 scrobotdb.word_dicary 구조 내보내기
DROP TABLE IF EXISTS `word_dicary`;
CREATE TABLE IF NOT EXISTS `word_dicary` (
  `word_dicary_id` int(11) NOT NULL AUTO_INCREMENT,
  `cn` varchar(4000) DEFAULT NULL,
  `eng_abrv_nm` varchar(255) DEFAULT NULL,
  `regist_dt` datetime(6) DEFAULT NULL,
  `thema_relm_nm` varchar(255) DEFAULT NULL,
  `word_eng_nm` varchar(255) DEFAULT NULL,
  `word_nm` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`word_dicary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 scrobotdb.word_dicary:~0 rows (대략적) 내보내기
DELETE FROM `word_dicary`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
