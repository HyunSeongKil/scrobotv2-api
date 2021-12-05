package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.SecsnDto;

public interface SecsnService {
  Long regist(SecsnDto dto);

  SecsnDto findById(Long secsnId);

  List<SecsnDto> findAllByUserId(String userId);
}
