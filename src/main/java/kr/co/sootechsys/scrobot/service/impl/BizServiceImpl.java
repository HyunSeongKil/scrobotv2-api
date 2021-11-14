package kr.co.sootechsys.scrobot.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.internal.Utils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import kr.co.sootechsys.scrobot.domain.CompnDto;
import kr.co.sootechsys.scrobot.domain.DbProduct;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.entity.Compn;
import kr.co.sootechsys.scrobot.entity.Menu;
import kr.co.sootechsys.scrobot.entity.Scrin;
import kr.co.sootechsys.scrobot.entity.ScrinGroup;
import kr.co.sootechsys.scrobot.entity.TrgetSys;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.service.BizService;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.DbDriverService;
import kr.co.sootechsys.scrobot.service.PrjctTrgetSysMapngService;
import kr.co.sootechsys.scrobot.service.TrgetSysService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BizServiceImpl implements BizService {

  private CompnService compnService;
  private PrjctTrgetSysMapngService prjctTrgetSysMapngService;
  private TrgetSysService trgetSysService;
  private DbDriverService dbDriverService;

  public BizServiceImpl(CompnService compnService, PrjctTrgetSysMapngService prjctTrgetSysMapngService,
      TrgetSysService trgetSysService, DbDriverService dbDriverService) {
    this.compnService = compnService;
    this.prjctTrgetSysMapngService = prjctTrgetSysMapngService;
    this.trgetSysService = trgetSysService;
    this.dbDriverService = dbDriverService;

  }

  @Override
  public void updt(String prjctId, String scrinId, Map<String, Object> map) throws SQLException {

    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return;
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      //
      String tableName = getTableName(jdbcTemplate, scrinId);
      if (null == tableName) {
        throw new RuntimeException("NULL TABLE_NAME. scrinId:" + scrinId);
      }

      // pk 값 검사
      if (Util.isEmpty(map.get(Util.getPkColmnName(tableName)))) {
        throw new RuntimeException("NULL PK Value " + map);
      }

      //
      String updateSql = createUpdateSql(trgetSysDto.getDbTyNm(), tableName, map);
      jdbcTemplate.execute(updateSql);

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }
  }

  @Override
  public void delete(String prjctId, String scrinId, String id) throws SQLException {
    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return;
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      //
      String tableName = getTableName(jdbcTemplate, scrinId);
      if (null == tableName) {
        throw new RuntimeException("NULL TABLE_NAME. scrinId:" + scrinId);
      }

      //
      String deleteSql = createDeleteSql(trgetSysDto.getDbTyNm(), tableName, id);
      jdbcTemplate.execute(deleteSql);

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }
  }

  @Override
  public void regist(String prjctId, String scrinId, Map<String, Object> map) throws SQLException {
    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return;
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      //
      String tableName = getTableName(jdbcTemplate, scrinId);
      if (null == tableName) {
        throw new RuntimeException("NULL TABLE_NAME. scrinId:" + scrinId);
      }

      //
      String insertSql = createInsertSql(trgetSysDto.getDbTyNm(), tableName, map);
      jdbcTemplate.execute(insertSql);

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }

  }

  @Override
  public Map<String, Object> findById(String prjctId, String scrinId, String id) throws SQLException {
    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return Map.of();
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      //
      String tableName = getTableName(jdbcTemplate, scrinId);
      if (null == tableName) {
        throw new RuntimeException("NULL TABLE_NAME. scrinId:" + scrinId);
      }

      //
      String selectSql = createSelectSql(trgetSysDto.getDbTyNm(), tableName, id);
      List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql);
      if (!Util.isEmpty(list)) {
        return list.get(0);
      }

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }

    return Map.of();

  }

  @Override
  public List<Map<String, Object>> findAll(String prjctId, String scrinId) throws SQLException {
    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return List.of();
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      //
      String tableName = getTableName(jdbcTemplate, scrinId);
      if (null == tableName) {
        throw new RuntimeException("NULL TABLE_NAME. scrinId:" + scrinId);
      }

      //
      String selectSql = createSelectSql(trgetSysDto.getDbTyNm(), tableName);
      return jdbcTemplate.queryForList(selectSql);

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }

  }

  /**
   * select sql문 생성
   * 
   * @param dbTyNm    db 종류
   * @param tableName 테이블명
   * @param id        (데이터)아이디
   * @return
   */
  private String createSelectSql(String dbTyNm, String tableName, String id) {
    String sql = createSelectSql(dbTyNm, tableName);
    sql += " WHERE " + Util.getPkColmnName(tableName) + " = '" + id + "'"; // pk

    return sql;
  }

  /**
   * select sql문 생성
   * 
   * @param dbTyNm    db 종류
   * @param tableName 테이블명
   * @param id        (데이터)아이디
   * @return
   */
  private String createSelectSql(String dbTyNm, String tableName) {
    StringBuffer sb = new StringBuffer();

    //
    sb.append(" SELECT * ");
    sb.append(" FROM " + tableName);

    return sb.toString();
  }

  /**
   * delete sql문 생성
   * 
   * @param dbTyNm    db 종류
   * @param tableName 테이블명
   * @param id        (데이터)아이디
   * @return
   */
  private String createDeleteSql(String dbTyNm, String tableName, String id) {
    StringBuffer sb = new StringBuffer();

    //
    sb.append(" DELETE FROM " + tableName);
    sb.append(" WHERE " + Util.getPkColmnName(tableName) + " = '" + id + "'"); // pk

    return sb.toString();
  }

  /**
   * insert sql문 생성
   * 
   * @param dbTyNm    db 종류
   * @param tableName 테이블명
   * @param map       데이터
   * @return
   */
  private String createInsertSql(String dbTyNm, String tableName, Map<String, Object> map) {
    StringBuffer sb = new StringBuffer();

    //
    sb.append(" INSERT INTO " + tableName + " (");
    sb.append(" " + Util.getPkColmnName(tableName)); // pk

    for (Entry<String, Object> entry : map.entrySet()) {
      sb.append(" , " + entry.getKey());
    }
    sb.append(" )");

    //
    sb.append(" VALUES (");
    sb.append(" '" + Util.getShortUuid() + "' "); // pk
    for (Entry<String, Object> entry : map.entrySet()) {
      sb.append(" , " + " '" + entry.getValue() + "'");
    }
    sb.append(" )");

    return sb.toString();
  }

  /**
   * update sql문 생성
   * 
   * @param dbTyNm    db 종류
   * @param tableName 테이블명
   * @param map       데이터
   * @return
   */
  private String createUpdateSql(String dbTyNm, String tableName, Map<String, Object> map) {
    StringBuffer sb = new StringBuffer();
    String pkColmnName = Util.getPkColmnName(tableName);

    //
    boolean isFirst = true;
    sb.append(" UPDATE " + tableName + " SET ");
    for (Entry<String, Object> entry : map.entrySet()) {
      if (isFirst) {
        sb.append("");
        isFirst = false;
      } else {
        sb.append(" , ");
      }
      sb.append(entry.getKey() + " = '" + entry.getValue() + "'");
    }
    sb.append(" WHERE " + pkColmnName + " = '" + map.get(pkColmnName) + "'"); // pk

    //
    return sb.toString();
  }

  @Override
  public void xxx(String prjctId, String scrinId) {
    PrjctTrgetSysMapngDto prjctTrgetSysMapngDto = prjctTrgetSysMapngService.findByPrjctId(prjctId);

    // 대상 시스템에 접속해서 컴포넌트 목록 조회 by scrinId
    // 대상 시스템에 접속해서 화면그룹 조회 by scrinId

  }

  @Override
  public List<Map<String, Object>> findAllScrins(String prjctId, String scrinGroupId) throws SQLException {
    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return List.of();
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      String sql = createSelectSql(Scrin.class, "AND scrin_group_id='" + scrinGroupId + "'");

      return jdbcTemplate.queryForList(sql);

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }

  }

  @Override
  public List<Map<String, Object>> findAllMenus(String prjctId) throws SQLException {
    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return List.of();
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      String sql = createSelectSql(Menu.class, "AND prjct_id='" + prjctId + "'");

      return jdbcTemplate.queryForList(sql);

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }

  }

  @Override
  public Map<String, Object> findScrin(String prjctId, String scrinId) throws SQLException {
    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return Map.of();
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      String sql = createSelectSql(Scrin.class, "AND scrin_id='" + scrinId + "'");

      List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
      if (null != list && 0 != list.size()) {
        return list.get(0);
      } else {
        return Map.of();
      }

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }
  }

  @Override
  public Map<String, Object> findScrinGroup(String prjctId, String scrinId) throws SQLException {
    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return Map.of();
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      //
      String sql = createSelectSql(Scrin.class, "AND scrin_id='" + scrinId + "'");
      List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
      if (Util.isEmpty(list)) {
        return Map.of();
      }

      //
      String scrinGroupId = "" + list.get(0).get("scrin_group_id");
      sql = createSelectSql(ScrinGroup.class, " AND scrin_group_id = '" + scrinGroupId + "' ");
      list = jdbcTemplate.queryForList(sql);
      if (!Util.isEmpty(list)) {
        return list.get(0);
      }

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }

    return Map.of();
  }

  @Override
  public List<Map<String, Object>> findAllCompns(String prjctId, String scrinId) throws SQLException {
    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return List.of();
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      String sql = createSelectSql(Compn.class, "AND scrin_id='" + scrinId + "'");

      return jdbcTemplate.queryForList(sql);

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }
  }

  /**
   * datasource 생성
   * 
   * @param trgetSysId
   * @return
   */
  BasicDataSource createDataSource(TrgetSysDto dto) {

    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName(dbDriverService.getDbDriverNm(dto.getDbTyNm()));
    ds.setUrl(dto.getDbUrlNm());
    ds.setUsername(dto.getDbUserNm());
    ds.setPassword(dto.getDbPasswordNm());

    return ds;
  }

  String getTableName(Class<?> entityClass) {
    return entityClass.getAnnotation(Table.class).name();
  }

  /**
   * key:필드명, value:컬럼명
   * 
   * @param entitycClass
   * @return
   */
  Map<String, String> getFieldColumnMap(Class<?> entitycClass) {
    Map<String, String> map = new HashMap<>();

    Field[] fields = entitycClass.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      Field f = fields[i];
      if (null != f.getAnnotation(Column.class)) {
        // key:필드명, value:컬럼명
        map.put(f.getName(), f.getAnnotation(Column.class).name());
      }
    }

    return map;

  }

  /**
   * select 문 생성
   * 
   * @param entityClass
   * @param where
   * @return
   */
  String createSelectSql(Class<?> entityClass, String where) {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT 1 AS dummy ");

    Map<String, String> fieldColumnMap = getFieldColumnMap(entityClass);

    for (Entry<String, String> entry : fieldColumnMap.entrySet()) {
      sb.append(", " + entry.getValue());
    }

    sb.append(" FROM " + getTableName(entityClass));
    sb.append(" WHERE 1=1 ");
    sb.append(where);

    return sb.toString();
  }

  /**
   * 프로젝트아이디로 대상시스템 정보 조회
   * 
   * @param prjctId 프로젝트아이디
   * @return
   */
  TrgetSysDto getTrgetSysDto(String prjctId) {
    PrjctTrgetSysMapngDto prjctTrgetSysMapngDto = prjctTrgetSysMapngService.findByPrjctId(prjctId);
    if (null == prjctTrgetSysMapngDto) {
      return null;
    }

    return trgetSysService.findById(prjctTrgetSysMapngDto.getTrgetSysId());
  }

  /**
   * 화면아이디로 테이블명 구하기
   * 
   * @param jdbcTemplate
   * @param scrinId      화면아이디
   * @return 테이블명
   */
  String getTableName(JdbcTemplate jdbcTemplate, String scrinId) {
    String scrinGroupId = getScrinGroupIdByScrinId(jdbcTemplate, scrinId);
    if (null == scrinGroupId) {
      return null;
    }

    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT eng_abrv_nm "); // table name
    sb.append(" FROM " + getTableName(ScrinGroup.class));
    sb.append(" WHERE scrin_group_id = '" + scrinGroupId + "'");

    List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
    if (Util.isEmpty(list)) {
      return null;
    }

    return "" + list.get(0).get("eng_abrv_nm");
  }

  /**
   * 화면아이디로 화면그룹아이디 구하기
   * 
   * @param jdbcTemplate
   * @param scrinId      화면아이디
   * @return 화면그룹아이디
   */
  String getScrinGroupIdByScrinId(JdbcTemplate jdbcTemplate, String scrinId) {
    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT scrin_group_id ");
    sb.append(" FROM " + getTableName(Scrin.class));
    sb.append(" WHERE scrin_id = '" + scrinId + "'");

    List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
    if (Util.isEmpty(list)) {
      return null;
    }

    return "" + list.get(0).get("scrin_group_id");
  }

  /**
   * 해당 테이블의 컬럼 목록 구하기
   * 
   * @param jdbcTemplate
   * @param dbTyNm
   * @param tableName
   * @return
   */
  Set<String> getColmns(JdbcTemplate jdbcTemplate, String dbTyNm, String tableName) {
    StringBuffer sb = new StringBuffer();

    switch (DbProduct.valueOf(dbTyNm)) {
    case MySQL:
    case MariaDB:
      sb.append(" SELECT TABLE_NAME, COLUMN_NAME AS column_name");
      sb.append(" FROM INFORMATION.SCHEMA");
      sb.append(" WHERE TALBE_NAME = '" + tableName + "'");
      break;

    default:
      throw new RuntimeException("NOT IMPL " + dbTyNm);
    }

    List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
    if (Util.isEmpty(list)) {
      return null;
    }

    Set<String> set = new HashSet<>();
    list.forEach(map -> {
      set.add("" + map.get("column_name"));
    });

    return set;

  }

  @Override
  public List<Map<String, Object>> findAllMeta(String prjctId, String scrinId) throws SQLException {
    TrgetSysDto trgetSysDto = getTrgetSysDto(prjctId);
    if (null == trgetSysDto) {
      return List.of();
    }

    BasicDataSource ds = null;

    try {
      ds = createDataSource(trgetSysDto);
      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      String tableName = getTableName(jdbcTemplate, scrinId);
      if (null == tableName) {
        throw new RuntimeException("NULL TABLE_NAME. scrinId:" + scrinId);
      }

      return findAllMeta(jdbcTemplate, trgetSysDto, tableName);

    } catch (Exception e) {
      throw e;
    } finally {
      if (null != ds) {
        ds.close();
      }
    }

  }

  /**
   * 테이블의 메타정보 조회. key:table_name, column_name, data_type, column_comment
   * 
   * @param jdbcTemplate
   * @param dbTyNm       디비타입
   * @param tableName    테이블명
   * @return
   * @throws SQLException
   */
  List<Map<String, Object>> findAllMeta(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName)
      throws SQLException {
    StringBuffer sb = new StringBuffer();

    switch (DbProduct.valueOf(trgetSysDto.getDbTyNm())) {
    case MySQL:
    case MariaDB:
      sb.append(" SELECT TABLE_NAME AS table_name");
      sb.append("   , COLUMN_NAME AS column_name");
      sb.append("   , DATA_TYPE AS data_type");
      sb.append("   , COLUMN_COMMENT AS column_comment");
      sb.append(" FROM information_schema.columns");
      sb.append(" WHERE TABLE_NAME = '" + tableName + "'");
      sb.append(" AND TABLE_SCHEMA = '" + trgetSysDto.getDbNm() + "'");

      break;

    default:
      throw new RuntimeException("NO IMPL " + trgetSysDto);
    }

    return jdbcTemplate.queryForList(sb.toString());
  }

}
