package kr.co.sootechsys.scrobot.service;

import java.sql.SQLException;
import java.util.Map;

public interface DeployService {
  public static final String TABLE_NAME_PRJCT = "prjct";
  public static final String TABLE_NAME_SCRIN_GROUP = "scrin_group";
  public static final String TABLE_NAME_MENU = "menu";
  public static final String TABLE_NAME_SCRIN = "scrin";
  public static final String TABLE_NAME_COMPN = "compn";


  /**
   * 
   * @param prjctId 프로젝트 아이디
   * @param trgetSysId 대상시스템 아이디
   * @return
   * @throws SQLException
   */
  Map<String, Long> deploy(String prjctId, String trgetSysId) throws SQLException;
}
