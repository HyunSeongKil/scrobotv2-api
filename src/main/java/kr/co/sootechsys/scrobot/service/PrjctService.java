package kr.co.sootechsys.scrobot.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sootechsys.scrobot.domain.PrjctDto;

public interface PrjctService {
  String regist(PrjctDto dto);

  void updt(PrjctDto dto);

  void delete(String prjctId);

  PrjctDto findById(String prjctId);

  List<PrjctDto> findAllByUserId(String userId);

}
