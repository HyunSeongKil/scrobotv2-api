package kr.co.sootechsys.scrobot.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import kr.co.sootechsys.scrobot.domain.GoodsDto;
import kr.co.sootechsys.scrobot.domain.SearchGoodsDto;

public interface GoodsService {
  Long regist(GoodsDto dto);

  void update(GoodsDto dto);

  void deleteById(Long goodsId);

  GoodsDto findById(Long goodsId);

  Map<String, Object> findAll(SearchGoodsDto searchDto, Pageable pageable);
}
