package kr.co.sootechsys.scrobot.service;

import java.util.List;
import kr.co.sootechsys.scrobot.domain.CmmnCodeDto;


/**
 * 공통 코드
 */
public interface CmmnCodeService {
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
   * @param cmmnCode 공통코드
   * @return
   */
  CmmnCodeDto findByPrntsCmmnCodeAndCmmnCode(String prntsCmmnCode, String cmmnCode);


  List<CmmnCodeDto> findAll();
}
