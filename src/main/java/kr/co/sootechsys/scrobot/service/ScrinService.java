package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.ScrinDto;

/**
 * 화면
 */
public interface ScrinService {
  /**
   * 등록
   * 
   * @param dto 값
   * @return
   */
  String regist(ScrinDto dto);

  /**
   * 수정
   * 
   * @param dto 값
   */
  void updt(ScrinDto dto);

  /**
   * 삭제
   * 
   * @param scrinId 화면 아이디
   */
  void delete(String scrinId);

  /**
   * 1건 조회
   * 
   * @param scrinId 화면 아이디
   * @return
   */
  ScrinDto find(String scrinId);

  /**
   * 화면 그룹 아이디로 목록 조회
   * 
   * @param scrinGroupId 화면 그룹 아이디
   * @return
   */
  List<ScrinDto> findAllByScrinGroupId(String scrinGroupId);

  /**
   * 프로젝트 아이디로 화면 목록 조회
   */
  List<ScrinDto> findAllByPrjctId(String prjctId);

}
