package kr.co.sootechsys.scrobot.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import kr.co.sootechsys.scrobot.domain.CmmnCodeDto;

/**
 * 공통 코드
 */
public interface CmmnCodeService {

  /**
   * 대용량으로 등록
   * 
   * @param dtos 값 목록
   * @return 등록갯수
   */
  int registByBulk(List<CmmnCodeDto> dtos);

  Long regist(CmmnCodeDto dto);

  /**
   * 부모공통코드로 목록 조회
   * 
   * @param prntsCmmnCode 부모공통코드
   * @return
   */
  List<CmmnCodeDto> findAllByPrntsCmmnCode(String prntsCmmnCode);

  /**
   * 상세조회
   * 
   * @param cmmnCodeId 공통코드아이디
   * @return
   */
  CmmnCodeDto findById(Long cmmnCodeId);

  /**
   * 상세조회
   * 
   * @param prntsCmmnCode 부모공통코드
   * @param cmmnCode      공통코드
   * @return
   */
  CmmnCodeDto findByPrntsCmmnCodeAndCmmnCode(String prntsCmmnCode, String cmmnCode);

  /**
   * 전체 목록 조회
   * 
   * @return
   */
  List<CmmnCodeDto> findAll();

  /**
   * 엑셀파일 파싱
   * 첫줄은 제목임
   * cell 순서 1.cmmnCode 2.cmmnCodeNm 3.cmmnCodeCn 4.prntsCmmnCode
   */
  List<Map<String, Object>> parseExcel(MultipartFile excelFile) throws Exception;

}
