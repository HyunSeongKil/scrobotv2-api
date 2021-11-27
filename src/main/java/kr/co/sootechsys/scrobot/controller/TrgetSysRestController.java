package kr.co.sootechsys.scrobot.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.service.TrgetSysService;

/**
 * 대상 시스템
 */
@RestController
@RequestMapping("trget-syss")
@Api(value = "대상 시스템 CONTROLLER")
public class TrgetSysRestController {

  private TrgetSysService service;

  public TrgetSysRestController(TrgetSysService service) {
    this.service = service;
  }

  @PostMapping()
  @ApiOperation(value = "등록")
  public void regist(@RequestBody TrgetSysDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    service.regist(dto);
  }

  @PutMapping()
  @ApiOperation(value = "수정")
  public void updt(@RequestBody TrgetSysDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    service.updt(dto);
  }

  @DeleteMapping("/{trgetSysId}")
  @ApiOperation(value = "삭제")
  public void delete(@PathVariable String trgetSysId) {
    service.delete(trgetSysId);
  }

  @GetMapping("/{trgetSysId}")
  @ApiOperation(value = "대상시스템 아이디 상세 조회")
  public ResponseEntity<Map<String, Object>> detail(@PathVariable String trgetSysId) {
    return ResponseEntity.ok(Map.of("data", service.findById(trgetSysId)));
  }

  @GetMapping()
  @ApiOperation(value = "목록 조회")
  public ResponseEntity<Map<String, Object>> list() {
    return ResponseEntity.ok(Map.of("data", service.findAll()));
  }
}
