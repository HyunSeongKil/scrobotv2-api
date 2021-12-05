package kr.co.sootechsys.scrobot.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.CmmnCodeDto;
import kr.co.sootechsys.scrobot.service.CmmnCodeService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

  @PostMapping()
  @ApiOperation(value = "등록")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody CmmnCodeDto dto) {
    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }

  @PostMapping("/bulk")
  @ApiOperation(value = "대용량으로 등록")
  public ResponseEntity<Map<String, Object>> registByBulk(@RequestBody List<CmmnCodeDto> dtos) {
    return ResponseEntity.ok(Map.of("data", service.registByBulk(dtos)));
  }

  @GetMapping("/{prntsCmmnCode}")
  @ApiOperation(value = "부모공통코드로 목록조회")
  public ResponseEntity<Map<String, Object>> list(@PathVariable String prntsCmmnCode) {
    return ResponseEntity.ok(Map.of("data", service.findAllByPrntsCmmnCode(prntsCmmnCode)));
  }

  @GetMapping("/{prntsCmmnCode}/{cmmnCode}")
  @ApiOperation(value = "상세조회")
  public ResponseEntity<Map<String, Object>> findByPrntsCmmnCodeAndCmmnCode(@PathVariable String prntsCmmnCode,
      @PathVariable String cmmnCode) {
    return ResponseEntity.ok(Map.of("data", service.findByPrntsCmmnCodeAndCmmnCode(prntsCmmnCode, cmmnCode)));
  }

  @PutMapping("/parse-excel")
  @ApiOperation(value = "엑셀파일 파싱")
  public ResponseEntity<Map<String, Object>> parseExcel(@RequestPart MultipartFile excelFile) throws Exception {
    return ResponseEntity.ok(Map.of("data", service.parseExcel(excelFile)));
  }
}
