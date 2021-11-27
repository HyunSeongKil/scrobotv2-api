package kr.co.sootechsys.scrobot.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;
import kr.co.sootechsys.scrobot.entity.PrjctCmmnCode;

public interface PrjctCmmnCodeRepository extends JpaRepository<PrjctCmmnCode, Long> {

  Iterable<PrjctCmmnCode> findAllByPrjctId(String prjctId);

  Optional<PrjctCmmnCode> findByCmmnCode(String cmmnCode);

  Iterable<PrjctCmmnCode> findAllByPrjctIdAndPrntsPrjctCmmnCode(String prjctId, String prntsCmmnCode);

}
