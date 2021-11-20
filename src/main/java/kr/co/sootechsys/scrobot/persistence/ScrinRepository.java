package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.entity.Scrin;


/**
 * 화면
 */
@Api(value = "화면 레포지토리")
public interface ScrinRepository extends JpaRepository<Scrin, String> {

  /**
   * 화면 그룹 아이디로 목록 조회
   * 
   * @param scrinGroupId 화면 그룹 아이디
   * @return
   */
  List<Scrin> findAllByScrinGroupId(String scrinGroupId);

}
