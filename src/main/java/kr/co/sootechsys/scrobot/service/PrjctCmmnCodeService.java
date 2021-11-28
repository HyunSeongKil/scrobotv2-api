package kr.co.sootechsys.scrobot.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;

public interface PrjctCmmnCodeService {
  Long regist(PrjctCmmnCodeDto dto);

  void update(PrjctCmmnCodeDto dto);

  void deleteById(Long prjctCmmnCodeId);

  List<PrjctCmmnCodeDto> findAllByPrjctId(String prjctId);

  PrjctCmmnCodeDto findByCmmnCode(String cmmnCode);

  List<PrjctCmmnCodeDto> findAllByPrjctIdAndPrntsPrjctCmmnCode(String prjctId, String prntsCmmnCode);

  List<Map<String, Object>> parseExcel(MultipartFile mf) throws IOException;
}
