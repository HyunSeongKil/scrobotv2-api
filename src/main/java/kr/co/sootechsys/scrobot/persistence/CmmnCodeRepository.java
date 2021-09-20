package kr.co.sootechsys.scrobot.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.sootechsys.scrobot.entity.CmmnCode;

public interface CmmnCodeRepository extends JpaRepository<CmmnCode, Long> {

  List<CmmnCode> findAllByPrntsCmmnCode(String prntsCmmnCode);

  Optional<CmmnCode> findByPrntsCmmnCodeAndCmmnCode(String prntsCmmnCode, String cmmnCode);

}
