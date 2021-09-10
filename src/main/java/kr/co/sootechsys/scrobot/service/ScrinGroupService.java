package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.ScrinGroupDto;
import kr.co.sootechsys.scrobot.entity.ScrinGroup;

public interface ScrinGroupService {
  String regist(ScrinGroupDto dto);

  void updt(ScrinGroupDto dto);

  void deleteById(String scrinGroupId);

  ScrinGroupDto find(String scrinGroupId);

  List<ScrinGroupDto> findAllByPrjctId(String prjctId);
}
