package kr.co.sootechsys.scrobot.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;
import kr.co.sootechsys.scrobot.entity.PrjctTrgetSysMapng;

public interface PrjctTrgetSysMapngRepository extends JpaRepository<PrjctTrgetSysMapng, Long> {

  Optional<PrjctTrgetSysMapng> findByPrjctId(String prjctId);

  Optional<PrjctTrgetSysMapng> findByTrgetSysId(String trgetSysId);

  Optional<PrjctTrgetSysMapng> findByPrjctIdAndTrgetSysId(String prjctId, String trgetSysId);

}
