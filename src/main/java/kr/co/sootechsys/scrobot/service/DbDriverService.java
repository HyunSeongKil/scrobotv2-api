package kr.co.sootechsys.scrobot.service;

import kr.co.sootechsys.scrobot.domain.TrgetSysDto;

public interface DbDriverService {
  String getDbDriverNm(String dbTyNm);

  String getUrl(TrgetSysDto dto);
}
