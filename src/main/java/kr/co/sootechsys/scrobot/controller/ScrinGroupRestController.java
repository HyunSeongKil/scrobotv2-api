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
import kr.co.sootechsys.scrobot.domain.ScrinGroupDto;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;


/**
 * 화면 그룹
 */
@RestController
@RequestMapping("/scrin-groups")
public class ScrinGroupRestController {

  private ScrinGroupService service;

  public ScrinGroupRestController(ScrinGroupService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "화면그룹 등록")
  public void regist(@RequestBody ScrinGroupDto dto) {
    service.regist(dto);
  }

  @PutMapping()
  @ApiOperation(value = "화면그룹 수정")
  public void updt(@RequestBody ScrinGroupDto dto) {
    service.updt(dto);
  }

  @DeleteMapping("/{scrinGroupId}")
  @ApiOperation(value = "화면그룹 아이디로 삭제")
  public void delete(@PathVariable String scrinGroupId) {
    service.deleteById(scrinGroupId);
  }

  @GetMapping("/{scrinGroupId}")
  @ApiOperation(value = "화면그룹 아이디로 조회")
  public ResponseEntity<Map<String, Object>> get(@PathVariable String scrinGroupId) {
    return ResponseEntity.ok(Map.of("data", service.find(scrinGroupId)));
  }

  @GetMapping("/by-prjct")
  @ApiOperation(value = "프로젝트 아이디로 화면그룹 목록 조회")
  public ResponseEntity<Map<String, Object>> list(@RequestParam String prjctId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByPrjctId(prjctId)));
  }
}
