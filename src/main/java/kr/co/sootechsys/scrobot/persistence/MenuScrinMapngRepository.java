package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.domain.MenuScrinMapngDto;
import kr.co.sootechsys.scrobot.entity.MenuScrinMapng;

public interface MenuScrinMapngRepository extends JpaRepository<MenuScrinMapng, Long> {

  List<MenuScrinMapng> findAllByMenuId(String menuId);

  void deleteAllByMenuId(String menuId);

}
