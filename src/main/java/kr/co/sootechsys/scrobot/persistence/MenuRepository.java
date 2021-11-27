package kr.co.sootechsys.scrobot.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.entity.Menu;

/**
 * 메뉴
 */
@Api(value = "메뉴 레포지토리")
public interface MenuRepository extends JpaRepository<Menu, String> {

  /**
   * 프로젝트 아이디로 목록 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  @ApiOperation(value = "프로젝트아이디로 목록 조회")
  List<Menu> findAllByPrjctId(String prjctId);

  @Query(value = "SELECT a FROM Menu a WHERE a.prjctId = :prjctId and a.prntsMenuId = :prntsMenuId ORDER BY a.menuOrdrValue")
  @ApiOperation(value = "프로젝트아이디+부모메뉴아이디로 목록 조회")
  List<Menu> findAllByPrjctIdAndPrntsMenuId(@Param("prjctId") String prjctId, @Param("prntsMenuId") String prntsMenuId);

}
