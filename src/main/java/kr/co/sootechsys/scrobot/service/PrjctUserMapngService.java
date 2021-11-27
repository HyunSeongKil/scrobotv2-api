package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.PrjctUserMapngDto;

public interface PrjctUserMapngService {
  Long regist(PrjctUserMapngDto dto);

  void update(PrjctUserMapngDto dto);

  void deleteById(Long prjctUserMapngId);

  List<PrjctUserMapngDto> findAllByUserId(String userId);

  List<PrjctUserMapngDto> findAllByPrjctId(String prjctId);
}
