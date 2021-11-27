package kr.co.sootechsys.scrobot.service;

import java.util.List;

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

  @ApiOperation(value = "프로젝트아이디로 목록 조회")
  List<GuidanceMssageDto> findAllByPrjctId(String prjctId);
}
