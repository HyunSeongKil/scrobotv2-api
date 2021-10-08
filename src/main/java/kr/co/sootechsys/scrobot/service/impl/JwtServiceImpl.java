package kr.co.sootechsys.scrobot.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.sootechsys.scrobot.domain.UserDto;
import kr.co.sootechsys.scrobot.service.JwtService;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
@Slf4j
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

    Map<String, Object> payload = new HashMap<>();
    payload.put("userId", dto.getUserId());
    payload.put("userNm", dto.getUserNm());

    Long expiredTime = 1000 * 60L * 60L * 2L;
    Date exp = new Date();
    exp.setTime(exp.getTime() + expiredTime);

    String jwt = Jwts.builder().setSubject("user").setExpiration(exp).setHeader(header).setClaims(payload).signWith(key)
        .compact();

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
