package kr.co.sootechsys.scrobot.controller;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.CmmnCodeDto;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchCmmnCodeDto;
import kr.co.sootechsys.scrobot.misc.Util;
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

  @PutMapping()
  @ApiOperation(value = "pk로 수정")
  public void update(@RequestBody CmmnCodeDto dto) {
    service.update(dto);
  }


  @PutMapping("/parse-excel")
  @ApiOperation(value = "엑셀파일 파싱")
  public ResponseEntity<Map<String, Object>> parseExcel(@RequestPart MultipartFile excelFile) throws Exception {
    return ResponseEntity.ok(Map.of("data", service.parseExcel(excelFile)));
  }


  @DeleteMapping("/{cmmnCodeId}")
  @ApiOperation(value = "pk로 삭제")
  public void deleteById(@PathVariable Long cmmnCodeId) {
    service.deleteById(cmmnCodeId);
  }


  @GetMapping("/ts")
  @ApiOperation(value = "ts 코드 생성")
  public ResponseEntity<Map<String, Object>> ts() {
    Map<String, Object> map = Map.of("form", Util.createFormGroupString(CmmnCodeDto.class), "item", Util.createItemString(CmmnCodeDto.class));
    return ResponseEntity.ok(map);
  }


  @GetMapping("/{cmmnCodeId}")
  @ApiOperation(value = "pk로 조회")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable Long cmmnCodeId) {
    return ResponseEntity.ok(Map.of("data", service.findById(cmmnCodeId)));
  }

  @GetMapping("/by-prnts-cmmn-code")
  @ApiOperation(value = "부모공통코드로 목록조회")
  public ResponseEntity<Map<String, Object>> list(@RequestParam String prntsCmmnCode) {
    return ResponseEntity.ok(Map.of("data", service.findAllByPrntsCmmnCode(prntsCmmnCode)));
  }

  @GetMapping("/{prntsCmmnCode}/{cmmnCode}")
  @ApiOperation(value = "상세조회")
  public ResponseEntity<Map<String, Object>> findByPrntsCmmnCodeAndCmmnCode(@PathVariable String prntsCmmnCode, @PathVariable String cmmnCode) {
    return ResponseEntity.ok(Map.of("data", service.findByPrntsCmmnCodeAndCmmnCode(prntsCmmnCode, cmmnCode)));
  }

  @GetMapping()
  @ApiOperation(value = "목록 조회")
  public ResponseEntity<PageableResult> findAll(SearchCmmnCodeDto searchDto, Pageable pageable) {
    return ResponseEntity.ok(service.findAll(searchDto, pageable));
  }

}
