package kr.co.sootechsys.scrobot.service.impl;

import java.io.File;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import kr.co.sootechsys.scrobot.service.DbDriverService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DbDriverServiceImpl implements DbDriverService {

  private Map<String, String> dbDriverMap = null;

  @PostConstruct
  private void init() {
    this.dbDriverMap = loadDbDriver();
  }

  @Override
  public String getDbDriverNm(String dbTyNm) {
    return dbDriverMap.get(dbTyNm.toLowerCase());
  }

  /**
   * load db-driver.json
   * 
   * @return
   */
  Map<String, String> loadDbDriver() {
    try {
      File file = new ClassPathResource("db-driver.json").getFile();
      return new ObjectMapper().readValue(file, Map.class);
    } catch (Exception e) {
      log.error("{}", e);
    }

    return null;
  }

}
