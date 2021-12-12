package kr.co.sootechsys.scrobot.controller;

import java.io.IOException;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;
import kr.co.sootechsys.scrobot.domain.SearchPrjctCmmnCodeDto;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.service.PrjctCmmnCodeService;

@RestController
@RequestMapping("/prjct-cmmn-codes")
public class PrjctCmmnCodeRestController {
  private PrjctCmmnCodeService service;

  public PrjctCmmnCodeRestController(PrjctCmmnCodeService service) {
    this.service = service;
  }

  @PostMapping()
  public ResponseEntity<Map<String, Object>> regist(@RequestBody PrjctCmmnCodeDto dto) {
    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }

  @PostMapping("/bulk")
  public ResponseEntity<Map<String, Object>> registBulk(@RequestBody List<PrjctCmmnCodeDto> dtos) {
    return ResponseEntity.ok(Map.of("data", service.regist(dtos)));
  }

  @PutMapping()
  public void update(@RequestBody PrjctCmmnCodeDto dto) {
    service.update(dto);
  }

  @PutMapping("/excel")
  public ResponseEntity<Map<String, Object>> uploadExcel(@RequestParam MultipartFile file) throws IOException {
    return ResponseEntity.ok(Map.of("data", service.parseExcel(file)));
  }

  @DeleteMapping("/{prjctCmmnCodeId}")
  public void deleteById(@PathVariable Long prjctCmmnCodeId) {
    service.deleteById(prjctCmmnCodeId);
  }

  @GetMapping("/ts")
  @ApiOperation(value = "ts 코드 생성")
  public ResponseEntity<Map<String, Object>> ts() {
    Map<String, Object> map = Map.of("form", Util.createFormGroupString(PrjctCmmnCodeDto.class), "item",
        Util.createItemString(PrjctCmmnCodeDto.class));
    return ResponseEntity.ok(map);
  }

  @GetMapping("/by-cmmn-code")
  public ResponseEntity<Map<String, Object>> findByCmmnCode(@RequestParam String cmmnCode) {
    return ResponseEntity.ok(Map.of("data", service.findByCmmnCode(cmmnCode)));
  }

  @GetMapping("/{prjctCmmnCodeId}")
  @ApiOperation(value = "pk로 조회")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable Long prjctCmmnCodeId) {
    return ResponseEntity.ok(Map.of("data", service.findById(prjctCmmnCodeId)));
  }

  @GetMapping()
  public ResponseEntity<PageableResult> findAll(SearchPrjctCmmnCodeDto searchDto,
      Pageable pageable) {
    return ResponseEntity.ok(service.findAll(searchDto, pageable));
  }

  @GetMapping("/by-prjct-and-prnts-cmmn-code")
  public ResponseEntity<Map<String, Object>> findAllByPrjctIdAndPrntsCmmnCode(@RequestParam String prjctId,
      @RequestParam String prntsCmmnCode) {
    return ResponseEntity
        .ok(Map.of("data", service.findAllByPrjctIdAndPrntsCmmnCode(prjctId, prntsCmmnCode)));
  }
}
