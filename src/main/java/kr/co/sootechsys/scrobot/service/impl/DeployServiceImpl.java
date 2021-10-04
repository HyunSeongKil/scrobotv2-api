package kr.co.sootechsys.scrobot.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StopWatch;
import ch.qos.logback.core.db.dialect.DBUtil;
import kr.co.sootechsys.scrobot.domain.CmmnCodeDto;
import kr.co.sootechsys.scrobot.domain.CompnDto;
import kr.co.sootechsys.scrobot.domain.DbProduct;
import kr.co.sootechsys.scrobot.domain.DeployDto;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.domain.PrjctDto;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;
import kr.co.sootechsys.scrobot.domain.ScrinDto;
import kr.co.sootechsys.scrobot.domain.ScrinGroupDto;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.entity.CmmnCode;
import kr.co.sootechsys.scrobot.entity.Compn;
import kr.co.sootechsys.scrobot.entity.Menu;
import kr.co.sootechsys.scrobot.entity.Prjct;
import kr.co.sootechsys.scrobot.entity.PrjctTrgetSysMapng;
import kr.co.sootechsys.scrobot.entity.Scrin;
import kr.co.sootechsys.scrobot.entity.ScrinGroup;
import kr.co.sootechsys.scrobot.entity.TrgetSys;
import kr.co.sootechsys.scrobot.misc.DbUtil;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.service.CmmnCodeService;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.DeployService;
import kr.co.sootechsys.scrobot.service.MenuService;
import kr.co.sootechsys.scrobot.service.PrjctService;
import kr.co.sootechsys.scrobot.service.PrjctTrgetSysMapngService;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;
import kr.co.sootechsys.scrobot.service.ScrinService;
import kr.co.sootechsys.scrobot.service.TrgetSysService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeployServiceImpl implements DeployService {

  private PrjctService prjctService;
  private MenuService menuService;
  private ScrinGroupService scrinGroupService;
  private ScrinService scrinService;
  private CompnService compnService;
  private TrgetSysService trgetSysService;
  private PrjctTrgetSysMapngService prjctTrgetSysMapngService;
  private CmmnCodeService cmmnCodeService;

  public DeployServiceImpl(CmmnCodeService cmmnCodeService, PrjctService prjctService, MenuService menuService, ScrinGroupService scrinGroupService, ScrinService scrinService,
      CompnService compnService, TrgetSysService trgetSysService, PrjctTrgetSysMapngService prjctTrgetSysMapngService) {
    this.cmmnCodeService = cmmnCodeService;
    this.prjctService = prjctService;
    this.menuService = menuService;
    this.scrinGroupService = scrinGroupService;
    this.scrinService = scrinService;
    this.compnService = compnService;
    this.trgetSysService = trgetSysService;
    this.prjctTrgetSysMapngService = prjctTrgetSysMapngService;
  }


  @Override
  @Transactional
  public Map<String, Long> deploy(DeployDto dto) throws SQLException {
    prjctTrgetSysMapngService.regist(dto.getPrjctId(), dto.getTrgetSysId());

    BasicDataSource ds = null;
    TrgetSysDto trgetSysDto = trgetSysService.findById(dto.getTrgetSysId());

    try {
      ds = createDataSource(trgetSysDto);

      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      trgetSysDto.setDbNm(DbUtil.getDbName(jdbcTemplate));
      trgetSysDto.setDbTyNm(DbUtil.getDbProductName(jdbcTemplate));


      executeBasicDdl(jdbcTemplate, trgetSysDto, CmmnCode.class, Compn.class, Menu.class, Prjct.class, Scrin.class, ScrinGroup.class, TrgetSys.class, PrjctTrgetSysMapng.class);

      //
      deleteOldData(jdbcTemplate, dto.getPrjctId());

      //
      insertNewData(jdbcTemplate, dto.getPrjctId());

      //
      return executeBizDdl(jdbcTemplate, trgetSysDto, dto.getPrjctId());

    } catch (Exception e) {
      // if (null != ds) {
      // ds.getConnection().rollback();
      // }

      throw e;
    } finally {
      if (null != ds) {
        // ds.getConnection().commit();
        ds.close();
      }

    }

  }


  /**
   * 기본 테이블 생성/수정
   * 
   * @param jdbcTemplate
   * @param trgetSysDto
   * @param entityClass
   * @throws SQLException
   */
  private void executeBasicDdl(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, Class<?> entityClass) throws SQLException {
    Table t = entityClass.getAnnotation(Table.class);
    String tableName = (null == t) ? "" : t.name();



    String pkColmnName = "";
    List<String> colmns = new ArrayList<>();
    List<Class<?>> classes = new ArrayList<>();

    Field[] fields = entityClass.getDeclaredFields();
    for (Field f : fields) {
      f.setAccessible(true);
      Column c = f.getAnnotation(Column.class);
      if (null == c) {
        continue;
      }

      if (colmns.contains(c.name())) {
        continue;
      }

      colmns.add(c.name());
      classes.add(f.getType());

      if (null != f.getAnnotation(Id.class)) {
        pkColmnName = c.name();
      }
    }

    boolean b = existsTable(jdbcTemplate, tableName);
    if (!b) {
      createTable(jdbcTemplate, trgetSysDto, tableName, pkColmnName, colmns, classes);
    } else {
      alterTable(jdbcTemplate, trgetSysDto, tableName, colmns, classes);
    }
  }


  /**
   * 기본 테이블 생성/수정
   * 
   * @param jdbcTemplate
   * @param trgetSysDto
   * @param entityClasses
   * @throws SQLException
   */
  private void executeBasicDdl(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, Class<?>... entityClasses) throws SQLException {

    for (Class<?> clz : entityClasses) {
      executeBasicDdl(jdbcTemplate, trgetSysDto, clz);
    }


  }


  @Transactional
  void insertNewData(JdbcTemplate jdbcTemplate, String prjctId) {
    List<CmmnCodeDto> cmmnCodeDtos = cmmnCodeService.findAll();
    PrjctDto prjctDto = prjctService.findById(prjctId);
    List<ScrinGroupDto> scrinGroupDtos = getScrinGroups(prjctId);
    List<MenuDto> menuDtos = getMenus(prjctId);
    List<ScrinDto> scrinDtos = getScrins(scrinGroupDtos);
    List<CompnDto> compnDtos = getCompns(scrinDtos);
    PrjctTrgetSysMapngDto prjctTrgetSysMapng = prjctTrgetSysMapngService.findByPrjctId(prjctId);


    insertCmmnCode(jdbcTemplate, cmmnCodeDtos);
    insertPrjct(jdbcTemplate, prjctDto);
    insertMenu(jdbcTemplate, menuDtos);
    insertScrinGroup(jdbcTemplate, scrinGroupDtos);
    insertScrin(jdbcTemplate, scrinDtos);
    insertCompn(jdbcTemplate, compnDtos);
    insertPrjctTrgetSysMapng(jdbcTemplate, prjctTrgetSysMapng);

  }



  /**
   * insert 컴포넌트 데이터 to 대상 시스템
   * 
   * @param jdbcTemplate
   * @param compnDtos
   */
  private void insertCompn(JdbcTemplate jdbcTemplate, List<CompnDto> compnDtos) {
    if (null == compnDtos) {
      return;
    }

    compnDtos.forEach(dto -> {
      StringBuffer sb = new StringBuffer();

      sb.append(" INSERT INTO " + DbUtil.getTableName(Compn.class) + "(");
      sb.append("   compn_id");
      sb.append("   , compn_nm");
      sb.append("   , compn_cn");
      sb.append("   , eng_abrv_nm");
      sb.append("   , hngl_abrv_nm");
      sb.append("   , scrin_id");
      sb.append("   , compn_se_code");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append("   '" + dto.getCompnId() + "' ");
      sb.append("   , '" + dto.getCompnNm() + "' ");
      sb.append("   , '" + dto.getCompnCn() + "' ");
      sb.append("   , '" + dto.getEngAbrvNm() + "' ");
      sb.append("   , '" + dto.getHnglAbrvNm() + "' ");
      sb.append("   , '" + dto.getScrinId() + "' ");
      sb.append("   , '" + dto.getCompnSeCode() + "' ");
      sb.append(" )");

      jdbcTemplate.execute(sb.toString());
    });
  }


  /**
   * insert 화면 데이터 to 대상 시스템
   * 
   * @param jdbcTemplate
   * @param scrinDtos
   */
  private void insertScrin(JdbcTemplate jdbcTemplate, List<ScrinDto> scrinDtos) {
    if (null == scrinDtos) {
      return;
    }

    scrinDtos.forEach(dto -> {
      StringBuffer sb = new StringBuffer();

      sb.append(" INSERT INTO " + DbUtil.getTableName(Scrin.class) + "(");
      sb.append("   scrin_id");
      sb.append("   , scrin_nm");
      sb.append("   , scrin_se_code");
      sb.append("   , scrin_group_id");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append("   '" + dto.getScrinId() + "' ");
      sb.append("   , '" + dto.getScrinNm() + "' ");
      sb.append("   , '" + dto.getScrinSeCode() + "' ");
      sb.append("   , '" + dto.getScrinGroupId() + "' ");
      sb.append(" )");

      jdbcTemplate.execute(sb.toString());
    });
  }


  /**
   * insert 메뉴 데이터 to 대상 시스템
   * 
   * @param jdbcTemplate
   * @param menuDtos
   */
  private void insertMenu(JdbcTemplate jdbcTemplate, List<MenuDto> menuDtos) {
    if (null == menuDtos) {
      return;
    }

    menuDtos.forEach(dto -> {
      StringBuffer sb = new StringBuffer();

      sb.append(" INSERT INTO " + DbUtil.getTableName(Menu.class) + "(");
      sb.append("   menu_id");
      sb.append("   , menu_nm");
      sb.append("   , prjct_id");
      sb.append("   , prnts_menu_id");
      sb.append("   , url_nm");
      sb.append("   , scrin_id");
      sb.append("   , menu_ordr_value");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append("   '" + dto.getMenuId() + "' ");
      sb.append("   , '" + dto.getMenuNm() + "' ");
      sb.append("   , '" + dto.getPrjctId() + "' ");
      sb.append("   , '" + dto.getPrntsMenuId() + "' ");
      sb.append("   , '" + dto.getUrlNm() + "' ");
      sb.append("   , '" + dto.getScrinId() + "' ");
      sb.append("   , '" + dto.getMenuOrdrValue() + "' ");
      sb.append(" )");

      jdbcTemplate.execute(sb.toString());
    });
  }

  /**
   * insert 화면그룹 데이터 to 대상 시스템
   * 
   * @param jdbcTemplate
   * @param scrinGroupDtos
   */
  private void insertScrinGroup(JdbcTemplate jdbcTemplate, List<ScrinGroupDto> scrinGroupDtos) {
    if (null == scrinGroupDtos) {
      return;
    }

    scrinGroupDtos.forEach(dto -> {
      StringBuffer sb = new StringBuffer();

      sb.append(" INSERT INTO " + DbUtil.getTableName(ScrinGroup.class) + "(");
      sb.append("   scrin_group_id");
      sb.append("   , scrin_group_nm");
      sb.append("   , eng_abrv_nm");
      sb.append("   , prjct_id");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append("   '" + dto.getScrinGroupId() + "' ");
      sb.append("   , '" + dto.getScrinGroupNm() + "' ");
      sb.append("   , '" + dto.getEngAbrvNm() + "' ");
      sb.append("   , '" + dto.getPrjctId() + "' ");
      sb.append(" )");

      jdbcTemplate.execute(sb.toString());
    });
  }


  /**
   * 프로젝트-대상시스템 등록
   * 
   * @param jdbcTemplate
   * @param prjctTrgetSysMapngDto
   */
  private void insertPrjctTrgetSysMapng(JdbcTemplate jdbcTemplate, PrjctTrgetSysMapngDto dto) {
    StringBuffer sb = new StringBuffer();

    sb.append(" INSERT INTO " + DbUtil.getTableName(PrjctTrgetSysMapng.class) + "(");
    sb.append("   prjct_trget_sys_mapng_id");
    sb.append("   , prjct_id");
    sb.append("   , trget_sys_id");
    sb.append(" )");
    sb.append(" VALUES (");
    sb.append("   '" + dto.getPrjctTrgetSysMapngId() + "' ");
    sb.append("   , '" + dto.getPrjctId() + "' ");
    sb.append("   , '" + dto.getTrgetSysId() + "' ");
    sb.append(" )");

    jdbcTemplate.execute(sb.toString());

  }



  /**
   * 공통코드 등록
   * 
   * @param jdbcTemplate
   * @param dtos
   */
  private void insertCmmnCode(JdbcTemplate jdbcTemplate, List<CmmnCodeDto> dtos) {
    StringBuffer sb = new StringBuffer();

    dtos.forEach(dto -> {
      sb.setLength(0);

      sb.append(" INSERT INTO " + DbUtil.getTableName(CmmnCode.class) + " (");
      sb.append("   cmmn_code_id");
      sb.append("   , cmmn_code");
      sb.append("   , cmmn_code_nm");
      sb.append("   , prnts_cmmn_code");
      sb.append("   , use_at");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append(" " + dto.getCmmnCodeId() + " ");
      sb.append("   ,'" + dto.getCmmnCode() + "' ");
      sb.append("   ,'" + dto.getCmmnCodeNm() + "' ");
      sb.append("   ,'" + dto.getPrntsCmmnCode() + "' ");
      sb.append("   ,'" + dto.getUseAt() + "' ");
      sb.append(" )");

      jdbcTemplate.execute(sb.toString());
    });
  }


  /**
   * insert 프로젝트 데이터 to 대상 시스템
   * 
   * @param jdbcTemplate
   * @param prjctDto
   */
  private void insertPrjct(JdbcTemplate jdbcTemplate, PrjctDto prjctDto) {
    StringBuffer sb = new StringBuffer();

    sb.append(" INSERT INTO " + DbUtil.getTableName(Prjct.class) + "(");
    sb.append("   prjct_id");
    sb.append("   , prjct_nm");
    sb.append("   , user_id");
    sb.append(" )");
    sb.append(" VALUES (");
    sb.append("   '" + prjctDto.getPrjctId() + "' ");
    sb.append("   , '" + prjctDto.getPrjctNm() + "' ");
    sb.append("   , '" + prjctDto.getUserId() + "' ");
    sb.append(" )");

    jdbcTemplate.execute(sb.toString());
  }


  /**
   * 콤포넌트 목록 조회
   * 
   * @param scrinDtos
   * @return
   */
  private List<CompnDto> getCompns(List<ScrinDto> scrinDtos) {
    List<CompnDto> dtos = new ArrayList<>();

    if (null == scrinDtos) {
      return dtos;
    }

    scrinDtos.forEach(scrinDto -> {
      dtos.addAll(compnService.findAllByScrinId(scrinDto.getScrinId()));
    });

    return dtos;
  }


  /**
   * 화면 목록 조회
   * 
   * @param scrinGroupDtos
   * @return
   */
  private List<ScrinDto> getScrins(List<ScrinGroupDto> scrinGroupDtos) {
    List<ScrinDto> dtos = new ArrayList<>();

    if (null == scrinGroupDtos) {
      return dtos;
    }

    scrinGroupDtos.forEach(scrinGroupDto -> {
      dtos.addAll(scrinService.findAllByScrinGroupId(scrinGroupDto.getScrinGroupId()));
    });


    return dtos;
  }



  /**
   * 메뉴 목록 조회
   * 
   * @param prjctId
   * @return
   */
  private List<MenuDto> getMenus(String prjctId) {
    return menuService.findAllByPrjctId(prjctId);
  }


  /**
   * 화면 그룹 목록 조회
   * 
   * @param prjctId
   * @return
   */
  private List<ScrinGroupDto> getScrinGroups(String prjctId) {
    return scrinGroupService.findAllByPrjctId(prjctId);
  }


  @Transactional
  void deleteOldData(JdbcTemplate jdbcTemplate, String prjctId) {
    // 공통코드
    List<Map<String, Object>> cmmnCodes = jdbcTemplate.queryForList("SELECT * FROM " + DbUtil.getTableName(CmmnCode.class));

    // 프로젝트
    List<Map<String, Object>> prjcts = jdbcTemplate.queryForList("SELECT * FROM " + DbUtil.getTableName(Prjct.class) + " WHERE prjct_id = '" + prjctId + "'");
    if (null == prjcts) {
      return;
    }

    // 메뉴
    List<Map<String, Object>> menus = jdbcTemplate.queryForList("SELECT menu_id, prjct_id FROM " + DbUtil.getTableName(Menu.class) + " WHERE prjct_id = '" + prjctId + "'");

    // 화면그룹
    List<Map<String, Object>> scrinGroups = jdbcTemplate.queryForList("SELECT scrin_group_id, prjct_id FROM " + DbUtil.getTableName(ScrinGroup.class) + " WHERE prjct_id = '" + prjctId + "'");

    // 화면
    List<Map<String, Object>> scrins = getScrins(jdbcTemplate, scrinGroups);

    // 컴포넌트
    List<Map<String, Object>> compns = getCompns(jdbcTemplate, scrins);


    deleteCompn(jdbcTemplate, compns);
    deleteScrin(jdbcTemplate, scrins);
    deleteScrinGroup(jdbcTemplate, scrinGroups);
    deleteMenu(jdbcTemplate, menus);
    deletePrjctTrgetSysMapng(jdbcTemplate, prjctId);
    deletePrjct(jdbcTemplate, prjctId);
    deleteCmmnCode(jdbcTemplate, cmmnCodes);

  }


  /**
   * 프로젝트-대상시스템 매핑 데이터 삭제
   * 
   * @param jdbcTemplate
   * @param prjctId
   */
  @Transactional
  void deletePrjctTrgetSysMapng(JdbcTemplate jdbcTemplate, String prjctId) {
    if (null == prjctId) {
      return;
    }

    jdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(PrjctTrgetSysMapng.class) + " WHERE prjct_id = '" + prjctId + "' ");
  }



  /**
   * 공통코드 데이터 삭제
   * 
   * @param jdbcTemplate
   * @param list
   */
  @Transactional
  void deleteCmmnCode(JdbcTemplate jdbcTemplate, List<Map<String, Object>> list) {
    jdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(CmmnCode.class));
  }


  /**
   * 프로젝트 삭제
   * 
   * @param jdbcTemplate
   * @param scrinGroups
   */
  @Transactional
  void deletePrjct(JdbcTemplate jdbcTemplate, String prjctId) {
    if (null == prjctId) {
      return;
    }

    jdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(Prjct.class) + " WHERE prjct_id = '" + prjctId + "' ");
  }



  /**
   * 메뉴 삭제
   * 
   * @param jdbcTemplate
   * @param menus
   */
  @Transactional
  void deleteMenu(JdbcTemplate jdbcTemplate, List<Map<String, Object>> menus) {
    if (null == menus) {
      return;
    }

    menus.forEach(map -> {
      jdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(Menu.class) + " WHERE menu_id = '" + map.get("menu_id") + "' ");
    });
  }


  /**
   * 화면그룹 삭제
   * 
   * @param jdbcTemplate
   * @param scrinGroups
   */
  @Transactional
  void deleteScrinGroup(JdbcTemplate jdbcTemplate, List<Map<String, Object>> scrinGroups) {
    if (null == scrinGroups) {
      return;
    }

    scrinGroups.forEach(map -> {
      jdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(ScrinGroup.class) + " WHERE scrin_group_id = '" + map.get("scrin_group_id") + "' ");
    });
  }


  /**
   * 화면 삭제
   * 
   * @param jdbcTemplate
   * @param scrins
   */
  @Transactional
  void deleteScrin(JdbcTemplate jdbcTemplate, List<Map<String, Object>> scrins) {
    if (null == scrins) {
      return;
    }

    scrins.forEach(map -> {
      jdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(Scrin.class) + " WHERE scrin_id = '" + map.get("scrin_id") + "' ");
    });
  }



  /**
   * 컴포넌트 삭제
   * 
   * @param jdbcTemplate
   * @param compns
   */
  @Transactional
  void deleteCompn(JdbcTemplate jdbcTemplate, List<Map<String, Object>> compns) {
    if (null == compns) {
      return;
    }


    compns.forEach(map -> {
      jdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(Compn.class) + " WHERE compn_id = '" + map.get("compn_id") + "' ");
    });
  }


  /**
   * 컴포넌트 목록 조회
   * 
   * @param jdbcTemplate
   * @param scrins 화면 목록
   * @return
   */
  List<Map<String, Object>> getCompns(JdbcTemplate jdbcTemplate, List<Map<String, Object>> scrins) {
    List<Map<String, Object>> list = new ArrayList<>();

    if (null == scrins) {
      return list;
    }

    for (Map<String, Object> map : scrins) {
      List<Map<String, Object>> compns = jdbcTemplate.queryForList("SELECT compn_id FROM " + DbUtil.getTableName(Compn.class) + " WHERE scrin_id = '" + map.get("scrin_id") + "' ");
      list.addAll(compns);
    }


    return list;
  }


  /**
   * 화면 목록 조회
   * 
   * @param jdbcTemplate
   * @param scrinGroups 화면그룹 목록
   * @return
   */
  List<Map<String, Object>> getScrins(JdbcTemplate jdbcTemplate, List<Map<String, Object>> scrinGroups) {
    List<Map<String, Object>> list = new ArrayList<>();

    if (null == scrinGroups) {
      return list;
    }

    for (Map<String, Object> map : scrinGroups) {
      List<Map<String, Object>> scrins = jdbcTemplate.queryForList("SELECT scrin_id FROM " + DbUtil.getTableName(Scrin.class) + " WHERE scrin_group_id = '" + map.get("scrin_group_id") + "' ");
      list.addAll(scrins);

    }

    return list;
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



  /**
   * ddl문 실행
   * 
   * @param jdbcTemplate
   * @param trgetSysDto
   * @param prjctId 프로젝트 아이디
   * @return
   * @throws SQLException
   */
  Map<String, Long> executeBizDdl(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String prjctId) throws SQLException {
    Map<String, List<CompnDto>> compnDtoMap = getCompnDtos(prjctId);
    if (null == compnDtoMap) {
      return Map.of();
    }

    long createCo = 0;
    long alterCo = 0;
    Date startDt = new Date();

    // 테이블 개수만큼 루프
    for (Entry<String, List<CompnDto>> entry : compnDtoMap.entrySet()) {
      String tableName = entry.getKey();
      List<CompnDto> compnDtos = entry.getValue();

      // 테이블 존재여부 확인
      if (!existsTable(jdbcTemplate, tableName)) {
        // 테이블 미존재이면
        createTable(jdbcTemplate, trgetSysDto, tableName, compnDtos);
        createCo++;

      } else {
        // 테이블 존재이면
        alterTable(jdbcTemplate, trgetSysDto, tableName, compnDtos);
        alterCo++;
      }

    }

    return Map.of("createCo", createCo, "alterCo", alterCo, "tm(ms)", (new Date().getTime() - startDt.getTime()));

  }



  /**
   * 
   * @param jdbcTemplate
   * @param tableName
   * @param colmns
   * @param classes
   * @throws SQLException
   */
  void alterTable(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, List<String> colmns, List<Class<?>> classes) throws SQLException {
    // 현재 테이블의 모든 컬럼 목록
    Set<String> list = findAllColmns(jdbcTemplate, trgetSysDto, tableName);

    for (int i = 0; i < colmns.size(); i++) {
      boolean exists = false;

      for (String tableColmn : list) {
        if (colmns.get(i).equals(tableColmn)) {
          exists = true;
          break;
        }
      }

      if (!exists) {
        // 현재 컬럼 목록에 없는 colmn이면 추가하기
        alterTable(jdbcTemplate, trgetSysDto, tableName, colmns.get(i), classes.get(i));
      }
    }
  }


  /**
   * alter table
   * 
   * @param jdbcTemplate
   * @param tableName
   * @param compnDtos
   * @throws SQLException
   */
  void alterTable(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, List<CompnDto> compnDtos) throws SQLException {

    // 현재 테이블의 모든 컬럼 목록
    Set<String> list = findAllColmns(jdbcTemplate, trgetSysDto, tableName);


    compnDtos.forEach(dto -> {
      if (Util.isEmpty(dto.getEngAbrvNm())) {
        return;
      }


      boolean exists = false;

      for (String tableColmn : list) {
        if (tableColmn.equals(dto.getEngAbrvNm())) {
          exists = true;
          break;
        }
      }

      if (!exists) {
        // 현재 컬럼 목록에 없는 colmn이면 추가하기
        try {
          alterTableAddColumn(jdbcTemplate, trgetSysDto, tableName, dto);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    });

  }


  /**
   * 테이블에 컬럼 추가
   * 
   * @param jdbcTemplate
   * @param tableName
   * @param dto
   * @throws SQLException
   */
  void alterTableAddColumn(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, CompnDto dto) throws SQLException {
    StringBuffer sb = new StringBuffer();

    switch (DbProduct.valueOf(trgetSysDto.getDbTyNm())) {
      case MySQL:
      case MariaDB:
        sb.append(" ALTER TABLE `" + tableName + "`");
        sb.append(" ADD COLUMN `" + dto.getEngAbrvNm() + "` VARCHAR(255) NOT NULL COMMENT '" + dto.getHnglAbrvNm() + "' ");
        break;

      default:
        throw new RuntimeException("NOT IMPL " + trgetSysDto);
    }


    log.debug("<< - {} {} {}", trgetSysDto, tableName, dto.getEngAbrvNm());
    jdbcTemplate.execute(sb.toString());
  }



  /**
   * alter table
   * 
   * @param jdbcTemplate
   * @param tableName 테이블 명
   * @param colmnName 컬럼 명
   * @param colmnType 컬럼 타입
   * @throws SQLException
   */
  void alterTable(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, String colmnName, Class colmnType) throws SQLException {
    StringBuffer sb = new StringBuffer();

    switch (DbProduct.valueOf(trgetSysDto.getDbTyNm())) {
      case MySQL:
      case MariaDB:
        sb.append("ALTER TABLE `" + tableName + "` ADD COLUMN `" + colmnName + "` ");
        if (String.class == colmnType) {
          sb.append(" VARCHAR(255) ");
        } else if (Integer.class == colmnType) {
          sb.append(" INT ");
        } else if (Date.class == colmnType) {
          sb.append(" DATETIME ");
        } else {
          throw new RuntimeException("NOT IMPL " + colmnType);
        }

        break;

      default:
        throw new RuntimeException("NOT IMPL " + trgetSysDto);
    }


    log.debug("<< - {} {} {}", trgetSysDto, tableName, colmnName, colmnType);
    jdbcTemplate.execute(sb.toString());
  }


  /**
   * 테이블 모든 컬럼 목록 조회
   * 
   * @param jdbcTemplate
   * @param trgetSysDto
   * @param tableName
   * @return
   * @throws SQLException
   */
  Set<String> findAllColmns(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName) throws SQLException {
    switch (DbProduct.valueOf(trgetSysDto.getDbTyNm())) {
      case MySQL:
      case MariaDB:
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COLUMN_NAME");
        sb.append(" FROM information_schema.columns");
        sb.append(" WHERE TABLE_NAME = '" + tableName + "'");
        sb.append(" AND TABLE_SCHEMA = '" + trgetSysDto.getDbNm() + "'");

        Set<String> set = new HashSet<>();

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        for (Map<String, Object> map : list) {
          set.add(map.get("COLUMN_NAME").toString());
        }

        return set;

      default:
        throw new RuntimeException("NOT IMPL " + trgetSysDto);
    }

  }


  /**
   * 테이블 존재여부
   * 
   * @param jdbcTemplate
   * @param tableName
   * @return 테이블 존재하면 true
   */
  boolean existsTable(JdbcTemplate jdbcTemplate, String tableName) {
    try {
      String sql = " SELECT COUNT(*) AS co FROM " + tableName;
      List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
      log.debug("{} {}", tableName, list);
      return 0 < list.size();

    } catch (Exception e) {
      return false;
    }

  }



  /**
   * 테이블 생성
   * 
   * @param jdbcTemplate
   * @param tableName
   * @param pk pk 컬럼 명
   * @param colmns 컬럼 목록
   * @param classes 컬럼의 타입 목록
   * @throws SQLException
   */
  void createTable(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, String pk, List<String> colmns, List<Class<?>> classes) throws SQLException {
    StringBuffer sb = new StringBuffer();

    switch (DbProduct.valueOf(trgetSysDto.getDbTyNm())) {
      case MySQL:
      case MariaDB:
        sb.append(" CREATE TABLE `" + tableName + "` (");

        for (int i = 0; i < colmns.size(); i++) {
          sb.append("  `" + colmns.get(i) + "` ");
          if (String.class == classes.get(i)) {
            sb.append("VARCHAR(1000)");
          } else if (Integer.class == classes.get(i) || Long.class == classes.get(i)) {
            sb.append("INT");
          } else {
            throw new RuntimeException("NOT IMPL " + classes.get(i));
          }
          sb.append(" NULL DEFAULT NULL COLLATE 'utf8_general_ci', ");
        }

        sb.append(" PRIMARY KEY (`" + pk + "`)  ");
        sb.append(" )");
        sb.append(" COLLATE='utf8_general_ci'");
        sb.append(" ENGINE=InnoDB");
        sb.append(" DEFAULT CHARSET=utf8");
        break;

      default:
        throw new RuntimeException("NOT IMPL " + trgetSysDto);
    }


    log.debug("<< - {} {} {} {} {}", trgetSysDto, tableName, pk, colmns, classes);
    jdbcTemplate.execute(sb.toString());
  }



  /**
   * create table 실행
   * 
   * @param tableName 테이블명
   * @param compnDtos 컬럼 목록
   * @return
   * @throws SQLException
   */
  void createTable(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, List<CompnDto> compnDtos) throws SQLException {
    StringBuffer sb = new StringBuffer();

    String pkColmnName = Util.getPkColmnName(tableName);

    switch (DbProduct.valueOf(trgetSysDto.getDbTyNm())) {
      case MySQL:
      case MariaDB:
        sb.append(" CREATE TABLE `" + tableName + "` (");
        sb.append("  `" + pkColmnName + "` VARCHAR(255) NOT NULL COMMENT 'PK' COLLATE 'utf8_general_ci', "); // pk

        for (CompnDto dto : compnDtos) {
          if (!Util.isEmpty(dto.getEngAbrvNm())) {
            sb.append("  `" + dto.getEngAbrvNm() + "` VARCHAR(255) NULL DEFAULT NULL COMMENT '" + dto.getHnglAbrvNm() + "' COLLATE 'utf8_general_ci', ");
          }
        }

        sb.append(" PRIMARY KEY (`" + pkColmnName + "`)  "); // pk
        sb.append(" )");
        sb.append(" COLLATE='utf8_general_ci'");
        sb.append(" ENGINE=InnoDB");
        sb.append(" DEFAULT CHARSET=utf8");
        break;

      default:
        throw new RuntimeException("NOT IMPL " + trgetSysDto);
    }


    log.debug("<< - {} {} {}", trgetSysDto, tableName, compnDtos);
    jdbcTemplate.execute(sb.toString());

  }



  /**
   * key:테이블명, value:컬럼목록 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  Map<String, List<CompnDto>> getCompnDtos(String prjctId) {
    Map<String, List<CompnDto>> map = new HashMap<>();

    //
    scrinGroupService.findAllByPrjctId(prjctId).forEach(scrinGroupDto -> {
      String tableName = scrinGroupDto.getEngAbrvNm();
      //
      List<CompnDto> compnDtos = new ArrayList<>();

      //
      scrinService.findAllByScrinGroupId(scrinGroupDto.getScrinGroupId()).forEach(scrinDto -> {

        //
        compnService.findAllByScrinId(scrinDto.getScrinId()).forEach(compnDto -> {
          addIfNotExists(compnDtos, compnDto);
        });
      });

      map.put(tableName, compnDtos);
    });

    return map;
  }


  /**
   * compnDtos에 존재하지 않는 compnDto이면 compnDtos에 추가
   * 
   * @param compnDtos
   * @param compnDto
   */
  void addIfNotExists(List<CompnDto> compnDtos, CompnDto compnDto) {
    if (Util.isEmpty(compnDto.getEngAbrvNm())) {
      return;
    }

    boolean exists = false;
    for (CompnDto dto : compnDtos) {
      if (Util.isEmpty(dto.getEngAbrvNm())) {
        return;
      }


      if (dto.getEngAbrvNm().equals(compnDto.getEngAbrvNm())) {
        exists = true;
        break;
      }
    }

    if (!exists) {
      compnDtos.add(compnDto);
    }
  }


}
