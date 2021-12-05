package kr.co.sootechsys.scrobot.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.GoodsDto;
import kr.co.sootechsys.scrobot.domain.SearchGoodsDto;

public interface GoodsService {
  Long regist(GoodsDto dto);

  void update(GoodsDto dto);

  void deleteById(Long goodsId);

  GoodsDto findById(Long goodsId);

  Map<String, Object> findAll(SearchGoodsDto searchDto, Pageable pageable);

  @ApiOperation(value = "사용자이읻로 목록 조회")
  List<GoodsDto> findAllByUserId(String userId);
}
