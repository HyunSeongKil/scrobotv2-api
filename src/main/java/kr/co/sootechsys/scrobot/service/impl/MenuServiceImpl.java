package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.entity.Menu;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.MenuRepository;
import kr.co.sootechsys.scrobot.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

  private MenuRepository repo;

  public MenuServiceImpl(MenuRepository repo) {
    this.repo = repo;
  }

  Menu toEntity(MenuDto dto) {
    Menu e = Menu.builder().build();
    e.setMenuId(Util.getShortUuid());
    e.setMenuNm(dto.getMenuNm());
    e.setMenuOrdrValue(dto.getMenuOrdrValue());
    e.setPrjctId(dto.getPrjctId());
    e.setPrntsMenuId(dto.getPrntsMenuId());
    e.setUrlNm(dto.getUrlNm());
    e.setScrinId(dto.getScrinId());

    return e;
  }

  MenuDto toDto(Menu e) {
    MenuDto dto = MenuDto.builder().build();
    dto.setMenuId(e.getMenuId());
    dto.setMenuNm(e.getMenuNm());
    dto.setMenuOrdrValue(e.getMenuOrdrValue());
    dto.setPrjctId(e.getPrjctId());
    dto.setPrntsMenuId(e.getPrntsMenuId());
    dto.setUrlNm(e.getUrlNm());
    dto.setScrinId(e.getScrinId());

    return dto;
  }

  @Override
  public void deleteByPrjctId(String prjctId) {
    repo.findAllByPrjctId(prjctId).forEach(e->{
      repo.delete(e);
    });
  }

  @Override
  public String regist(MenuDto dto) {
    return repo.save(toEntity(dto)).getMenuId();
  }

  @Override
  public void updt(MenuDto dto) {
    if (null == findById(dto.getMenuId())) {
      return;
    }

    Menu e = toEntity(dto);
    e.setMenuId(dto.getMenuId());

    repo.save(e);
  }

  @Override
  public void deleteById(String menuId) {
    if (null == findById(menuId)) {
      return;
    }

    repo.deleteById(menuId);
  }

  @Override
  public MenuDto findById(String menuId) {
    Optional<Menu> opt = repo.findById(menuId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    //
    return null;
  }

  @Override
  public List<MenuDto> findAllByPrjctId(String prjctId) {
    List<MenuDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

 

}
