package kr.co.sootechsys.scrobot.controller;

import java.util.List;
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
import kr.co.sootechsys.scrobot.domain.CompnDto;
import kr.co.sootechsys.scrobot.service.CompnService;


/**
 * 컴포넌트
 */
@RestController
@RequestMapping("/compns")
public class CompnRestController {

  private CompnService service;

  public CompnRestController(CompnService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "컴포넌트 등록", notes = "컴포넌트 등록")
  public void regist(@RequestBody List<CompnDto> dtos) {
    service.regist(dtos);
  }

  @PutMapping()
  @ApiOperation(value = "컴포넌트 수정")
  public void updt(@RequestBody CompnDto dto) {
    service.updt(dto);
  }

  @DeleteMapping("/{compnId}")
  @ApiOperation(value = "컴포넌트 아이디로 삭제")
  public void delete(@PathVariable String compnId) {
    service.delete(compnId);
  }

  @DeleteMapping("/by-scrin")
  @ApiOperation(value = "컴포넌트 아이디로 삭제")
  public void deleteByScrinId(@RequestParam String scrinId) {
    service.deleteByScrinId(scrinId);
  }


  @GetMapping("/{compnId}")
  @ApiOperation(value = "컴포넌트 아이디로 조회")
  public ResponseEntity<Map<String, Object>> detail(@PathVariable String compnId) {
    return ResponseEntity.ok(Map.of("data", service.findById(compnId)));
  }

  @GetMapping("/by-scrin")
  @ApiOperation(value = "화면아이디로 컴포넌트 목록 조회")
  public ResponseEntity<Map<String, Object>> listByScrin(@RequestParam String scrinId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByScrinId(scrinId)));
  }
}
