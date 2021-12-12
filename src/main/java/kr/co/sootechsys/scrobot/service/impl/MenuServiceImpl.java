package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.el.MethodNotFoundException;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.entity.Menu;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.MenuRepository;
import kr.co.sootechsys.scrobot.service.MenuService;

@Service
@Api(value = "메뉴 서비스")
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
    // e.setScrinId(dto.getScrinId());

    if (null == dto.getMenuId() || "" == dto.getMenuId()) {
      e.setRegistDt(new Date());
    } else {
      e.setMenuId(dto.getMenuId());
    }

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
    // dto.setScrinId(e.getScrinId());
    dto.setRegistDt(e.getRegistDt());

    return dto;
  }

  @Override
  @Transactional
  public void deleteByPrjctId(String prjctId) {
    repo.deleteAllByPrjctId(prjctId);
  }

  @Override
  public String regist(MenuDto dto) {
    dto.setMenuOrdrValue(repo.findAll().size());
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

    repo.findAllByPrjctId(prjctId, Sort.by(Direction.ASC, "menuOrdrValue")).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public List<MenuDto> findAllByPrjctIdWithSort(String prjctId) {
    List<MenuDto> menuDtos = findAllByPrjctId(prjctId);

    List<MenuDto> dtos = new ArrayList<>();

    menuDtos.forEach(dto -> {
      if ("-".equals(dto.getPrntsMenuId())) {
        dtos.add(dto);
        dtos.addAll(getChildren(menuDtos, dto.getMenuId()));
      }
    });

    return dtos;
  }

  /**
   * 하위 메뉴 목록 조회
   * 
   * @param menuDtos    전체 메뉴 목록
   * @param prntsMenuId 부모 메뉴 아이디
   * @return 하위 메뉴 목록
   */
  private List<MenuDto> getChildren(List<MenuDto> menuDtos, String prntsMenuId) {
    List<MenuDto> dtos = new ArrayList<>();

    menuDtos.forEach(dto -> {
      if (prntsMenuId.equals(dto.getPrntsMenuId())) {
        dtos.add(dto);
      }
    });

    return dtos;
  }

}
