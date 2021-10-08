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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.sootechsys.scrobot.domain.UserDto;
import kr.co.sootechsys.scrobot.service.UserService;

@RestController
@RequestMapping("/users")
public class UserRestController {

  private UserService service;

  public UserRestController(UserService userService) {
    this.service = userService;
  }

  @PutMapping("/signin")
  public ResponseEntity<Map<String, Object>> signin(@RequestBody UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    return ResponseEntity.ok(Map.of("data", service.signin(dto)));
  }
}
