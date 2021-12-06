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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchUserDto;
import kr.co.sootechsys.scrobot.domain.UserDto;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.service.UserService;

@RestController
@RequestMapping("/users")
@Api(value = "회원 CONTROLLER")
public class UserRestController {

  private UserService service;

  public UserRestController(UserService userService) {
    this.service = userService;
  }

  @PutMapping("/reissue")
  @ApiOperation(value = "토큰 재발급")
  public ResponseEntity<Map<String, Object>> reIssueToken(@RequestBody Map<String, Object> map)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    return ResponseEntity.ok(Map.of("data", service.reIssueToken(map.get("refreshToken").toString())));
  }

  @PutMapping("/signin")
  @ApiOperation(value = "signin")
  public ResponseEntity<Map<String, Object>> signin(@RequestBody UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    return ResponseEntity.ok(Map.of("data", service.signin(dto)));
  }

  @PostMapping("/join")
  @ApiOperation(value = "회원가입")
  public void join(@RequestBody UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    service.join(dto);
  }

  @GetMapping("/ts")
  @ApiOperation(value = "ts")
  public ResponseEntity<Map<String, Object>> ts() {
    return ResponseEntity.ok(Map.of("form", Util.createFormGroupString(UserDto.class), "item", Util.createItemString(UserDto.class)));
  }

  @GetMapping("/dupl")
  @ApiOperation(value = "아이디 중복 검사")
  public ResponseEntity<Map<String, Object>> checkDupl(@RequestParam String userId)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    UserDto dto = service.findById(userId);

    return ResponseEntity.ok(Map.of("data", null == dto ? "N" : "Y"));
  }

  @GetMapping("/by-prjct")
  @ApiOperation(value = "프로젝트아이디로 사용자 목록 조회")
  public ResponseEntity<Map<String, Object>> findAllByPrjctId(@RequestParam String prjctId) {
    return ResponseEntity.ok(Map.of("data", service.findAllByPrjctId(prjctId)));
  }

  @GetMapping("/find-id")
  @ApiOperation(value = "id 찾기")
  public ResponseEntity<Map<String, Object>> findIdByUserNmAndTelno(@RequestParam String userNm, @RequestParam String telno)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    return ResponseEntity.ok(Map.of("data", service.findIdByUserNmAndTelno(userNm, telno)));
  }


  @GetMapping()
  @ApiOperation(value = "목록 조회")
  public ResponseEntity<PageableResult> findAll(SearchUserDto searchDto, Pageable pageable) {
    return ResponseEntity.ok(service.findAll(searchDto, pageable));
  }

  @GetMapping("/{userId}")
  @ApiOperation(value = "상세 조회")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable String userId)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    return ResponseEntity.ok(Map.of("data", service.findById(userId)));
  }
}
