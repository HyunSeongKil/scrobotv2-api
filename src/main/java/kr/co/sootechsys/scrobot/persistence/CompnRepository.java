package kr.co.sootechsys.scrobot.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.entity.Compn;


/**
 * 콤포넌트
 */
@Api(value = "콤포넌트 레포지토리")
public interface CompnRepository extends JpaRepository<Compn, String> {

  /**
   * 화면 아이디로 목록 조회
   * 
   * @param scrinId 화면 아이디
   * @return
   */
  List<Compn> findAllByScrinId(String scrinId);

  /**
   * 화면 아이디로 삭제
   * 
   * @param scrinId
   */
  void deleteByScrinId(String scrinId);

}
