package kr.co.sootechsys.scrobot.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Jwts;
import kr.co.sootechsys.scrobot.domain.UserDto;
import kr.co.sootechsys.scrobot.entity.User;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.UserRepository;
import kr.co.sootechsys.scrobot.service.JwtService;
import kr.co.sootechsys.scrobot.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Value("${app.secret.key}")
  private String secretKey;

  private UserRepository repo;
  private JwtService jwtService;

  public UserServiceImpl(UserRepository repo, JwtService jwtService) {
    this.repo = repo;
    this.jwtService = jwtService;
  }

  UserDto toDto(User e) {
    return UserDto.builder().userId(e.getUserId()).userNm(e.getUserNm()).password(e.getPassword())
        .registDt(e.getRegistDt()).lastLoginDt(e.getLastLoginDt()).sttusCode(e.getSttusCode()).build();
  }

  @Override
  @Transactional
  public String signin(UserDto dto) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException,
      NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    Optional<User> opt = repo.findById(dto.getUserId());
    if (opt.isEmpty()) {
      // 회원 없음
      return "E-USER_ID";
    }

    UserDto mbDto = toDto(opt.get());
    String password = Util.encodeAes(secretKey, dto.getPassword());
    log.debug("{} {}", password, Util.decodeAes(secretKey, password));

    if (!mbDto.getPassword().equals(password)) {
      // 비번 틀림
      return "E-PASSWORD";
    }

    // TODO 상태 점검

    // 마지막 로그인 일시 저장
    User e = opt.get();
    e.setLastLoginDt(new Date());
    repo.save(e);

    // jwt 생성
    String jwt = jwtService.createToken(mbDto);
    log.debug("{} {}", jwt, jwtService.getUserId(jwt));

    return jwt;
  }

}
