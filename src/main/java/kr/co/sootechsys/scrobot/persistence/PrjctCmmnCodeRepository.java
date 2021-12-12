package kr.co.sootechsys.scrobot.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;
import kr.co.sootechsys.scrobot.entity.PrjctCmmnCode;

public interface PrjctCmmnCodeRepository
    extends JpaRepository<PrjctCmmnCode, Long>, JpaSpecificationExecutor<PrjctCmmnCode> {

  Iterable<PrjctCmmnCode> findAllByPrjctId(String prjctId);

  Optional<PrjctCmmnCode> findByCmmnCode(String cmmnCode);

  Iterable<PrjctCmmnCode> findAllByPrjctIdAndPrntsCmmnCode(String prjctId, String prntsCmmnCode);

}
