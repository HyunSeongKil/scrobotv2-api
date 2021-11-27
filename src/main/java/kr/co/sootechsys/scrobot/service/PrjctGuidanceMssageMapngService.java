package kr.co.sootechsys.scrobot.service;

import kr.co.sootechsys.scrobot.domain.PrjctGuidanceMssageMapngDto;

public interface PrjctGuidanceMssageMapngService {
  Long regist(PrjctGuidanceMssageMapngDto dto);

  void update(PrjctGuidanceMssageMapngDto dto);

  void deleteById(Long prjctGuidanceMssageMapngId);
}
