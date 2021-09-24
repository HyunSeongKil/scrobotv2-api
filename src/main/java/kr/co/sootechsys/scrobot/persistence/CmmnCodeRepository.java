package kr.co.sootechsys.scrobot.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.sootechsys.scrobot.entity.CmmnCode;


/**
 * 공통 코드
 */
public interface CmmnCodeRepository extends JpaRepository<CmmnCode, Long> {

  /**
   * 부모 공통코드로 목록 조회
   * 
   * @param prntsCmmnCode 부모 공통 코드
   * @return
   */
  List<CmmnCode> findAllByPrntsCmmnCode(String prntsCmmnCode);

  /**
   * 부모 공통 코드 & 공통 코드로 1건 조회
   * 
   * @param prntsCmmnCode 부모 공통 코드
   * @param cmmnCode 공통 코드
   * @return
   */
  Optional<CmmnCode> findByPrntsCmmnCodeAndCmmnCode(String prntsCmmnCode, String cmmnCode);

}
