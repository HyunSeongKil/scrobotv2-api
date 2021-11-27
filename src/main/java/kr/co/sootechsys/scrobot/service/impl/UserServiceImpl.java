package kr.co.sootechsys.scrobot.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import com.jayway.jsonpath.Option;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.UserDto;
import kr.co.sootechsys.scrobot.entity.User;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.UserRepository;
import kr.co.sootechsys.scrobot.service.JwtService;
import kr.co.sootechsys.scrobot.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Api(value = "회원 서비스")
public class UserServiceImpl implements UserService {

  @Value("${app.secret.key}")
  private String secretKey;

  private UserRepository repo;
  private JwtService jwtService;

  public UserServiceImpl(UserRepository repo, JwtService jwtService) {
    this.repo = repo;
    this.jwtService = jwtService;
  }

  User toEntity(UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    User e = User.builder().password(Util.encodeAes(secretKey, dto.getPassword())).userId(dto.getUserId())
        .userNm(dto.getUserNm()).registDt(new Date()).telno(dto.getTelno()).build();

    return e;
  }

  UserDto toDto(User e) {
    return UserDto.builder().userId(e.getUserId()).userNm(e.getUserNm()).password(e.getPassword())
        .registDt(e.getRegistDt()).lastLoginDt(e.getLastLoginDt()).sttusCode(e.getSttusCode())
        .telno(e.getTelno()).build();
  }

  @Override
  @Transactional
  public Map<String, Object> signin(UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    Optional<User> opt = repo.findById(dto.getUserId());
    if (opt.isEmpty()) {
      // 회원 없음
      return Map.of("e", "E-USER_ID");
    }

    UserDto mbDto = toDto(opt.get());
    String password = Util.encodeAes(secretKey, dto.getPassword());
    log.debug("{} {}", password, Util.decodeAes(secretKey, password));

    if (!mbDto.getPassword().equals(password)) {
      // 비번 틀림
      return Map.of("e", "E-PASSWORD");
    }

    // TODO 상태 점검

    // 마지막 로그인 일시 저장
    User e = opt.get();
    e.setLastLoginDt(new Date());
    repo.save(e);

    // jwt 생성
    String jwt = jwtService.createToken(mbDto, 1000 * 60L * 10L); // 10분
    String refreshToken = jwtService.createToken(mbDto, 1000 * 60L * 60L * 24L); // 1day
    log.debug("{} {}", jwt, jwtService.getUserId(jwt));

    return Map.of("accessToken", jwt, "refreshToken", refreshToken);
  }

  @Override
  public void join(UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    repo.save(toEntity(dto));

  }

  @Override
  public UserDto findById(String userId) {
    Optional<User> opt = repo.findById(userId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;

  }

  @Override
  public String reIssueToken(String refreshToken) {
    boolean b = jwtService.varifyToken(refreshToken);
    if (!b) {
      throw new RuntimeException("");
    }

    String userId = jwtService.getUserId(refreshToken);
    UserDto dto = findById(userId);
    String jwt = jwtService.createToken(dto, 1000 * 60L * 10L); // 10분

    return jwt;
  }

  @Override
  public List<UserDto> findAllByPrjctId(String prjctId) {
    List<UserDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
