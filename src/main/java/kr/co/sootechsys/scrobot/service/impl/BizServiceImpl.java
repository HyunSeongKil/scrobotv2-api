package kr.co.sootechsys.scrobot.service.impl;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Table;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import kr.co.sootechsys.scrobot.domain.CompnDto;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.entity.Compn;
import kr.co.sootechsys.scrobot.entity.Menu;
import kr.co.sootechsys.scrobot.entity.Scrin;
import kr.co.sootechsys.scrobot.service.BizService;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.PrjctTrgetSysMapngService;
import kr.co.sootechsys.scrobot.service.TrgetSysService;

@Service
public class BizServiceImpl implements BizService {

  private CompnService compnService;
  private PrjctTrgetSysMapngService prjctTrgetSysMapngService;
  private TrgetSysService trgetSysService;


  public BizServiceImpl(CompnService compnService, PrjctTrgetSysMapngService prjctTrgetSysMapngService, TrgetSysService trgetSysService) {
    this.compnService = compnService;
    this.prjctTrgetSysMapngService = prjctTrgetSysMapngService;
    this.trgetSysService = trgetSysService;
  }

  @Override
  public void xxx(String prjctId, String scrinId) {
    PrjctTrgetSysMapngDto prjctTrgetSysMapngDto = prjctTrgetSysMapngService.findByPrjctId(prjctId);

    // 대상 시스템에 접속해서 컴포넌트 목록 조회 by scrinId
    // 대상 시스템에 접속해서 화면그룹 조회 by scrinId


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
    ds.setDriverClassName(dto.getDbDriverNm());
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

    for (Map.Entry entry : fieldColumnMap.entrySet()) {
      sb.append(", " + entry.getValue() + " AS " + entry.getKey());
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



}
