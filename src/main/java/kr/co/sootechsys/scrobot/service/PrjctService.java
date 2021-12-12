package kr.co.sootechsys.scrobot.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import kr.co.sootechsys.scrobot.domain.PrjctDto;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;

/**
 * 프로젝트
 */
public interface PrjctService {
  /**
   * 등록
   * 
   * @param dto 값
   * @return
   */
  String regist(PrjctDto dto);

  /**
   * 수정
   * 
   * @param dto 값
   */
  void updt(PrjctDto dto);

  /**
   * 삭제
   * 
   * @param prjctId 프로젝트 아이디
   */
  void delete(String prjctId);

  /**
   * 프로젝트 아이디로 1건 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  PrjctDto findById(String prjctId);

  /**
   * 사용자 아이디로 목록 조회 (프로젝트-사용자 매핑 테이블과 조인)
   * 
   * @param userId 사용자 아이디
   * @return
   */
  List<PrjctDto> findAllByUserId(String userId);

  /**
   * 프로젝트 복사
   * 
   * @param oldPrjctId
   * @return 신규로 생성된 프로젝트 아이디
   */
  String copy(String oldPrjctId);

  void update(PrjctDto dto, TrgetSysDto trgetSysDto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

  String regist(PrjctDto dto, TrgetSysDto trgetSysDto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

}
