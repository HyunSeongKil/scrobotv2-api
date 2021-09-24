package kr.co.sootechsys.scrobot.service;

import java.util.List;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;

/**
 * 대상 시스템
 */
public interface TrgetSysService {

  /**
   * 등록
   * 
   * @param dto 값
   * @return
   */
  String regist(TrgetSysDto dto);

  /**
   * 수정
   * 
   * @param dto 값
   */
  void updt(TrgetSysDto dto);

  /**
   * 삭제
   * 
   * @param trgetSysId 대상 시스템 아이디
   */
  void delete(String trgetSysId);

  /**
   * 1건 조회
   * 
   * @param trgetSysId 대상 시스템 아이디
   * @return
   */
  TrgetSysDto findById(String trgetSysId);

  /**
   * 목록 조회
   * 
   * @return
   */
  List<TrgetSysDto> findAll();
}
