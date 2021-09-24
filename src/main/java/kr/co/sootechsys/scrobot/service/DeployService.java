package kr.co.sootechsys.scrobot.service;

import java.sql.SQLException;
import java.util.Map;


/**
 * 배포
 */
public interface DeployService {


  /**
   * 배포
   * 
   * @param prjctId 프로젝트 아이디
   * @param trgetSysId 대상시스템 아이디
   * @return
   * @throws SQLException
   */
  Map<String, Long> deploy(String prjctId, String trgetSysId) throws SQLException;
}
