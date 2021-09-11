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
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.service.MenuService;

@RestController
@RequestMapping("/menus")
public class MenuRestController {

  private MenuService service;

  public MenuRestController(MenuService service) {
    this.service = service;
  }

  @PostMapping()
  public void regist(@RequestBody MenuDto dto) {
    service.regist(dto);
  }

  @PutMapping()
  public void updt(@RequestBody MenuDto dto) {
    service.updt(dto);
  }

  @DeleteMapping("/{menuId}")
  public void delete(@PathVariable String menuId) {
    service.deleteById(menuId);
  }

  @GetMapping("/{menuId}")
  public ResponseEntity<Map<String, Object>> detail(@PathVariable String menuId) {
    return ResponseEntity.ok(Map.of("data", service.findById(menuId)));
  }

  @GetMapping("/by-prjct")
  public ResponseEntity<Map<String, Object>> listByPrjctId(@RequestParam String prjctId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByPrjctId(prjctId)));
  }

}
