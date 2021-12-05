package kr.co.sootechsys.scrobot.service;

import kr.co.sootechsys.scrobot.domain.UserGoodsMapngDto;

public interface UserGoodsMapngService {
  Long regist(UserGoodsMapngDto dto);

  void deleteById(Long userGoodsMapngId);

}
