package kr.co.sootechsys.scrobot.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import kr.co.sootechsys.scrobot.domain.BbsDto;
import kr.co.sootechsys.scrobot.domain.SearchBbsDto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BbsService {
  Long regist(BbsDto dto);

  Long regist(BbsDto dto, List<MultipartFile> files) throws IllegalStateException, IOException;

  void updt(BbsDto dto);

  void deleteById(Long bbsId);

  Map<String, Object> findAll(SearchBbsDto searchDto, Pageable pageable);

  BbsDto findById(Long bbsId);

  /**
   * 조회수 증가 (비동기)
   * 
   * @param bbsId 게시판아이디
   */
  void increaseInqireCoAsync(Long bbsId);
}
