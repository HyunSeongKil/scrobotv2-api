package kr.co.sootechsys.scrobot.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.entity.Menu;


/**
 * 메뉴
 */
public interface MenuRepository extends JpaRepository<Menu, String> {

  /**
   * 프로젝트 아이디로 목록 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  List<Menu> findAllByPrjctId(String prjctId);

}
