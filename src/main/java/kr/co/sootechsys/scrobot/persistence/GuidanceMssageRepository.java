package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.sootechsys.scrobot.domain.GuidanceMssageDto;
import kr.co.sootechsys.scrobot.entity.GuidanceMssage;

public interface GuidanceMssageRepository extends JpaRepository<GuidanceMssage, Long> {

  Iterable<GuidanceMssage> findAllByPrjctId(@Param("prjctId") String prjctId, Sort sort);

}
