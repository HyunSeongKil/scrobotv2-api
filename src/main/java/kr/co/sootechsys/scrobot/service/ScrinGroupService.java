package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.ScrinGroupDto;
import kr.co.sootechsys.scrobot.entity.ScrinGroup;

/**
 * 화면 그룹
 */
@Deprecated(since = "1128")
public interface ScrinGroupService {
  /**
   * 등록
   * 
   * @param dto 값
   * @return
   */
  String regist(ScrinGroupDto dto);

  /**
   * 수정
   * 
   * @param dto 값
   */
  void updt(ScrinGroupDto dto);

  /**
   * 삭제
   * 
   * @param scrinGroupId 화면 그룹 아이디
   */
  void deleteById(String scrinGroupId);

  /**
   * 1건 조회
   * 
   * @param scrinGroupId 화면 그룹 아이디
   * @return
   */
  ScrinGroupDto find(String scrinGroupId);

  /**
   * 프로젝트 아이디로 목록 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  List<ScrinGroupDto> findAllByPrjctId(String prjctId);

}
