package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.sootechsys.scrobot.domain.GuidanceMssageDto;
import kr.co.sootechsys.scrobot.entity.GuidanceMssage;

public interface GuidanceMssageRepository extends JpaRepository<GuidanceMssage, Long> {

  @Query(value = "SELECT a FROM GuidanceMssage a  INNER JOIN PrjctGuidanceMssageMapng mapng ON a.guidanceMssageId = mapng.guidanceMssageId WHERE mapng.prjctId = :prjctId ORDER BY a.guidanceMssageNm ")
  Iterable<GuidanceMssage> findAllByPrjctId(@Param("prjctId") String prjctId);

}
