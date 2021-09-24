package kr.co.sootechsys.scrobot.service;

import java.util.List;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;

/**
 * 대상 시스템
 */
public interface PrjctTrgetSysMapngService {
  /**
   * 등록
   * 
   * @param prjctId 프로젝트 아이디
   * @param trgetSysId 대상 시스템 아이디
   * @return
   */
  Long regist(String prjctId, String trgetSysId);

  /**
   * 등록
   * 
   * @param dto 값
   * @return
   */
  Long regist(PrjctTrgetSysMapngDto dto);

  /**
   * 프로젝트 아이디로 1건 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  PrjctTrgetSysMapngDto findByPrjctId(String prjctId);


  /**
   * 대상 시스템 아이디로 1건 조회
   * 
   * @param trgetSysId 대상 시스템 아이디
   * @return
   */
  PrjctTrgetSysMapngDto findByTrgetSysId(String trgetSysId);

  /**
   * 프로젝트 아이디로 삭제
   * 
   * @param prjctId 프로젝트 아이디
   */
  void deleteByPrjctId(String prjctId);

  /**
   * 대상 시스템 아이디로 삭제
   * 
   * @param trgetSysId 대상 시스템 아이디
   */
  void deleteByTrgetSysId(String trgetSysId);

}
