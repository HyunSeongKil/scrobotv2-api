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
import kr.co.sootechsys.scrobot.domain.ScrinDto;
import kr.co.sootechsys.scrobot.service.ScrinService;

/**
 * 화면
 */
@RestController
@RequestMapping("/scrins")
@Api(value = "화면 CONTROLLER")
public class ScrinRestController {

  private ScrinService service;

  public ScrinRestController(ScrinService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "화면 등록")
  public void regist(@RequestBody ScrinDto dto) {
    service.regist(dto);
  }

  @PostMapping("/copy")
  @ApiOperation(value = "화면 복사")
  public ResponseEntity<Map<String, Object>> copy(@RequestParam String srcScrinId, @RequestBody ScrinDto trgetDto) {
    return ResponseEntity.ok(Map.of("data", service.copy(srcScrinId, trgetDto)));
  }

  @PutMapping()
  @ApiOperation(value = "화면 수정")
  public void updt(@RequestBody ScrinDto dto) {
    service.updt(dto);
  }

  @DeleteMapping("/{scrinId}")
  @ApiOperation(value = "화면 삭제")
  public void delete(@PathVariable String scrinId) {
    service.delete(scrinId);
  }

  @GetMapping("/{scrinId}")
  @ApiOperation(value = "화면 아이디로 조회")
  public ResponseEntity<Map<String, Object>> get(@PathVariable String scrinId) {
    return ResponseEntity.ok(Map.of("data", service.find(scrinId)));
  }

  @GetMapping("/by-scrin-group")
  @ApiOperation(value = "화면그룹 아이디로 화면 목록 조회")
  public ResponseEntity<Map<String, Object>> list(@RequestParam String scrinGroupId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByScrinGroupId(scrinGroupId)));
  }

  @GetMapping("/by-prjct")
  @ApiOperation(value = "프로젝트 아이디로 화면 목록 조회")
  public ResponseEntity<Map<String, Object>> findAllByPrjctId(@RequestParam String prjctId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByPrjctId(prjctId)));
  }
}
