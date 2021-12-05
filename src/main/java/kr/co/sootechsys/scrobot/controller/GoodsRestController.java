package kr.co.sootechsys.scrobot.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.GoodsDto;
import kr.co.sootechsys.scrobot.domain.SearchGoodsDto;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.service.GoodsService;

@RestController
@RequestMapping("/goods")
public class GoodsRestController {
  private GoodsService service;

  public GoodsRestController(GoodsService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "등록")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody GoodsDto dto) {
    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }

  @PutMapping()
  @ApiOperation(value = "수정")
  public void update(@RequestBody GoodsDto dto) {
    service.update(dto);
  }

  @DeleteMapping("/{goodsId}")
  @ApiOperation(value = "삭제")
  public void deleteById(@PathVariable Long goodsId) {
    service.deleteById(goodsId);
  }

  @GetMapping("/ts")
  @ApiOperation(value = "ts")
  public ResponseEntity<Map<String, Object>> ts() {
    return ResponseEntity
        .ok(Map.of("form", Util.createFormGroupString(GoodsDto.class), "item", Util.createItemString(GoodsDto.class)));
  }

  @GetMapping("/{goodsId}")
  @ApiOperation(value = "pk로 조회")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable Long goodsId) {
    return ResponseEntity.ok(Map.of("data", service.findById(goodsId)));
  }

  @GetMapping()
  @ApiOperation(value = "목록 조회")
  public ResponseEntity<Map<String, Object>> findAll(SearchGoodsDto searchDto, Pageable pageable) {
    return ResponseEntity.ok(service.findAll(searchDto, pageable));
  }
}
