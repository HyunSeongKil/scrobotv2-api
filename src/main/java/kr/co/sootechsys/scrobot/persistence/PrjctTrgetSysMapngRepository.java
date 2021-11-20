package kr.co.sootechsys.scrobot.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;
import kr.co.sootechsys.scrobot.entity.PrjctTrgetSysMapng;


/**
 * 프로젝트-대상 시스템 매핑
 */
@Api(value = "프로젝트-대상시스템 매핑 레포지토리")
public interface PrjctTrgetSysMapngRepository extends JpaRepository<PrjctTrgetSysMapng, Long> {

  /**
   * 프로젝트 아이디로 1건 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  Optional<PrjctTrgetSysMapng> findByPrjctId(String prjctId);

  /**
   * 대상 시스템 아이디로 1건 조회
   * 
   * @param trgetSysId 대상 시스템 아이디
   * @return
   */
  Optional<PrjctTrgetSysMapng> findByTrgetSysId(String trgetSysId);

  /**
   * 프로젝트 아이디 & 대상 시스템 아이디로 1건 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @param trgetSysId 대상 시스템 아이디
   * @return
   */
  Optional<PrjctTrgetSysMapng> findByPrjctIdAndTrgetSysId(String prjctId, String trgetSysId);

}
