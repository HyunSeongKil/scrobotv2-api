package kr.co.sootechsys.scrobot.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import kr.co.sootechsys.scrobot.domain.CompnDto;
import kr.co.sootechsys.scrobot.domain.DbType;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.domain.PrjctDto;
import kr.co.sootechsys.scrobot.domain.ScrinDto;
import kr.co.sootechsys.scrobot.domain.ScrinGroupDto;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.DeployService;
import kr.co.sootechsys.scrobot.service.MenuService;
import kr.co.sootechsys.scrobot.service.PrjctService;
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

  public DeployServiceImpl(PrjctService prjctService, MenuService menuService, ScrinGroupService scrinGroupService, ScrinService scrinService, CompnService compnService,
      TrgetSysService trgetSysService) {
    this.prjctService = prjctService;
    this.menuService = menuService;
    this.scrinGroupService = scrinGroupService;
    this.scrinService = scrinService;
    this.compnService = compnService;
    this.trgetSysService = trgetSysService;
  }


  @Override
  public Map<String, Long> deploy(String prjctId, String trgetSysId) throws SQLException {
    BasicDataSource ds = null;
    TrgetSysDto trgetSysDto = trgetSysService.findById(trgetSysId);

    try {
      ds = createDataSource(trgetSysDto);

      JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      //
      deleteOldData(jdbcTemplate, prjctId);

      //
      insertNewData(jdbcTemplate, prjctId);

      //
      return executeDdl(jdbcTemplate, trgetSysDto, prjctId);

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


  @Transactional
  void insertNewData(JdbcTemplate jdbcTemplate, String prjctId) {
    PrjctDto prjctDto = prjctService.findById(prjctId);
    List<ScrinGroupDto> scrinGroupDtos = getScrinGroups(prjctId);
    List<MenuDto> menuDtos = getMenus(prjctId);
    List<ScrinDto> scrinDtos = getScrins(scrinGroupDtos);
    List<CompnDto> compnDtos = getCompns(scrinDtos);


    insertPrjct(jdbcTemplate, prjctDto);
    insertMenu(jdbcTemplate, menuDtos);
    insertScrinGroup(jdbcTemplate, scrinGroupDtos);
    insertScrin(jdbcTemplate, scrinDtos);
    insertCompn(jdbcTemplate, compnDtos);

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

      sb.append(" INSERT INTO " + TABLE_NAME_COMPN + "(");
      sb.append("   compn_id");
      sb.append("   , compn_nm");
      sb.append("   , compn_cn");
      sb.append("   , eng_abrv_nm");
      sb.append("   , hngl_abrv_nm");
      sb.append("   , scrin_id");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append("   '" + dto.getCompnId() + "' ");
      sb.append("   , '" + dto.getCompnNm() + "' ");
      sb.append("   , '" + dto.getCompnCn() + "' ");
      sb.append("   , '" + dto.getEngAbrvNm() + "' ");
      sb.append("   , '" + dto.getHnglAbrvNm() + "' ");
      sb.append("   , '" + dto.getScrinId() + "' ");
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

      sb.append(" INSERT INTO " + TABLE_NAME_SCRIN + "(");
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

      sb.append(" INSERT INTO " + TABLE_NAME_MENU + "(");
      sb.append("   menu_id");
      sb.append("   , menu_nm");
      sb.append("   , prjct_id");
      sb.append("   , prnts_menu_id");
      sb.append("   , url_nm");
      sb.append("   , menu_ordr_value");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append("   '" + dto.getMenuId() + "' ");
      sb.append("   , '" + dto.getMenuNm() + "' ");
      sb.append("   , '" + dto.getPrjctId() + "' ");
      sb.append("   , '" + dto.getPrntsMenuId() + "' ");
      sb.append("   , '" + dto.getUrlNm() + "' ");
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

      sb.append(" INSERT INTO " + TABLE_NAME_SCRIN_GROUP + "(");
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
   * insert 프로젝트 데이터 to 대상 시스템
   * 
   * @param jdbcTemplate
   * @param prjctDto
   */
  private void insertPrjct(JdbcTemplate jdbcTemplate, PrjctDto prjctDto) {
    StringBuffer sb = new StringBuffer();

    sb.append(" INSERT INTO " + TABLE_NAME_PRJCT + "(");
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
    // 프로젝트
    List<Map<String, Object>> prjcts = jdbcTemplate.queryForList("SELECT * FROM " + TABLE_NAME_PRJCT + " WHERE prjct_id = '" + prjctId + "'");
    if (null == prjcts) {
      return;
    }

    // 메뉴
    List<Map<String, Object>> menus = jdbcTemplate.queryForList("SELECT menu_id, prjct_id FROM " + TABLE_NAME_MENU + " WHERE prjct_id = '" + prjctId + "'");

    // 화면그룹
    List<Map<String, Object>> scrinGroups = jdbcTemplate.queryForList("SELECT scrin_group_id, prjct_id FROM " + TABLE_NAME_SCRIN_GROUP + " WHERE prjct_id = '" + prjctId + "'");

    // 화면
    List<Map<String, Object>> scrins = getScrins(jdbcTemplate, scrinGroups);

    // 컴포넌트
    List<Map<String, Object>> compns = getCompns(jdbcTemplate, scrins);


    deleteCompn(jdbcTemplate, compns);
    deleteScrin(jdbcTemplate, scrins);
    deleteScrinGroup(jdbcTemplate, scrinGroups);
    deleteMenu(jdbcTemplate, menus);
    deletePrjct(jdbcTemplate, prjctId);

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

    jdbcTemplate.execute("DELETE FROM " + TABLE_NAME_PRJCT + " WHERE prjct_id = '" + prjctId + "' ");
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
      jdbcTemplate.execute("DELETE FROM " + TABLE_NAME_MENU + " WHERE menu_id = '" + map.get("menu_id") + "' ");
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
      jdbcTemplate.execute("DELETE FROM " + TABLE_NAME_SCRIN_GROUP + " WHERE scrin_group_id = '" + map.get("scrin_group_id") + "' ");
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
      jdbcTemplate.execute("DELETE FROM " + TABLE_NAME_SCRIN + " WHERE scrin_id = '" + map.get("scrin_id") + "' ");
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
      jdbcTemplate.execute("DELETE FROM " + TABLE_NAME_COMPN + " WHERE compn_id = '" + map.get("compn_id") + "' ");
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
      List<Map<String, Object>> compns = jdbcTemplate.queryForList("SELECT compn_id FROM " + TABLE_NAME_COMPN + " WHERE scrin_id = '" + map.get("scrin_id") + "' ");
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
      List<Map<String, Object>> scrins = jdbcTemplate.queryForList("SELECT scrin_id FROM " + TABLE_NAME_SCRIN + " WHERE scrin_group_id = '" + map.get("scrin_group_id") + "' ");
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
   */
  Map<String, Long> executeDdl(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String prjctId) {
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
        createTable(jdbcTemplate, trgetSysDto.getDbTyNm(), tableName, compnDtos);
        createCo++;

      } else {
        // 테이블 존재이면
        alterTable(jdbcTemplate, trgetSysDto.getDbTyNm(), tableName, compnDtos);
        alterCo++;
      }

    }

    return Map.of("createCo", createCo, "alterCo", alterCo, "tm(ms)", (new Date().getTime() - startDt.getTime()));

  }



  /**
   * alter table
   * 
   * @param jdbcTemplate
   * @param dbTyNm
   * @param tableName
   * @param compnDtos
   */
  void alterTable(JdbcTemplate jdbcTemplate, String dbTyNm, String tableName, List<CompnDto> compnDtos) {

    // 현재 테이블의 모든 컬럼 목록
    List<String> list = findAllColmns(jdbcTemplate, dbTyNm, tableName);


    compnDtos.forEach(dto -> {
      boolean exists = false;

      for (String tableColmn : list) {
        if (dto.getEngAbrvNm().equals(tableColmn)) {
          exists = true;
          break;
        }
      }

      if (!exists) {
        // 현재 컬럼 목록에 없는 colmn이면 추가하기
        alterTable(jdbcTemplate, dbTyNm, tableName, dto);
      }
    });

  }


  /**
   * 테이블에 컬럼 추가
   * 
   * @param jdbcTemplate
   * @param dbTyNm
   * @param tableName
   * @param dto
   */
  void alterTable(JdbcTemplate jdbcTemplate, String dbTyNm, String tableName, CompnDto dto) {
    StringBuffer sb = new StringBuffer();

    switch (DbType.valueOf(dbTyNm)) {
      case MYSQL:
      case MARIA_DB:
        sb.append("ALTER TABLE `" + tableName + "` ADD COLUMN `" + dto.getEngAbrvNm() + "` VARCHAR(255) NOT NULL COMMENT '" + dto.getHnglAbrvNm() + "' ");
        break;

      default:
        throw new RuntimeException("NOT IMPL " + dbTyNm);
    }


    log.debug("<< - {} {} {}", dbTyNm, tableName, dto.getEngAbrvNm());
    jdbcTemplate.execute(sb.toString());
  }


  /**
   * 테이블 모든 컬럼 목록 조회
   * 
   * @param jdbcTemplate
   * @param trgetSysDto
   * @param tableName
   * @return
   */
  List<String> findAllColmns(JdbcTemplate jdbcTemplate, String dbTyNm, String tableName) {
    switch (DbType.valueOf(dbTyNm)) {
      case MYSQL:
      case MARIA_DB:
        String sql = "SELECT COLUMN_NAME FROM information_schema.columns WHERE TABLE_NAME = '" + tableName + "'";

        List<String> list2 = new ArrayList<>();

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
          list2.add(map.get("COLUMN_NAME").toString());
        }

        return list2;
    }

    return null;
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
   * create table 실행
   * 
   * @param tableName 테이블명
   * @param compnDtos 컬럼 목록
   * @return
   */
  void createTable(JdbcTemplate jdbcTemplate, String dbTyNm, String tableName, List<CompnDto> compnDtos) {
    StringBuffer sb = new StringBuffer();

    switch (DbType.valueOf(dbTyNm)) {
      case MYSQL:
      case MARIA_DB:
        sb.append(" CREATE TABLE `" + tableName + "` (");
        sb.append("  `" + tableName + "_id" + "` VARCHAR(255) NOT NULL COLLATE 'utf8_general_ci', "); // pk

        for (CompnDto dto : compnDtos) {
          sb.append("  `" + dto.getEngAbrvNm() + "` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci', ");
        }

        sb.append(" PRIMARY KEY (`" + tableName + "_id" + "`)  ");
        sb.append(" )");
        sb.append(" COLLATE='utf8_general_ci'");
        sb.append(" ENGINE=InnoDB");
        sb.append(" DEFAULT CHARSET=utf8");
        break;

      default:
        throw new RuntimeException("NOT IMPL " + dbTyNm);
    }


    log.debug("<< - {} {} {}", dbTyNm, tableName, compnDtos);
    jdbcTemplate.execute(sb.toString());

  }



  /**
   * 테이블명, 컬럼목록 조회
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
          compnDtos.add(compnDto);
        });
      });

      map.put(tableName, compnDtos);
    });

    return map;
  }
}
