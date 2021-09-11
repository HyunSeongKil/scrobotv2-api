package kr.co.sootechsys.scrobot.service;

import java.util.List;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;

public interface TrgetSysService {

  String regist(TrgetSysDto dto);

  void updt(TrgetSysDto dto);

  void delete(String trgetSysId);

  TrgetSysDto findById(String trgetSysId);

  List<TrgetSysDto> findAll();
}
