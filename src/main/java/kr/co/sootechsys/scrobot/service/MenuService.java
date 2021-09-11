package kr.co.sootechsys.scrobot.service;

import java.util.List;
import kr.co.sootechsys.scrobot.domain.MenuDto;

public interface MenuService {

  String regist(MenuDto dto);

  void updt(MenuDto dto);

  void deleteById(String menuId);

  MenuDto findById(String menuId);

  List<MenuDto> findAllByPrjctId(String prjctId);
}
