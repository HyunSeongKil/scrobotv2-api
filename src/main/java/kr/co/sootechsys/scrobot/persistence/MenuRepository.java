package kr.co.sootechsys.scrobot.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, String> {

  List<Menu> findAllByPrjctId(String prjctId);

}
