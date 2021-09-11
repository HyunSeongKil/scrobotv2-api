package kr.co.sootechsys.scrobot.controller;

import java.sql.SQLException;
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
import kr.co.sootechsys.scrobot.domain.PrjctDto;
import kr.co.sootechsys.scrobot.service.PrjctService;


@RestController
@RequestMapping("/prjcts")
public class PrjctRestController {

  private PrjctService service;

  public PrjctRestController(PrjctService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "프로젝트 등록")
  public void regist(@RequestBody PrjctDto dto) {
    service.regist(dto);
  }

  @PutMapping()
  @ApiOperation(value = "프로젝트 수정")
  public void updt(@RequestBody PrjctDto dto) {
    service.updt(dto);
  }


  @DeleteMapping("/{prjctId}")
  @ApiOperation(value = "프로젝트 아이디로 삭제")
  public void delete(@PathVariable String prjctId) {
    service.delete(prjctId);
  }

  @GetMapping("/{prjctId}")
  @ApiOperation(value = "프로젝트 아이디로 조회")
  public ResponseEntity<Map<String, Object>> get(@PathVariable String prjctId) {
    return ResponseEntity.ok(Map.of("data", service.findById(prjctId)));
  }

  @GetMapping("/by-user")
  @ApiOperation(value = "사용자 아이디로 프로젝트 목록 조회")
  public ResponseEntity<Map<String, Object>> list(@RequestParam String userId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByUserId(userId)));
  }
}
