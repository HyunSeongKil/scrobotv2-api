package kr.co.sootechsys.scrobot.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.service.CmmnCodeService;


/**
 * 공통코드
 */
@RestController
@RequestMapping("/cmmn-codes")
@Api(value = "공통코드 CONTROLLER")
public class CmmnCodeRestController {
  private CmmnCodeService service;

  public CmmnCodeRestController(CmmnCodeService service) {
    this.service = service;
  }

  @GetMapping("/{prntsCmmnCode}")
  @ApiOperation(value = "부모공통코드로 목록조회")
  public ResponseEntity<Map<String, Object>> list(@PathVariable String prntsCmmnCode) {
    return ResponseEntity.ok(Map.of("data", service.findAllByPrntsCmmnCode(prntsCmmnCode)));
  }

  @GetMapping("/{prntsCmmnCode}/{cmmnCode}")
  @ApiOperation(value = "상세조회")
  public ResponseEntity<Map<String, Object>> detail(@PathVariable String prntsCmmnCode, @PathVariable String cmmnCode) {
    return ResponseEntity.ok(Map.of("data", service.findByPrntsCmmnCodeAndCmmnCode(prntsCmmnCode, cmmnCode)));
  }
}
