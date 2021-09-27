package kr.co.sootechsys.scrobot.service;

import java.util.List;
import kr.co.sootechsys.scrobot.domain.MenuDto;


/**
 * 메뉴
 */
public interface MenuService {

  /**
   * 등록
   * 
   * @param dto 값
   * @return
   */
  String regist(MenuDto dto);

  /**
   * 수정
   * 
   * @param dto 값
   */
  void updt(MenuDto dto);

  /**
   * 메뉴 아이디로 삭제
   * 
   * @param menuId 메뉴 아이디
   */
  void deleteById(String menuId);

  /**
   * 메뉴 아이디로 1건 조회
   * 
   * @param menuId 메뉴 아이디
   * @return
   */
  MenuDto findById(String menuId);

  /**
   * 프로젝트 아이디로 목록 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  List<MenuDto> findAllByPrjctId(String prjctId);

  void deleteByPrjctId(String prjctId);
}
