package kr.co.sootechsys.scrobot.service;

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.PrjctGuidanceMssageMapngDto;

public interface PrjctGuidanceMssageMapngService {
  Long regist(PrjctGuidanceMssageMapngDto dto);

  void update(PrjctGuidanceMssageMapngDto dto);

  void deleteById(Long prjctGuidanceMssageMapngId);

  @ApiOperation(value = "안내메시지아이디로 삭제")
  void deleteByGuidanceMssageId(Long guidanceMssageId);
}
