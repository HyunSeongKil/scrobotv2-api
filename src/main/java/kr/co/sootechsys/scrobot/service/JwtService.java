package kr.co.sootechsys.scrobot.service;

import javax.servlet.http.HttpServletRequest;

import kr.co.sootechsys.scrobot.domain.UserDto;

public interface JwtService {
  public static final String HEADER_NAME = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";

  /**
   * 토큰 생성
   * 
   * @param dto
   * @param expiredTimeMs 만료시간. 밀리초
   * @return
   */
  String createToken(UserDto dto, Long expiredTimeMs);

  /**
   * 토큰 검증
   * 
   * @param jwt
   * @return
   */
  boolean varifyToken(String jwt);

  String getToken(HttpServletRequest req);

  String getUserId(HttpServletRequest req);

  String getUserId(String jwt);
}
