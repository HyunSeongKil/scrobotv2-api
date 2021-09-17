package kr.co.sootechsys.scrobot.service;

import java.util.List;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;

public interface PrjctTrgetSysMapngService {
  Long regist(String prjctId, String trgetSysId);

  Long regist(PrjctTrgetSysMapngDto dto);

  PrjctTrgetSysMapngDto findByPrjctId(String prjctId);

  PrjctTrgetSysMapngDto findByTrgetSysId(String trgetSysId);

  void deleteByPrjctId(String prjctId);

  void deleteByTrgetSysId(String trgetSysId);

}
