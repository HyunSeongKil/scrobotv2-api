package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.sootechsys.scrobot.domain.AtchmnflGroupDto;
import kr.co.sootechsys.scrobot.entity.AtchmnflGroup;

public interface AtchmnflGroupRepository extends JpaRepository<AtchmnflGroup, Long> {

  Iterable<AtchmnflGroup> findAllByPrjctId(String prjctId);

}
