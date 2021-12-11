package kr.co.sootechsys.scrobot.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.service.PrjctTrgetSysMapngService;

@RestController()
@RequestMapping("/prjct-trget-sys-mapngs")
public class PrjctTrgetSysMapngRestController {
  private PrjctTrgetSysMapngService service;

  public PrjctTrgetSysMapngRestController(PrjctTrgetSysMapngService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "등록")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody PrjctTrgetSysMapngDto dto) {
    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }
}
