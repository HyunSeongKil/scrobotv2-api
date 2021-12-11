package kr.co.sootechsys.scrobot.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import kr.co.sootechsys.scrobot.domain.TrgetSysDto;

/**
 * 대상 시스템
 */
public interface TrgetSysService {

  /**
   * 등록
   * 
   * @param dto 값
   * @return
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws InvalidAlgorithmParameterException
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws UnsupportedEncodingException
   * @throws InvalidKeyException
   */
  String regist(TrgetSysDto dto) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException,
      NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

  /**
   * 수정
   * 
   * @param dto 값
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws InvalidAlgorithmParameterException
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws UnsupportedEncodingException
   * @throws InvalidKeyException
   */
  void updt(TrgetSysDto dto) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException,
      NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

  /**
   * 삭제
   * 
   * @param trgetSysId 대상 시스템 아이디
   */
  void delete(String trgetSysId);

  /**
   * 1건 조회
   * 
   * @param trgetSysId 대상 시스템 아이디
   * @return
   */
  TrgetSysDto findById(String trgetSysId);

  /**
   * 목록 조회
   * 
   * @return
   */
  List<TrgetSysDto> findAll();

  List<TrgetSysDto> findAllByPrjctId(String prjctId);
}
