package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.sootechsys.scrobot.entity.TrgetSys;

public interface TrgetSysRepository extends JpaRepository<TrgetSys, String> {

}
