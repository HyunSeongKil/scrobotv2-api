package kr.co.sootechsys.scrobot.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sootechsys.scrobot.domain.UserGoodsMapngDto;
import kr.co.sootechsys.scrobot.entity.UserGoodsMapng;
import kr.co.sootechsys.scrobot.persistence.UserGoodsMapngRepository;
import kr.co.sootechsys.scrobot.service.UserGoodsMapngService;

@Service
public class UserGoodsMapngServiceImpl implements UserGoodsMapngService {

  private UserGoodsMapngRepository repo;

  public UserGoodsMapngServiceImpl(UserGoodsMapngRepository repo) {
    this.repo = repo;
  }

  UserGoodsMapng toEntity(UserGoodsMapngDto dto) {
    UserGoodsMapng e = UserGoodsMapng.builder().goodsId(dto.getGoodsId()).userId(dto.getUserId()).build();

    if (null == dto.getUserGoodsMapngId() || 0 > dto.getUserGoodsMapngId()) {
      e.setRegistDt(new Date());
    } else {
      e.setUserGoodsMapngId(dto.getUserGoodsMapngId());
    }

    return e;
  }

  UserGoodsMapngDto toDto(UserGoodsMapng e) {
    return UserGoodsMapngDto.builder().goodsId(e.getGoodsId()).registDt(e.getRegistDt())
        .userGoodsMapngId(e.getUserGoodsMapngId()).userId(e.getUserId()).build();
  }

  @Override
  @Transactional
  public Long regist(UserGoodsMapngDto dto) {
    return repo.save(toEntity(dto)).getUserGoodsMapngId();
  }

  @Override
  @Transactional
  public void deleteById(Long userGoodsMapngId) {
    repo.deleteById(userGoodsMapngId);
  }

}
