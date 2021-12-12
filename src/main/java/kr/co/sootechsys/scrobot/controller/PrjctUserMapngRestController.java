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

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.PrjctUserMapngDto;
import kr.co.sootechsys.scrobot.service.PrjctUserMapngService;

@RestController
@RequestMapping("/prjct-user-mapngs")
public class PrjctUserMapngRestController {

  private PrjctUserMapngService service;

  public PrjctUserMapngRestController(PrjctUserMapngService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "등록")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody PrjctUserMapngDto dto) {
    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }

  @PutMapping("/mngr")
  @ApiOperation(value = "관리자로 수정")
  public void updateToMngr(@RequestBody PrjctUserMapngDto dto) {
    service.updateToMngr(dto);
  }

  @DeleteMapping("/{prjctUserMapngId}")
  @ApiOperation(value = "pk로 삭제")
  public void deleteById(@PathVariable Long prjctUserMapngId) {
    service.deleteById(prjctUserMapngId);
  }

  @GetMapping("/by-prjct")
  @ApiOperation(value = "프로젝트아이디로 목록 조회")
  public ResponseEntity<Map<String, Object>> findAllByPrjctId(@RequestParam String prjctId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByPrjctId(prjctId)));
  }
}
