package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.entity.Prjct;

/**
 * 프로젝트
 */
@Api(value = "프로젝트 레포지토리")
public interface PrjctRepository extends JpaRepository<Prjct, String> {

  /**
   * 사용자 아이디로 목록 조회
   * 
   * @param userId
   * @return
   */
  @Query(value = "SELECT a FROM Prjct a LEFT OUTER JOIN PrjctUserMapng mapng ON a.prjctId = mapng.prjctId WHERE mapng.userId = :userId OR a.userId = :userId ORDER BY a.prjctNm")
  List<Prjct> findAllByUserId(@Param("userId") String userId);

}
