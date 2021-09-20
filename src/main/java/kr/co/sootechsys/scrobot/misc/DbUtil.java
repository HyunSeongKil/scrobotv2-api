package kr.co.sootechsys.scrobot.misc;

import java.sql.SQLException;
import javax.persistence.Table;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbUtil {


  public static String getTableName(Class<?> entityClass) {
    return entityClass.getAnnotation(Table.class).name();
  }


  /**
   * 디비 인스턴스명 구하기
   * 
   * @param jdbcTemplate
   * @return
   * @throws SQLException
   */
  public static String getDbName(JdbcTemplate jdbcTemplate) throws SQLException {
    return jdbcTemplate.getDataSource().getConnection().getCatalog();
  }


  /**
   * 디비 제품명 구하기
   * 
   * @param jdbcTemplate
   * @return
   * @throws SQLException
   */
  public static String getDbProductName(JdbcTemplate jdbcTemplate) throws SQLException {
    return jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName();
  }
}
