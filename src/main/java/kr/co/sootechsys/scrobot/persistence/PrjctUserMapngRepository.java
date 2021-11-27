package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.domain.PrjctUserMapngDto;
import kr.co.sootechsys.scrobot.entity.PrjctUserMapng;

public interface PrjctUserMapngRepository extends JpaRepository<PrjctUserMapng, Long> {

  Iterable<PrjctUserMapng> findAllByUserId(String userId);

  Iterable<PrjctUserMapng> findAllByPrjctId(String prjctId);

}
