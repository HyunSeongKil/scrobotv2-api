package kr.co.sootechsys.scrobot.service;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.PrjctUserMapngDto;

public interface PrjctUserMapngService {
  Long regist(PrjctUserMapngDto dto);

  void update(PrjctUserMapngDto dto);

  void deleteById(Long prjctUserMapngId);

  List<PrjctUserMapngDto> findAllByUserId(String userId);

  List<PrjctUserMapngDto> findAllByPrjctId(String prjctId);

  /**
   * 관리자로 설정
   * 
   * @param dto
   */
  @ApiOperation(value = "관리자로 설정")
  void updateToMngr(PrjctUserMapngDto dto);
}
