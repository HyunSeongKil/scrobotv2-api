package kr.co.sootechsys.scrobot.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import kr.co.sootechsys.scrobot.domain.MenuDto;

public interface BizService {

  void xxx(String prjctId, String scrinId);


  /**
   * 메뉴목록 조회
   * 
   * @param prjctId 프로젝트아이디
   * @return
   * @throws SQLException
   */
  List<Map<String, Object>> findAllMenus(String prjctId) throws SQLException;


  /**
   * 화면 조회
   * 
   * @param prjctId 프로젝트아이디
   * @param scrinId 화면아이디
   * @return
   * @throws SQLException
   */
  Map<String, Object> findScrin(String prjctId, String scrinId) throws SQLException;


  /**
   * 콤포넌트 목록 조회
   * 
   * @param prjctId 프로젝트아이디
   * @param scrinId 화면아이디
   * @return
   * @throws SQLException
   */
  List<Map<String, Object>> findAllCompns(String prjctId, String scrinId) throws SQLException;


  /**
   * 등록
   * 
   * @param prjctId 프로젝트아이디
   * @param scrinId 화면아이디
   * @param map 값
   * @throws SQLException
   */
  void regist(String prjctId, String scrinId, Map<String, Object> map) throws SQLException;


  /**
   * 삭제
   * 
   * @param prjctId 프로젝트아이디
   * @param scrinId 화면아이디
   * @param id (데이터)아이디
   * @throws SQLException
   */
  void delete(String prjctId, String scrinId, String id) throws SQLException;


  /**
   * 수정
   * 
   * @param prjctId 프로젝트아이디
   * @param scrinId 화면아이디
   * @param map 값
   * @throws SQLException
   */
  void updt(String prjctId, String scrinId, Map<String, Object> map) throws SQLException;

  /**
   * 상세조회
   * 
   * @param prjctId 프로젝트아이디
   * @param scrinId 화면아이디
   * @param id (데이터)아이디
   * @return
   * @throws SQLException
   */
  Map<String, Object> findById(String prjctId, String scrinId, String id) throws SQLException;


  /**
   * 목록 조회
   * 
   * @param prjctId 프로젝트아이디
   * @param scrinId 화면아이디
   * @return
   * @throws SQLException
   */
  List<Map<String, Object>> findAll(String prjctId, String scrinId) throws SQLException;


  /**
   * 화면그룹 상세조회
   * 
   * @param prjctId 프로젝트아이디
   * @param scrinId 화면아이디
   * @return
   * @throws SQLException
   */
  Map<String, Object> findScrinGroup(String prjctId, String scrinId) throws SQLException;


  /**
   * 화면그룹아이디로 화면 목록 조회
   * 
   * @param prjctId 프로젝트아이디
   * @param scrinGroupId 화면그룹아이디
   * @return
   * @throws SQLException
   */
  List<Map<String, Object>> findAllScrins(String prjctId, String scrinGroupId) throws SQLException;

}
