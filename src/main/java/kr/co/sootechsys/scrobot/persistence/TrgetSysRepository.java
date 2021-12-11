package kr.co.sootechsys.scrobot.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.entity.TrgetSys;

/**
 * 대상 시스템
 */
@Api(value = "대상시스템 레포지토리")
public interface TrgetSysRepository extends JpaRepository<TrgetSys, String> {

  @Query(value = "SELECT a FROM TrgetSys a INNER JOIN PrjctTrgetSysMapng mapng ON a.trgetSysId = mapng.trgetSysId WHERE mapng.prjctId = :prjctId ")
  Iterable<TrgetSys> findAllByPrjctId(@Param("prjctId") String prjctId);

}
