package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;

public interface PrjctCmmnCodeService {
  Long regist(PrjctCmmnCodeDto dto);

  void update(PrjctCmmnCodeDto dto);

  void deleteById(Long prjctCmmnCodeId);

  List<PrjctCmmnCodeDto> findAllByPrjctId(String prjctId);

  PrjctCmmnCodeDto findByCmmnCode(String cmmnCode);

  List<PrjctCmmnCodeDto> findAllByPrjctIdAndPrntsPrjctCmmnCode(String prjctId, String prntsCmmnCode);
}
