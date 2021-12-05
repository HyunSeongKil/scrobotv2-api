package kr.co.sootechsys.scrobot.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import kr.co.sootechsys.scrobot.domain.BbsDto;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchBbsDto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;

public interface BbsService {
  Long regist(BbsDto dto);

  Long regist(BbsDto dto, List<MultipartFile> files) throws IllegalStateException, IOException;

  void updt(BbsDto dto);

  void updt(BbsDto dto, List<MultipartFile> files) throws IllegalStateException, IOException;

  void deleteById(Long bbsId);

  PageableResult findAll(SearchBbsDto searchDto, Pageable pageable);

  BbsDto findById(Long bbsId);

  /**
   * 조회수 증가 (비동기)
   * 
   * @param bbsId 게시판아이디
   */
  void increaseInqireCoAsync(Long bbsId);

  @ApiOperation(value = "등록자아이디+게시판구분으로 목록 조회")
  List<BbsDto> findAllByRegisterIdAndBbsSeCd(String registerId, String bbsSeCd);
}
