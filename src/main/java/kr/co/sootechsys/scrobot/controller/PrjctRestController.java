package kr.co.sootechsys.scrobot.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.PrjctDto;
import kr.co.sootechsys.scrobot.domain.ScrinGroupDto;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.JwtService;
import kr.co.sootechsys.scrobot.service.PrjctService;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;
import kr.co.sootechsys.scrobot.service.ScrinService;

/**
 * 프로젝트
 */
@RestController
@RequestMapping("/prjcts")
@Api(value = "프로젝트 CONTROLLER")
public class PrjctRestController {

  private PrjctService service;
  private JwtService jwtService;
  private ScrinGroupService scrinGroupService;
  private ScrinService scrinService;
  private CompnService compnService;

  public PrjctRestController(JwtService jwtService, PrjctService service, ScrinGroupService scrinGroupService,
      ScrinService scrinService, CompnService compnService) {
    this.service = service;
    this.jwtService = jwtService;
    this.scrinGroupService = scrinGroupService;
    this.scrinService = scrinService;
    this.compnService = compnService;
  }

  @PostMapping()
  @ApiOperation(value = "프로젝트 등록")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody PrjctDto dto) {
    return ResponseEntity.ok(Map.of("data", service.regist(dto)));
  }

  @PostMapping("/copy")
  @ApiOperation(value = "프로젝트 복사")
  public ResponseEntity<Map<String, Object>> copy(@RequestBody PrjctDto dto) {
    return ResponseEntity.ok(Map.of("data", service.copy(dto.getPrjctId())));
  }

  @PutMapping()
  @ApiOperation(value = "프로젝트 수정")
  public ResponseEntity<Map<String, Object>> updt(@RequestBody PrjctDto dto) {
    service.updt(dto);

    return ResponseEntity.ok().build();
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
  public ResponseEntity<Map<String, Object>> list(HttpServletRequest request, @RequestParam String userId) {
    if (!jwtService.getUserId(request).equals(userId)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(Map.of("data", service.findAllByUserId(userId)));
  }
}
