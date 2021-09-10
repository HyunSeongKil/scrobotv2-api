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

import kr.co.sootechsys.scrobot.domain.ScrinDto;
import kr.co.sootechsys.scrobot.domain.ScrinGroupDto;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;
import kr.co.sootechsys.scrobot.service.ScrinService;

@RestController
@RequestMapping("/scrins")
public class ScrinRestController {

  private ScrinService service;

  public ScrinRestController(ScrinService service){
    this.service = service;
  }
  
  @PostMapping()
  public void regist(@RequestBody ScrinDto dto){
    service.regist(dto);
  }

  @PutMapping()
  public void updt(@RequestBody ScrinDto dto){
    service.updt(dto);
  }

  @DeleteMapping("/{scrinId}")
  public void delete(@PathVariable String scrinId){
    service.delete(scrinId);
  }

  @GetMapping("/{scrinId}")
  public ResponseEntity<Map<String,Object>> get(@PathVariable String scrinId){
    return ResponseEntity.ok(Map.of("data", service.find(scrinId)));
  }

  @GetMapping("/by-scrin-group")
  public ResponseEntity<Map<String,Object>> list(@RequestParam String scrinGroupId){
    return ResponseEntity.ok(Map.of("data", service.findAllByScrinGroupId(scrinGroupId)));
  }
}
