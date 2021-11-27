package kr.co.sootechsys.scrobot.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import kr.co.sootechsys.scrobot.domain.UserDto;

public interface UserService {
  Map<String, Object> signin(UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

  void join(UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

  UserDto findById(String userId);

  /**
   * 토큰 재발급
   * 
   * @param refreshToken refresh token
   */
  String reIssueToken(String refreshToken);

  /**
   * 프로젝트아이디로 사용자 목록 조회
   * 
   * @param prjctId 프로젝트아이디
   * @return 사용자 목록
   */
  List<UserDto> findAllByPrjctId(String prjctId);
}
