package kr.co.sootechsys.scrobot.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sootechsys.scrobot.domain.PrjctDto;


/**
 * 프로젝트
 */
public interface PrjctService {
  /**
   * 등록
   * 
   * @param dto 값
   * @return
   */
  String regist(PrjctDto dto);

  /**
   * 수정
   * 
   * @param dto 값
   */
  void updt(PrjctDto dto);

  /**
   * 삭제
   * 
   * @param prjctId 프로젝트 아이디
   */
  void delete(String prjctId);

  /**
   * 프로젝트 아이디로 1건 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  PrjctDto findById(String prjctId);

  /**
   * 사용자 아이디로 목록 조회
   * 
   * @param userId 사용자 아이디
   * @return
   */
  List<PrjctDto> findAllByUserId(String userId);

  /**
   * 프로젝트 복사 
   * @param oldPrjctId
   * @return 신규로 생성된 프로젝트 아이디
   */
  String copy(String oldPrjctId);

}
