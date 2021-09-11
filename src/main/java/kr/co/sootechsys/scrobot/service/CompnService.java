package kr.co.sootechsys.scrobot.service;

import java.util.List;
import kr.co.sootechsys.scrobot.domain.CompnDto;

public interface CompnService {

  String regist(CompnDto dto);

  void updt(CompnDto dto);

  void delete(String compnId);

  CompnDto findById(String compnId);

  List<CompnDto> findAllByScrinId(String scrinId);
}
