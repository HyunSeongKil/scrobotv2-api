package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sootechsys.scrobot.domain.MenuScrinMapngDto;
import kr.co.sootechsys.scrobot.entity.MenuScrinMapng;
import kr.co.sootechsys.scrobot.persistence.MenuScrinMapngRepository;
import kr.co.sootechsys.scrobot.service.MenuScrinMapngService;

@Service
public class MenuScrinMapngServiceImpl implements MenuScrinMapngService {

  private MenuScrinMapngRepository repo;

  public MenuScrinMapngServiceImpl(MenuScrinMapngRepository repo) {
    this.repo = repo;
  }

  MenuScrinMapng toEntity(MenuScrinMapngDto dto) {
    MenuScrinMapng e = MenuScrinMapng.builder().menuId(dto.getMenuId()).scrinId(dto.getScrinId()).build();

    if (null == dto.getMenuScrinMapngId() || 0 >= dto.getMenuScrinMapngId()) {
      //
    } else {
      e.setMenuScrinMapngId(dto.getMenuScrinMapngId());
    }

    return e;
  }

  MenuScrinMapngDto toDto(MenuScrinMapng e) {
    return MenuScrinMapngDto.builder().menuId(e.getMenuId()).menuScrinMapngId(e.getMenuScrinMapngId())
        .scrinId(e.getScrinId()).build();
  }

  @Override
  public Long regist(MenuScrinMapngDto dto) {
    return repo.save(toEntity(dto)).getMenuScrinMapngId();
  }

  @Override
  public void deleteById(Long menuScrinMapngId) {
    repo.deleteById(menuScrinMapngId);
  }

  @Override
  public List<MenuScrinMapngDto> findAllByMenuId(String menuId) {
    List<MenuScrinMapngDto> dtos = new ArrayList<>();

    //
    repo.findAllByMenuId(menuId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public boolean existsByMenuId(String menuId) {
    return 0 < repo.findAllByMenuId(menuId).size();
  }

  @Override
  @Transactional
  public void deleteByMenuId(String menuId) {
    repo.deleteAllByMenuId(menuId);

  }

  @Override
  @Transactional
  public void update(MenuScrinMapngDto dto) {
    repo.save(toEntity(dto));

  }

  @Override
  public MenuScrinMapngDto findByMenuIdAndScrinId(String menuId, String scrinId) {
    Optional<MenuScrinMapng> opt = repo.findByMenuIdAndScrinId(menuId, scrinId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public List<MenuScrinMapngDto> findAllByScrinId(String scrinId) {
    List<MenuScrinMapngDto> dtos = new ArrayList<>();

    repo.findAllByScrinId(scrinId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
