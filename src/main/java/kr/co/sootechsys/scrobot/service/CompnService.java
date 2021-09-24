package kr.co.sootechsys.scrobot.service;

import java.util.List;
import kr.co.sootechsys.scrobot.domain.CompnDto;


/**
 * 콤포넌트
 */
public interface CompnService {

  /**
   * 등록
   * 
   * @param dto 값
   * @return
   */
  String regist(CompnDto dto);

  /**
   * 수정
   * 
   * @param dto 값
   */
  void updt(CompnDto dto);

  /**
   * 삭제
   * 
   * @param compnId 콤포넌트 아이디
   */
  void delete(String compnId);

  /**
   * 콤포넌트 아이디로 1건 조회
   * 
   * @param compnId 콤포넌트 아이디
   * @return
   */
  CompnDto findById(String compnId);


  /**
   * 화면 아이디로 목록 조회
   * 
   * @param scrinId 화면 아이디
   * @return
   */
  List<CompnDto> findAllByScrinId(String scrinId);
}
