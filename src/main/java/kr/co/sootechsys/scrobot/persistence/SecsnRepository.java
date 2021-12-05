package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.domain.SecsnDto;
import kr.co.sootechsys.scrobot.entity.Secsn;

public interface SecsnRepository extends JpaRepository<Secsn, Long> {

  Iterable<Secsn> findAllByUserId(String userId);

}
