package kr.co.sootechsys.scrobot.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.MenuScrinMapngDto;
import kr.co.sootechsys.scrobot.service.MenuScrinMapngService;

@RestController
@RequestMapping("/menu-scrin-mapngs")
public class MenuScrinMapngRestController {
  private MenuScrinMapngService service;

  public MenuScrinMapngRestController(MenuScrinMapngService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "등록")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody MenuScrinMapngDto dto) {
    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }

  @PutMapping()
  @ApiOperation(value = "수정")
  public void update(@RequestBody MenuScrinMapngDto dto) {
    service.update(dto);
  }

  @DeleteMapping("/by-menu")
  @ApiOperation(value = "메뉴아이디로 삭제")
  public void deleteByMenuId(@RequestParam String menuId) {
    service.deleteByMenuId(menuId);
  }

  @GetMapping("/by-menu")
  @ApiOperation(value = "메뉴아이디로 목록 조회")
  public ResponseEntity<Map<String, Object>> findAllByMenuId(@RequestParam String menuId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByMenuId(menuId)));
  }

  @GetMapping("/exists")
  @ApiOperation(value = "매핑정보 존재하는지 여부")
  public ResponseEntity<Map<String, Object>> existsByMenuId(@RequestParam String menuId) {
    return ResponseEntity.ok(Map.of("data", service.existsByMenuId(menuId)));
  }
}
