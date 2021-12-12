package kr.co.sootechsys.scrobot.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;
import kr.co.sootechsys.scrobot.domain.SearchPrjctCmmnCodeDto;

public interface PrjctCmmnCodeService {
  Long regist(PrjctCmmnCodeDto dto);

  void update(PrjctCmmnCodeDto dto);

  void deleteById(Long prjctCmmnCodeId);

  List<PrjctCmmnCodeDto> findAllByPrjctId(String prjctId);

  PrjctCmmnCodeDto findByCmmnCode(String cmmnCode);

  List<PrjctCmmnCodeDto> findAllByPrjctIdAndPrntsCmmnCode(String prjctId, String prntsCmmnCode);

  List<Map<String, Object>> parseExcel(MultipartFile mf) throws IOException;

  PageableResult findAll(SearchPrjctCmmnCodeDto searchDto, Pageable pageable);

  PrjctCmmnCodeDto findById(Long prjctCmmnCodeId);

  List<Long> regist(List<PrjctCmmnCodeDto> dtos);
}
