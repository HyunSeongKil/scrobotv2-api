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
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.AtchmnflDto;
import kr.co.sootechsys.scrobot.domain.AtchmnflGroupDto;
import kr.co.sootechsys.scrobot.domain.CmmnCodeDto;
import kr.co.sootechsys.scrobot.domain.CompnDto;
import kr.co.sootechsys.scrobot.domain.DbProduct;
import kr.co.sootechsys.scrobot.domain.DeployDto;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.domain.MenuScrinMapngDto;
import kr.co.sootechsys.scrobot.domain.PrjctDto;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;
import kr.co.sootechsys.scrobot.domain.ScrinDto;
import kr.co.sootechsys.scrobot.domain.ScrinGroupDto;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.entity.Atchmnfl;
import kr.co.sootechsys.scrobot.entity.AtchmnflGroup;
import kr.co.sootechsys.scrobot.entity.CmmnCode;
import kr.co.sootechsys.scrobot.entity.Compn;
import kr.co.sootechsys.scrobot.entity.Menu;
import kr.co.sootechsys.scrobot.entity.MenuScrinMapng;
import kr.co.sootechsys.scrobot.entity.Prjct;
import kr.co.sootechsys.scrobot.entity.PrjctTrgetSysMapng;
import kr.co.sootechsys.scrobot.entity.Scrin;
import kr.co.sootechsys.scrobot.entity.ScrinGroup;
import kr.co.sootechsys.scrobot.entity.TrgetSys;
import kr.co.sootechsys.scrobot.misc.DbUtil;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.service.AtchmnflGroupService;
import kr.co.sootechsys.scrobot.service.AtchmnflService;
import kr.co.sootechsys.scrobot.service.CmmnCodeService;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.DbDriverService;
import kr.co.sootechsys.scrobot.service.DeployService;
import kr.co.sootechsys.scrobot.service.MenuScrinMapngService;
import kr.co.sootechsys.scrobot.service.MenuService;
import kr.co.sootechsys.scrobot.service.PrjctService;
import kr.co.sootechsys.scrobot.service.PrjctTrgetSysMapngService;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;
import kr.co.sootechsys.scrobot.service.ScrinService;
import kr.co.sootechsys.scrobot.service.TrgetSysService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Api(value = "?????? ?????????")
public class DeployServiceImpl implements DeployService {

  private PrjctService prjctService;
  private MenuService menuService;
  private MenuScrinMapngService menuScrinMapngService;
  private ScrinGroupService scrinGroupService;
  private ScrinService scrinService;
  private CompnService compnService;
  private TrgetSysService trgetSysService;
  private PrjctTrgetSysMapngService prjctTrgetSysMapngService;
  private CmmnCodeService cmmnCodeService;
  private DbDriverService dbDriverService;
  private AtchmnflGroupService atchmnflGroupService;
  private AtchmnflService atchmnflService;

  public DeployServiceImpl(CmmnCodeService cmmnCodeService, PrjctService prjctService, MenuService menuService,
      MenuScrinMapngService menuScrinMapngService,
      ScrinGroupService scrinGroupService, ScrinService scrinService,
      CompnService compnService, TrgetSysService trgetSysService, PrjctTrgetSysMapngService prjctTrgetSysMapngService,
      DbDriverService dbDriverService, AtchmnflGroupService atchmnflGroupService,
      AtchmnflService atchmnflService) {
    this.cmmnCodeService = cmmnCodeService;
    this.prjctService = prjctService;
    this.menuService = menuService;
    this.menuScrinMapngService = menuScrinMapngService;
    this.scrinGroupService = scrinGroupService;
    this.scrinService = scrinService;
    this.compnService = compnService;
    this.trgetSysService = trgetSysService;
    this.prjctTrgetSysMapngService = prjctTrgetSysMapngService;
    this.dbDriverService = dbDriverService;
    this.atchmnflGroupService = atchmnflGroupService;
    this.atchmnflService = atchmnflService;
  }

  @Override
  @Transactional
  public Map<String, Long> deploy(DeployDto dto) throws SQLException {
    // prjctTrgetSysMapngService.regist(dto.getPrjctId(), dto.getTrgetSysId());

    BasicDataSource ds = null;
    TrgetSysDto trgetSysDto = trgetSysService.findById(dto.getTrgetSysId());

    try {
      ds = createDataSource(trgetSysDto);

      JdbcTemplate trgetJdbcTemplate = new JdbcTemplate(ds);

      trgetSysDto.setDbNm(DbUtil.getDbName(trgetJdbcTemplate));
      trgetSysDto.setDbTyNm(DbUtil.getDbProductName(trgetJdbcTemplate));

      //
      executeBasicDdl(trgetJdbcTemplate, trgetSysDto, CmmnCode.class, Compn.class, Menu.class, MenuScrinMapng.class,
          Prjct.class, Scrin.class, TrgetSys.class, PrjctTrgetSysMapng.class, AtchmnflGroup.class, Atchmnfl.class);

      //
      deleteOldData(trgetJdbcTemplate, dto.getPrjctId());

      //
      insertNewData(trgetJdbcTemplate, dto.getPrjctId());

      //
      return executeBizDdl(trgetJdbcTemplate, trgetSysDto, dto.getPrjctId());

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
   * ?????? ????????? ??????/??????
   * 
   * @param jdbcTemplate
   * @param trgetSysDto
   * @param entityClass
   * @throws SQLException
   */
  private void executeBasicDdl(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, Class<?> entityClass)
      throws SQLException {
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
   * ?????? ????????? ??????/??????
   * 
   * @param jdbcTemplate
   * @param trgetSysDto
   * @param entityClasses
   * @throws SQLException
   */
  private void executeBasicDdl(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, Class<?>... entityClasses)
      throws SQLException {

    for (Class<?> clz : entityClasses) {
      executeBasicDdl(jdbcTemplate, trgetSysDto, clz);
    }

  }

  @Transactional
  void insertNewData(JdbcTemplate trgetJdbcTemplate, String prjctId) {
    List<CmmnCodeDto> cmmnCodeDtos = cmmnCodeService.findAll();
    PrjctDto prjctDto = prjctService.findById(prjctId);
    List<ScrinGroupDto> scrinGroupDtos = getScrinGroups(prjctId);
    List<MenuDto> menuDtos = getMenus(prjctId);
    List<MenuScrinMapngDto> menuScrinMapngDtos = getMenuScrinMapngs(menuDtos);
    List<ScrinDto> scrinDtos = getScrins(prjctId);
    List<CompnDto> compnDtos = getCompns(scrinDtos);
    PrjctTrgetSysMapngDto prjctTrgetSysMapng = prjctTrgetSysMapngService.findByPrjctId(prjctId);
    List<AtchmnflGroupDto> atchmnflGroupDtos = atchmnflGroupService.findAllByPrjctId(prjctId);
    List<AtchmnflDto> atchmnflDtos = atchmnflService.findAllByPrjctId(prjctId);

    insertCmmnCode(trgetJdbcTemplate, cmmnCodeDtos);
    insertPrjct(trgetJdbcTemplate, prjctDto);
    insertMenu(trgetJdbcTemplate, menuDtos);
    insertScrinGroup(trgetJdbcTemplate, scrinGroupDtos);
    insertScrin(trgetJdbcTemplate, scrinDtos);
    insertMenuScrinMapng(trgetJdbcTemplate, menuScrinMapngDtos);
    insertCompn(trgetJdbcTemplate, compnDtos);
    insertPrjctTrgetSysMapng(trgetJdbcTemplate, prjctTrgetSysMapng);
    insertAtchmnflGroup(trgetJdbcTemplate, atchmnflGroupDtos);
    insertAtchmnfl(trgetJdbcTemplate, atchmnflDtos);

    // TODO ?????? ????????? ????????????

  }

  /**
   * insert ????????????
   * 
   * @param trgetJjdbcTemplate
   * @param atchmnflDtos
   */
  @Transactional
  private void insertAtchmnfl(JdbcTemplate trgetJdbcTemplate, List<AtchmnflDto> atchmnflDtos) {
    if (null == atchmnflDtos || 0 == atchmnflDtos.size()) {
      return;
    }

    atchmnflDtos.forEach(dto -> {
      StringBuffer sb = new StringBuffer();

      sb.append(" INSERT INTO " + DbUtil.getTableName(Atchmnfl.class) + " (");
      sb.append("   atchmnfl_id");
      sb.append("   , atchmnfl_group_id");
      sb.append("   , atchmnfl_etsion");
      sb.append("   , atchmnfl_filesz_value");
      sb.append("   , atchmnfl_storg_path_value");
      sb.append("   , encoding_dstnct_cd");
      sb.append("   , original_file_nm");
      sb.append("   , storg_file_nm");
      sb.append("   , regist_dt");
      sb.append(" ) VALUES (");
      sb.append("   " + dto.getAtchmnflId());
      sb.append("   ," + dto.getAtchmnflGroupId());
      sb.append("   ,'" + dto.getAtchmnflEtsion() + "'");
      sb.append("   ,'" + dto.getAtchmnflFileszValue() + "'");
      sb.append("   ,'" + dto.getAtchmnflStorgPathValue() + "'");
      sb.append("   ,'" + dto.getEncodingDstnctCd() + "'");
      sb.append("   ,'" + dto.getOriginalFileNm() + "'");
      sb.append("   ,'" + dto.getStorgFileNm() + "'");
      sb.append("   ,'" + dto.getRegistDt() + "'");
      sb.append(")");

      trgetJdbcTemplate.execute(sb.toString());
    });
  }

  /**
   * insert ???????????? ??????
   * 
   * @param trgetJdbcTemplate
   * @param atchmnflGroupDtos
   */
  @Transactional
  private void insertAtchmnflGroup(JdbcTemplate trgetJdbcTemplate, List<AtchmnflGroupDto> atchmnflGroupDtos) {
    if (null == atchmnflGroupDtos || 0 == atchmnflGroupDtos.size()) {
      return;
    }

    atchmnflGroupDtos.forEach(dto -> {
      StringBuffer sb = new StringBuffer();

      sb.append(" INSERT INTO " + DbUtil.getTableName(AtchmnflGroup.class) + " (");
      sb.append("   atchmnfl_group_id");
      sb.append("   , prjct_id");
      sb.append("   , regist_dt");
      sb.append(" ) VALUES (");
      sb.append("   " + dto.getAtchmnflGroupId());
      sb.append("   ,'" + dto.getPrjctId() + "'");
      sb.append("   ,'" + dto.getRegistDt() + "'");
      sb.append(")");

      trgetJdbcTemplate.execute(sb.toString());
    });
  }

  /**
   * insert ???????????? ????????? to ?????? ?????????
   * 
   * @param jdbcTemplate
   * @param compnDtos
   */
  @Transactional
  private void insertCompn(JdbcTemplate jdbcTemplate, List<CompnDto> compnDtos) {
    if (null == compnDtos) {
      return;
    }

    compnDtos.forEach(dto -> {
      StringBuffer sb = new StringBuffer();

      sb.append(" INSERT INTO " + DbUtil.getTableName(Compn.class) + "(");
      sb.append("   compn_id");
      sb.append("   , compn_cn");
      sb.append("   , compn_nm");
      sb.append("   , compn_se_code");
      sb.append("   , eng_abrv_nm");
      sb.append("   , hngl_abrv_nm");
      sb.append("   , scrin_id");
      sb.append("   , regist_dt");
      sb.append("   , updt_dt");
      sb.append("   , ordr_value");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append("   '" + dto.getCompnId() + "' ");
      sb.append("   , '" + dto.getCompnCn() + "' ");
      sb.append("   , '" + dto.getCompnNm() + "' ");
      sb.append("   , '" + dto.getCompnSeCode() + "' ");
      sb.append("   , '" + dto.getEngAbrvNm() + "' ");
      sb.append("   , '" + dto.getHnglAbrvNm() + "' ");
      sb.append("   , '" + dto.getScrinId() + "' ");
      sb.append("   , '" + dto.getRegistDt() + "' ");
      sb.append("   , '" + dto.getUpdtDt() + "' ");
      sb.append("   , '" + dto.getOrdrValue() + "' ");
      sb.append(" )");

      jdbcTemplate.execute(sb.toString());
    });
  }

  /**
   * insert ?????? ????????? to ?????? ?????????
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
      sb.append("   , scrin_group_id");
      sb.append("   , scrin_nm");
      sb.append("   , scrin_se_code");
      sb.append("   , stdr_data_nm");
      sb.append("   , menu_id");
      sb.append("   , prjct_id");
      sb.append("   , regist_dt");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append("   '" + dto.getScrinId() + "' ");
      sb.append("   , '" + dto.getScrinGroupId() + "' ");
      sb.append("   , '" + dto.getScrinNm() + "' ");
      sb.append("   , '" + dto.getScrinSeCode() + "' ");
      sb.append("   , '" + dto.getStdrDataNm() + "' ");
      sb.append("   , '" + dto.getMenuId() + "' ");
      sb.append("   , '" + dto.getPrjctId() + "' ");
      sb.append("   , '" + dto.getRegistDt() + "' ");
      sb.append(" )");

      jdbcTemplate.execute(sb.toString());
    });
  }

  /**
   * ?????? ???????????? ??????-?????? ?????? ?????? ??????
   */
  private void insertMenuScrinMapng(JdbcTemplate trgetJdbcTemplate, List<MenuScrinMapngDto> menuScrinMapngDtos) {
    if (null == menuScrinMapngDtos) {
      return;
    }

    menuScrinMapngDtos.forEach(dto -> {
      StringBuffer sb = new StringBuffer();

      sb.append(" INSERT INTO " + DbUtil.getTableName(MenuScrinMapng.class) + "(");
      sb.append("   menu_scrin_mapng_id");
      sb.append("   , menu_id");
      sb.append("   , scrin_id");
      sb.append(" ) VALUES (");
      sb.append("   " + dto.getMenuScrinMapngId());
      sb.append("   , '" + dto.getMenuId() + "'");
      sb.append("   , '" + dto.getScrinId() + "'");
      sb.append(" )");

      trgetJdbcTemplate.execute(sb.toString());
    });
  }

  /**
   * insert ?????? ????????? to ?????? ?????????
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
      sb.append("   , menu_ordr_value");
      sb.append("   , prjct_id");
      sb.append("   , prnts_menu_id");
      sb.append("   , url_nm");
      sb.append("   , regist_dt");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append("   '" + dto.getMenuId() + "' ");
      sb.append("   , '" + dto.getMenuNm() + "' ");
      sb.append("   , '" + dto.getMenuOrdrValue() + "' ");
      sb.append("   , '" + dto.getPrjctId() + "' ");
      sb.append("   , '" + dto.getPrntsMenuId() + "' ");
      sb.append("   , '" + dto.getUrlNm() + "' ");
      sb.append("   , '" + dto.getRegistDt() + "' ");
      sb.append(" )");

      jdbcTemplate.execute(sb.toString());
    });
  }

  /**
   * insert ???????????? ????????? to ?????? ?????????
   * 
   * @param jdbcTemplate
   * @param scrinGroupDtos
   */
  @Deprecated(since = "")
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
   * ????????????-??????????????? ??????
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
   * ???????????? ??????
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
      sb.append("   , cmmn_code_cn");
      sb.append("   , prnts_cmmn_code");
      sb.append("   , use_at");
      sb.append("   , register_id");
      sb.append("   , register_nm");
      sb.append("   , regist_dt");
      sb.append(" )");
      sb.append(" VALUES (");
      sb.append(" " + dto.getCmmnCodeId() + " ");
      sb.append("   ,'" + dto.getCmmnCode() + "' ");
      sb.append("   ,'" + dto.getCmmnCodeNm() + "' ");
      sb.append("   ,'" + dto.getCmmnCodeCn() + "' ");
      sb.append("   ,'" + dto.getPrntsCmmnCode() + "' ");
      sb.append("   ,'" + dto.getUseAt() + "' ");
      sb.append("   ,'" + dto.getRegisterId() + "' ");
      sb.append("   ,'" + dto.getRegisterNm() + "' ");
      sb.append("   ,'" + dto.getRegistDt() + "' ");
      sb.append(" )");

      jdbcTemplate.execute(sb.toString());
    });
  }

  /**
   * insert ???????????? ????????? to ?????? ?????????
   * 
   * @param jdbcTemplate
   * @param prjctDto
   */
  private void insertPrjct(JdbcTemplate jdbcTemplate, PrjctDto prjctDto) {
    StringBuffer sb = new StringBuffer();

    sb.append(" INSERT INTO " + DbUtil.getTableName(Prjct.class) + "(");
    sb.append("   prjct_id");
    sb.append("   , prjct_nm");
    sb.append("   , prjct_cn");
    sb.append("   , user_id");
    sb.append("   , regist_dt");
    sb.append("   , updt_dt");
    sb.append(" )");
    sb.append(" VALUES (");
    sb.append("   '" + prjctDto.getPrjctId() + "' ");
    sb.append("   , '" + prjctDto.getPrjctNm() + "' ");
    sb.append("   , '" + prjctDto.getPrjctCn() + "' ");
    sb.append("   , '" + prjctDto.getUserId() + "' ");
    sb.append("   , '" + prjctDto.getRegistDt() + "' ");
    sb.append("   , '" + prjctDto.getUpdtDt() + "' ");
    sb.append(" )");

    jdbcTemplate.execute(sb.toString());
  }

  /**
   * ???????????? ?????? ??????
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
   * ?????? ?????? ??????
   * 
   * @param scrinGroupDtos
   * @return
   */
  private List<ScrinDto> getScrins(String prjctId) {
    return scrinService.findAllByPrjctId(prjctId);
  }

  /**
   * ?????? ?????? ??????
   * 
   * @param prjctId
   * @return
   */
  private List<MenuDto> getMenus(String prjctId) {
    return menuService.findAllByPrjctId(prjctId);
  }

  /**
   * ?????????????????? ??????-?????? ?????? ?????? ??????
   */
  private List<MenuScrinMapngDto> getMenuScrinMapngs(List<MenuDto> menuDtos) {
    List<MenuScrinMapngDto> dtos = new ArrayList<>();

    menuDtos.forEach(menuDto -> {
      dtos.addAll(menuScrinMapngService.findAllByMenuId(menuDto.getMenuId()));
    });

    return dtos;
  }

  /**
   * ?????? ?????? ?????? ??????
   * 
   * @param prjctId
   * @return
   */
  private List<ScrinGroupDto> getScrinGroups(String prjctId) {
    return scrinGroupService.findAllByPrjctId(prjctId);
  }

  @Transactional
  void deleteOldData(JdbcTemplate trgetJdbcTemplate, String prjctId) {
    // ????????????
    List<Map<String, Object>> cmmnCodes = trgetJdbcTemplate
        .queryForList("SELECT * FROM " + DbUtil.getTableName(CmmnCode.class));

    // ????????????
    List<Map<String, Object>> prjcts = trgetJdbcTemplate
        .queryForList("SELECT * FROM " + DbUtil.getTableName(Prjct.class) + " WHERE prjct_id = '" + prjctId + "'");
    if (null == prjcts) {
      return;
    }

    // ??????
    List<Map<String, Object>> menus = trgetJdbcTemplate.queryForList(
        "SELECT menu_id, prjct_id FROM " + DbUtil.getTableName(Menu.class) + " WHERE prjct_id = '" + prjctId + "'");

    // ??????-?????? ??????
    List<Map<String, Object>> menuScrinMapngs = getMenuScrinMapngs(trgetJdbcTemplate, menus);

    // ????????????
    List<Map<String, Object>> scrinGroups = trgetJdbcTemplate.queryForList("SELECT scrin_group_id, prjct_id FROM "
        + DbUtil.getTableName(ScrinGroup.class) + " WHERE prjct_id = '" + prjctId + "'");

    // ??????
    List<Map<String, Object>> scrins = getScrins(trgetJdbcTemplate, prjctId);

    // ????????????
    List<Map<String, Object>> compns = getCompns(trgetJdbcTemplate, scrins);

    // ???????????? ??????
    List<Map<String, Object>> atchmnflGroups = trgetJdbcTemplate.queryForList("SELECT atchmnfl_group_id, prjct_id FROM "
        + DbUtil.getTableName(AtchmnflGroup.class) + " WHERE prjct_id = '" + prjctId + "'");

    // ????????????
    List<Map<String, Object>> atchmnfls = trgetJdbcTemplate.queryForList(
        "SELECT a.atchmnfl_id FROM " + DbUtil.getTableName(Atchmnfl.class) + " a INNER JOIN "
            + DbUtil.getTableName(AtchmnflGroup.class) + " b ON a.atchmnfl_group_id = b.atchmnfl_group_id");

    deleteCompn(trgetJdbcTemplate, compns);
    deleteScrin(trgetJdbcTemplate, scrins);
    deleteScrinGroup(trgetJdbcTemplate, scrinGroups);
    deleteMenuScrinMapng(trgetJdbcTemplate, menuScrinMapngs);
    deleteMenu(trgetJdbcTemplate, menus);
    deletePrjctTrgetSysMapng(trgetJdbcTemplate, prjctId);
    deletePrjct(trgetJdbcTemplate, prjctId);
    deleteCmmnCode(trgetJdbcTemplate, cmmnCodes);
    deleteAtchmnfl(trgetJdbcTemplate, atchmnfls);
    deleteAtchmnflGroup(trgetJdbcTemplate, atchmnflGroups);

    // TODO ??????????????? ????????? ???????
  }

  /**
   * ???????????? ?????? ??????
   */
  private void deleteAtchmnflGroup(JdbcTemplate trgetJdbcTemplate, List<Map<String, Object>> atchmnflGroups) {
    if (null == atchmnflGroups || 0 == atchmnflGroups.size()) {
      return;
    }

    atchmnflGroups.forEach(map -> {
      trgetJdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(AtchmnflGroup.class)
          + " WHERE atchmnfl_group_id = " + map.get("atchmnfl_group_id"));
    });
  }

  /**
   * ???????????? ??????
   * 
   * @param trgetJdbcTemplate
   * @param atchmnfls
   */
  private void deleteAtchmnfl(JdbcTemplate trgetJdbcTemplate, List<Map<String, Object>> atchmnfls) {
    if (null == atchmnfls || 0 == atchmnfls.size()) {
      return;
    }

    atchmnfls.forEach(map -> {
      trgetJdbcTemplate.execute(
          "DELETE FROM " + DbUtil.getTableName(Atchmnfl.class) + " WHERE atchmnfl_id = " + map.get("atchmnfl_id"));
    });
  }

  /**
   * ????????????-??????????????? ?????? ????????? ??????
   * 
   * @param jdbcTemplate
   * @param prjctId
   */
  @Transactional
  void deletePrjctTrgetSysMapng(JdbcTemplate jdbcTemplate, String prjctId) {
    if (null == prjctId) {
      return;
    }

    jdbcTemplate.execute(
        "DELETE FROM " + DbUtil.getTableName(PrjctTrgetSysMapng.class) + " WHERE prjct_id = '" + prjctId + "' ");
  }

  /**
   * ???????????? ????????? ??????
   * 
   * @param jdbcTemplate
   * @param list
   */
  @Transactional
  void deleteCmmnCode(JdbcTemplate jdbcTemplate, List<Map<String, Object>> list) {
    jdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(CmmnCode.class));
  }

  /**
   * ???????????? ??????
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

  @Transactional
  void deleteMenuScrinMapng(JdbcTemplate trgetJdbcTemplate, List<Map<String, Object>> menuScrinMapngs) {
    if (null == menuScrinMapngs) {
      return;
    }

    menuScrinMapngs.forEach(x -> {
      trgetJdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(MenuScrinMapng.class)
          + " WHERE menu_scrin_mapng_id = " + x.get("menu_scrin_mapng_id"));
    });
  }

  /**
   * ?????? ??????
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
      jdbcTemplate
          .execute("DELETE FROM " + DbUtil.getTableName(Menu.class) + " WHERE menu_id = '" + map.get("menu_id") + "' ");
    });
  }

  /**
   * ???????????? ??????
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
      jdbcTemplate.execute("DELETE FROM " + DbUtil.getTableName(ScrinGroup.class) + " WHERE scrin_group_id = '"
          + map.get("scrin_group_id") + "' ");
    });
  }

  /**
   * ?????? ??????
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
      jdbcTemplate.execute(
          "DELETE FROM " + DbUtil.getTableName(Scrin.class) + " WHERE scrin_id = '" + map.get("scrin_id") + "' ");
    });
  }

  /**
   * ???????????? ??????
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
      jdbcTemplate.execute(
          "DELETE FROM " + DbUtil.getTableName(Compn.class) + " WHERE compn_id = '" + map.get("compn_id") + "' ");
    });
  }

  /**
   * ???????????? ?????? ??????
   * 
   * @param jdbcTemplate
   * @param scrins       ?????? ??????
   * @return
   */
  List<Map<String, Object>> getCompns(JdbcTemplate jdbcTemplate, List<Map<String, Object>> scrins) {
    List<Map<String, Object>> list = new ArrayList<>();

    if (null == scrins) {
      return list;
    }

    for (Map<String, Object> map : scrins) {
      List<Map<String, Object>> compns = jdbcTemplate.queryForList("SELECT compn_id FROM "
          + DbUtil.getTableName(Compn.class) + " WHERE scrin_id = '" + map.get("scrin_id") + "' ");
      list.addAll(compns);
    }

    return list;
  }

  /**
   * ?????????????????? ??????-?????? ?????? ?????? ??????
   * 
   * @param jdbcTemplate
   * @param menus        ?????? ??????
   */
  List<Map<String, Object>> getMenuScrinMapngs(JdbcTemplate jdbcTemplate, List<Map<String, Object>> menus) {
    List<Map<String, Object>> list = new ArrayList<>();

    if (null == menus) {
      return list;
    }

    menus.forEach(x -> {
      list.addAll(jdbcTemplate
          .queryForList("SELECT menu_scrin_mapng_id FROM menu_scrin_mapng WHERE menu_id = '" + x.get("menu_id") + "'"));
    });

    return list;
  }

  /**
   * ?????? ?????? ??????
   * 
   * @param jdbcTemplate
   * @param prjctId      ?????????????????????
   * @return
   */
  List<Map<String, Object>> getScrins(JdbcTemplate jdbcTemplate, String prjctId) {
    List<Map<String, Object>> list = new ArrayList<>();

    if (null == prjctId) {
      return list;
    }

    return jdbcTemplate.queryForList(
        "SELECT scrin_id FROM " + DbUtil.getTableName(Scrin.class) + " WHERE prjct_id = '" + prjctId + "' ");

  }

  /**
   * datasource ??????
   * 
   * @param trgetSysId
   * @return
   */
  BasicDataSource createDataSource(TrgetSysDto dto) {

    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName(dbDriverService.getDbDriverNm(dto.getDbTyNm()));
    ds.setUrl(dbDriverService.getUrl(dto));
    ds.setUsername(dto.getDbUserNm());
    ds.setPassword(dto.getDbPasswordNm());

    return ds;
  }

  /**
   * ddl??? ??????
   * 
   * @param jdbcTemplate
   * @param trgetSysDto
   * @param prjctId      ???????????? ?????????
   * @return
   * @throws SQLException
   */
  Map<String, Long> executeBizDdl(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String prjctId)
      throws SQLException {
    Map<String, List<CompnDto>> compnDtoMap = getCompnDtos(prjctId);
    if (null == compnDtoMap) {
      return Map.of();
    }

    long createCo = 0;
    long alterCo = 0;
    Date startDt = new Date();

    // ????????? ???????????? ??????
    for (Entry<String, List<CompnDto>> entry : compnDtoMap.entrySet()) {
      String tableName = entry.getKey();
      List<CompnDto> compnDtos = entry.getValue();

      // ????????? ???????????? ??????
      if (!existsTable(jdbcTemplate, tableName)) {
        // ????????? ???????????????
        createTable(jdbcTemplate, trgetSysDto, tableName, compnDtos);
        createCo++;

      } else {
        // ????????? ????????????
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
  void alterTable(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, List<String> colmns,
      List<Class<?>> classes) throws SQLException {
    // ?????? ???????????? ?????? ?????? ??????
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
        // ?????? ?????? ????????? ?????? colmn?????? ????????????
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
  void alterTable(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, List<CompnDto> compnDtos)
      throws SQLException {

    // ?????? ???????????? ?????? ?????? ??????
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
        // ?????? ?????? ????????? ?????? colmn?????? ????????????
        try {
          alterTableAddColumn(jdbcTemplate, trgetSysDto, tableName, dto);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    });

  }

  /**
   * ???????????? ?????? ??????
   * 
   * @param jdbcTemplate
   * @param tableName
   * @param dto
   * @throws SQLException
   */
  void alterTableAddColumn(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, CompnDto dto)
      throws SQLException {
    StringBuffer sb = new StringBuffer();

    switch (DbProduct.valueOf(trgetSysDto.getDbTyNm())) {
      case MySQL:
      case MariaDB:
        sb.append(" ALTER TABLE `" + tableName + "`");
        sb.append(
            " ADD COLUMN `" + dto.getEngAbrvNm() + "` VARCHAR(255) NOT NULL COMMENT '" + dto.getHnglAbrvNm() + "' ");
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
   * @param tableName    ????????? ???
   * @param colmnName    ?????? ???
   * @param colmnType    ?????? ??????
   * @throws SQLException
   */
  void alterTable(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, String colmnName,
      Class colmnType) throws SQLException {
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
   * ????????? ?????? ?????? ?????? ??????
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
   * ????????? ????????????
   * 
   * @param jdbcTemplate
   * @param tableName
   * @return ????????? ???????????? true
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
   * ????????? ??????
   * 
   * @param jdbcTemplate
   * @param tableName
   * @param pk           pk ?????? ???
   * @param colmns       ?????? ??????
   * @param classes      ????????? ?????? ??????
   * @throws SQLException
   */
  void createTable(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, String pk, List<String> colmns,
      List<Class<?>> classes) throws SQLException {
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
          } else if (Date.class == classes.get(i)) {
            sb.append("DATETIME");
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
   * create table ??????
   * 
   * @param tableName ????????????
   * @param compnDtos ?????? ??????
   * @return
   * @throws SQLException
   */
  void createTable(JdbcTemplate jdbcTemplate, TrgetSysDto trgetSysDto, String tableName, List<CompnDto> compnDtos)
      throws SQLException {
    StringBuffer sb = new StringBuffer();

    String pkColmnName = Util.getPkColmnName(tableName);

    switch (DbProduct.valueOf(trgetSysDto.getDbTyNm())) {
      case MySQL:
      case MariaDB:
        sb.append(" CREATE TABLE `" + tableName + "` (");
        sb.append("  `" + pkColmnName + "` VARCHAR(255) NOT NULL COMMENT 'PK' COLLATE 'utf8_general_ci', "); // pk

        for (CompnDto dto : compnDtos) {
          if (!Util.isEmpty(dto.getEngAbrvNm())) {
            sb.append("  `" + dto.getEngAbrvNm() + "` VARCHAR(255) NULL DEFAULT NULL COMMENT '" + dto.getHnglAbrvNm()
                + "' COLLATE 'utf8_general_ci', ");
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
   * key:????????????, value:???????????? ??????
   * 
   * @param prjctId ???????????? ?????????
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
   * compnDtos??? ???????????? ?????? compnDto?????? compnDtos??? ??????
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

  @Override
  public Map<String, Long> deploy(String prjctId) throws SQLException {
    List<TrgetSysDto> dtos = trgetSysService.findAllByPrjctId(prjctId);
    if (0 == dtos.size()) {
      throw new RuntimeException("not exists trget sys");
    }

    DeployDto dto = new DeployDto();
    dto.setPrjctId(prjctId);
    dto.setTrgetSysId(dtos.get(0).getTrgetSysId());

    return deploy(dto);
  }

}
