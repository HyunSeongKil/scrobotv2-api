package kr.co.sootechsys.scrobot.service.impl;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.sootechsys.scrobot.domain.UserDto;
import kr.co.sootechsys.scrobot.service.JwtService;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.annotations.Api;

@Service
@Slf4j
@Api(value = "jwt 서비스")
public class JwtServiceImpl implements JwtService {

  @Value("${app.secret.key}")
  private String secretKey;

  private Key key;

  @PostConstruct
  private void init() {

    //
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public String createToken(UserDto dto) {
    Map<String, Object> header = new HashMap<>();
    header.put("typ", "JWT");
    header.put("alg", "HS256");

    // Long expiredTime = 1000 * 60L * 60L * 2L;
    Long expiredTime = 1000 * 60L * 10L; // 1분
    Date exp = new Date();
    exp.setTime(exp.getTime() + expiredTime);

    Map<String, Object> payload = new HashMap<>();
    payload.put("userId", dto.getUserId());
    payload.put("userNm", dto.getUserNm());

    // 초단위로 설정되어야 함 (밀리초단위 아님)
    payload.put("exp", (System.currentTimeMillis() / 1000) + (expiredTime / 1000));

    String jwt = Jwts.builder().setSubject("user").setExpiration(exp).setHeader(header).setClaims(payload).signWith(key).compact();

    return jwt;
  }

  @Override
  public boolean varifyToken(String jwt) {
    log.debug("{}", key);
    log.debug("{}", jwt);

    boolean b = false;

    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
      b = true;
    } catch (Exception e) {
      log.error("{}", e);
    }

    return b;
  }

  @Override
  public String getToken(HttpServletRequest request) {
    String authorization = request.getHeader(HEADER_NAME);

    if (null == authorization || !authorization.startsWith(TOKEN_PREFIX)) {
      log.debug("NULL AUTHORIZATION - {} {}", request.getMethod(), request.getRequestURI());
      return null;
    }

    return authorization.replaceAll(TOKEN_PREFIX, "");

  }

  @Override
  public String getUserId(HttpServletRequest request) {
    String token = getToken(request);
    return getUserId(token);
  }

  private Claims getClaims(String jwt) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
    log.debug("{}", claims);

    return claims;

  }

  @Override
  public String getUserId(String jwt) {
    return getClaims(jwt).getOrDefault("userId", "").toString();
  }

}
