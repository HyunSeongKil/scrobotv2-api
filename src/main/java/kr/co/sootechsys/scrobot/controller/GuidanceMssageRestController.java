package kr.co.sootechsys.scrobot.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.GuidanceMssageDto;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.service.GuidanceMssageService;

@RestController
@RequestMapping("/guidance-mssages")
@Api(value = "안내 메시지 controller")
public class GuidanceMssageRestController {
  private GuidanceMssageService service;

  public GuidanceMssageRestController(GuidanceMssageService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "등록")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody GuidanceMssageDto dto) {
    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }

  @PutMapping()
  @ApiOperation(value = "수정")
  public void update(@RequestBody GuidanceMssageDto dto) {
    service.update(dto);
  }

  @DeleteMapping("/{guidanceMssageId}")
  @ApiOperation(value = "삭제")
  public void deleteById(@PathVariable Long guidanceMssageId) {
    service.deleteById(guidanceMssageId);
  }

  @GetMapping("/ts")
  @ApiOperation(value = "ts 코드 생성")
  public ResponseEntity<Map<String, Object>> ts() {
    Map<String, Object> map = Map.of("form", Util.createFormGroupString(GuidanceMssageDto.class), "item",
        Util.createItemString(GuidanceMssageDto.class));
    return ResponseEntity.ok(map);
  }

  @GetMapping("/by-prjct")
  @ApiOperation(value = "프로젝트아이디로 목록 조회")
  public ResponseEntity<Map<String, Object>> findAllByPrjctId(@RequestParam String prjctId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByPrjctId(prjctId)));
  }

}
