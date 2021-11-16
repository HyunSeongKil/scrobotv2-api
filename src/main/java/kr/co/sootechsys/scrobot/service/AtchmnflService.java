package kr.co.sootechsys.scrobot.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import kr.co.sootechsys.scrobot.domain.AtchmnflDto;

public interface AtchmnflService {
  Long regist(AtchmnflDto dto);

  Long regist(Long atchmnflGroupId, MultipartFile file) throws IllegalStateException, IOException;

  AtchmnflDto findById(Long atchmnflId);

  File getFile(Long atchmnflId);

  /**
   * 프로젝트 아이디로 목록 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  List<AtchmnflDto> findAllByPrjctId(String prjctId);
}
