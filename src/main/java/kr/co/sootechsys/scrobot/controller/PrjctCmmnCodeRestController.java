package kr.co.sootechsys.scrobot.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;
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

  @PutMapping()
  public void update(@RequestBody PrjctCmmnCodeDto dto) {
    service.update(dto);
  }

  @PutMapping("/excel")
  public ResponseEntity<Map<String, Object>> uploadExcel(@RequestParam MultipartFile mf) throws IOException {
    return ResponseEntity.ok(Map.of("data", service.parseExcel(mf)));
  }

  @DeleteMapping("/{prjctCmmnCodeId}")
  public void deleteById(@RequestParam Long prjctCmmnCodeId) {
    service.deleteById(prjctCmmnCodeId);
  }

  @GetMapping("/by-cmmn-code")
  public ResponseEntity<Map<String, Object>> findByCmmnCode(@RequestParam String cmmnCode) {
    return ResponseEntity.ok(Map.of("data", service.findByCmmnCode(cmmnCode)));
  }

  @GetMapping("/by-prjct")
  public ResponseEntity<Map<String, Object>> findAllByPrjctId(@RequestParam String prjctId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByPrjctId(prjctId)));
  }

  @GetMapping("/by-prjct-and-prnts-prjct-cmmn-code")
  public ResponseEntity<Map<String, Object>> findAllByPrjctIdAndPrntsCmmnCode(@RequestParam String prjctId,
      @RequestParam String prntsPrjctCmmnCode) {
    return ResponseEntity
        .ok(Map.of("data", service.findAllByPrjctIdAndPrntsPrjctCmmnCode(prjctId, prntsPrjctCmmnCode)));
  }
}
