package kr.co.sootechsys.scrobot.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchSecsnDto;
import kr.co.sootechsys.scrobot.domain.SecsnDto;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.service.SecsnService;

@RestController
@RequestMapping("/secsns")
public class SecsnRestController {

  private SecsnService service;

  public SecsnRestController(SecsnService service) {
    this.service = service;
  }

  @GetMapping("/ts")
  @ApiOperation(value = "ts 코드 생성")
  public ResponseEntity<Map<String, Object>> ts() {
    Map<String, Object> map = Map.of("form", Util.createFormGroupString(SecsnDto.class), "item", Util.createItemString(SecsnDto.class));
    return ResponseEntity.ok(map);
  }

  @GetMapping()
  @ApiOperation(value = "목록 조회")
  public ResponseEntity<PageableResult> findAll(SearchSecsnDto searchDto, Pageable pageable) {
    return ResponseEntity.ok(service.findAll(searchDto, pageable));
  }

  @GetMapping("/{secsnId}")
  @ApiOperation(value = " 조회")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable Long secsnId)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    return ResponseEntity.ok(Map.of("data", service.findById(secsnId)));
  }
}
