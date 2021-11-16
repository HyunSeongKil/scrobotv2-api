package kr.co.sootechsys.scrobot.service;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import kr.co.sootechsys.scrobot.domain.AtchmnflDto;

public interface AtchmnflService {
  Long regist(AtchmnflDto dto);

  Long regist(Long atchmnflGroupId, MultipartFile file) throws IllegalStateException, IOException;

  AtchmnflDto findById(Long atchmnflId);

  File getFile(Long atchmnflId);
}
