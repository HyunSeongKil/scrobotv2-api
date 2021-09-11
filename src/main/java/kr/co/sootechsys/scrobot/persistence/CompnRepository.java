package kr.co.sootechsys.scrobot.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.sootechsys.scrobot.entity.Compn;

public interface CompnRepository extends JpaRepository<Compn, String> {

  List<Compn> findAllByScrinId(String scrinId);

}
