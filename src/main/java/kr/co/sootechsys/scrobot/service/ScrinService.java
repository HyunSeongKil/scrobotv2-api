package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.ScrinDto;

public interface ScrinService {
  String regist(ScrinDto dto);

  void updt(ScrinDto dto);

  void delete(String scrinId);

  ScrinDto find(String scrinId);

  List<ScrinDto> findAllByScrinGroupId(String scrinGroupId);
}
