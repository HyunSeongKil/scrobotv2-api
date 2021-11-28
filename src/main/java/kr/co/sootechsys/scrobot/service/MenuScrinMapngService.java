package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.MenuScrinMapngDto;

public interface MenuScrinMapngService {
  Long regist(MenuScrinMapngDto dto);

  void deleteById(Long menuScrinMapngId);

  List<MenuScrinMapngDto> findAllByMenuId(String menuId);

  /**
   * 메뉴아이디로 매핑 정보 존재하는지 여부 확인
   * 
   * @param menuId 메뉴아이디
   * @return
   */
  boolean existsByMenuId(String menuId);

  /**
   * 메뉴 아이디로 삭제
   * 
   * @param menuId 메뉴아이디
   */
  void deleteByMenuId(String menuId);

  /**
   * 수정
   * 
   * @param dto 값
   */
  void update(MenuScrinMapngDto dto);
}
