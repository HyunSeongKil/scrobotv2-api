package kr.co.sootechsys.scrobot.service;

import java.sql.SQLException;
import java.util.Map;
import kr.co.sootechsys.scrobot.domain.DeployDto;

/**
 * 배포
 */
public interface DeployService {

  /**
   * 배포
   * 
   * @param dto 값
   * @return
   * @throws SQLException
   */
  Map<String, Long> deploy(DeployDto dto) throws SQLException;

  Map<String, Long> deploy(String prjctId) throws SQLException;
}
