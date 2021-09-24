package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.sootechsys.scrobot.entity.TrgetSys;


/**
 * 대상 시스템
 */
public interface TrgetSysRepository extends JpaRepository<TrgetSys, String> {

}
