package kr.co.sootechsys.scrobot.controller;

import java.sql.SQLException;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.service.DeployService;

@RestController
@RequestMapping("/deploys")
public class DeployRestController {

  private DeployService service;

  public DeployRestController(DeployService service) {
    this.service = service;
  }

  @PutMapping()
  @ApiOperation(value = "배포")
  public ResponseEntity<Map<String, Object>> deploy(@RequestParam String prjctId, @RequestParam String trgetSysId) throws SQLException {
    return ResponseEntity.ok(Map.of("data", service.deploy(prjctId, trgetSysId)));
  }
}
