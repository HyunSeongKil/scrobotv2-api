package kr.co.sootechsys.scrobot.service;

import java.util.List;
import kr.co.sootechsys.scrobot.domain.AtchmnflGroupDto;

public interface AtchmnflGroupService {
  Long regist(String prjctId);

  /**
   * 프로젝트 아이디로 목록 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  List<AtchmnflGroupDto> findAllByPrjctId(String prjctId);
}
