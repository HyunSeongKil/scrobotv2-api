package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.PrjctDto;

public interface PrjctService {
  String regist(PrjctDto dto);

  void updt(PrjctDto dto);

  void delete(String prjctId);

  PrjctDto find(String prjctId);

  List<PrjctDto> findAllByUserId(String userId);
}
