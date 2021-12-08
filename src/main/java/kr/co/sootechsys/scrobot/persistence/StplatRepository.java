package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import kr.co.sootechsys.scrobot.entity.Stplat;

public interface StplatRepository extends JpaRepository<Stplat, Long>, JpaSpecificationExecutor<Stplat> {

}
