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
import kr.co.sootechsys.scrobot.service.BizService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/biz")
@Slf4j
public class BizRestController {

  private BizService service;

  public BizRestController(BizService service) {
    this.service = service;
  }


  @PostMapping("/{prjctId}/{scrinId}")
  @ApiOperation(value = "등록")
  public void regist(@PathVariable String prjctId, @PathVariable String scrinId, @RequestBody Map<String, Object> map) throws SQLException {
    service.regist(prjctId, scrinId, map);
  }

  @PutMapping("/{prjctId}/{scrinId}")
  @ApiOperation(value = "수정")
  public void updt(@PathVariable String prjctId, @PathVariable String scrinId, @RequestBody Map<String, Object> map) throws SQLException {
    service.updt(prjctId, scrinId, map);
  }

  @DeleteMapping("/{prjctId}/{scrinId}/{id}")
  @ApiOperation(value = "삭제")
  public void delete(@PathVariable String prjctId, @PathVariable String scrinId, @PathVariable String id) throws SQLException {
    service.delete(prjctId, scrinId, id);
  }


  @GetMapping("/{prjctId}/{scrinId}/{id}")
  @ApiOperation(value = "상세조회")
  public ResponseEntity<Map<String, Object>> detail(@PathVariable String prjctId, @PathVariable String scrinId, @PathVariable String id) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.findById(prjctId, scrinId, id)));
  }

  @GetMapping("/{prjctId}/{scrinId}")
  @ApiOperation(value = "목록 조회")
  public ResponseEntity<Map<String, Object>> list(@PathVariable String prjctId, @PathVariable String scrinId) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.findAll(prjctId, scrinId)));
  }


  @GetMapping("/{prjctId}/menus")
  @ApiOperation(value = "프로젝트아이디로 메뉴 목록 조회")
  public ResponseEntity<Map<String, Object>> getMenus(@PathVariable String prjctId) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.findAllMenus(prjctId)));
  }

  @GetMapping("/{prjctId}/{scrinGroupId}/scrins")
  @ApiOperation(value = "화면그룹아이디로 화면목록 목록 조회")
  public ResponseEntity<Map<String, Object>> getScrins(@PathVariable String prjctId, @PathVariable String scrinGroupId) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.findAllScrins(prjctId, scrinGroupId)));
  }



  @GetMapping("/{prjctId}/{scrinId}/scrin")
  @ApiOperation(value = "화면아이디로  화면 조회")
  public ResponseEntity<Map<String, Object>> getScrin(@PathVariable String prjctId, @PathVariable String scrinId) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.findScrin(prjctId, scrinId)));
  }

  @GetMapping("/{prjctId}/{scrinId}/scrin-group")
  @ApiOperation(value = "화면아이디로  화면그룹 조회")
  public ResponseEntity<Map<String, Object>> getScrinGroup(@PathVariable String prjctId, @PathVariable String scrinId) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.findScrinGroup(prjctId, scrinId)));
  }


  @GetMapping("/{prjctId}/{scrinId}/compns")
  @ApiOperation(value = "화면아이디로  콤포넌트 목록 조회")
  public ResponseEntity<Map<String, Object>> getCompns(@PathVariable String prjctId, @PathVariable String scrinId) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.findAllCompns(prjctId, scrinId)));
  }
}
