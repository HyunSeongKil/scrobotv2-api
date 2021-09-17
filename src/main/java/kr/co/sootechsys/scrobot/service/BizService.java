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

}
