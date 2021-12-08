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
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchStplatDto;
import kr.co.sootechsys.scrobot.domain.StplatDto;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.service.StplatService;

@RestController
@RequestMapping("/stplats")
public class StplatRestController {

  private StplatService service;

  public StplatRestController(StplatService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "등록")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody StplatDto dto) {
    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }

  @PutMapping()
  @ApiOperation(value = "수정")
  public void update(@RequestBody StplatDto dto) {
    service.update(dto);
  }

  @DeleteMapping("/{stplatId}")
  @ApiOperation(value = "pk로 삭제")
  public void deleteById(@PathVariable Long stplatId) {
    service.deleteById(stplatId);
  }

  @GetMapping("/ts")
  @ApiOperation(value = "ts 코드 생성")
  public ResponseEntity<Map<String, Object>> ts() {
    Map<String, Object> map = Map.of("form", Util.createFormGroupString(StplatDto.class), "item", Util.createItemString(StplatDto.class));
    return ResponseEntity.ok(map);
  }


  @GetMapping()
  @ApiOperation(value = "목록 조회")
  public ResponseEntity<PageableResult> findAll(SearchStplatDto searchDto, Pageable pageable) {
    return ResponseEntity.ok(service.findAll(searchDto, pageable));
  }

  @GetMapping("/{stplatId}")
  @ApiOperation(value = "pk로 조회")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable Long stplatId) {
    return ResponseEntity.ok(Map.of("data", service.findById(stplatId)));
  }

}
