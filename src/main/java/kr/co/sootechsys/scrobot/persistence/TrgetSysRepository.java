package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.entity.TrgetSys;


/**
 * 대상 시스템
 */
@Api(value = "대상시스템 레포지토리")
public interface TrgetSysRepository extends JpaRepository<TrgetSys, String> {

}
