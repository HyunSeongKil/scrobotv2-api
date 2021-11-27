package kr.co.sootechsys.scrobot.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.GuidanceMssageDto;

@Api(value = "안내 메시지 서비스")
public interface GuidanceMssageService {
  @ApiOperation(value = "안내 메시지 등록")
  Long regist(GuidanceMssageDto dto);

  @ApiOperation(value = "안내 메시지 수정")
  void update(GuidanceMssageDto dto);

  @ApiOperation(value = "안내 메시지 삭제")
  void deleteById(Long guidanceMssageId);
}
