package kr.co.sootechsys.scrobot.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.service.BizService;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.MenuService;
import kr.co.sootechsys.scrobot.service.ScrinService;

@RestController
@RequestMapping("/biz")
public class BizRestController {

  private BizService service;
  private CompnService compnService;
  private MenuService menuService;
  private ScrinService scrinService;

  public BizRestController(BizService service, CompnService compnService, MenuService menuService, ScrinService scrinService) {
    this.service = service;
    this.compnService = compnService;
    this.menuService = menuService;
    this.scrinService = scrinService;
  }


  @GetMapping("/{prjctId}/menus")
  @ApiOperation(value = "프로젝트아이디로 메뉴 목록 조회")
  public ResponseEntity<Map<String, Object>> getMenus(@PathVariable String prjctId) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.findAllMenus(prjctId)));
  }


  @GetMapping("/{prjctId}/scrins/{scrinId}")
  @ApiOperation(value = "화면아이디로  화면 조회")
  public ResponseEntity<Map<String, Object>> getScrin(@PathVariable String prjctId, @PathVariable String scrinId) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.findScrin(prjctId, scrinId)));
  }


  @GetMapping("/{prjctId}/compns/by-scrin")
  @ApiOperation(value = "화면아이디로  콤포넌트 목록 조회")
  public ResponseEntity<Map<String, Object>> getCompns(@PathVariable String prjctId, @RequestParam String scrinId) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.findAllCompns(prjctId, scrinId)));
  }
}
