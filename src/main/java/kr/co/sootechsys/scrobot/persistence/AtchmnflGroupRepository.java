package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.AtchmnflGroupDto;
import kr.co.sootechsys.scrobot.entity.AtchmnflGroup;

@Api(value = "첨부파일그룹 레포지토리")
public interface AtchmnflGroupRepository extends JpaRepository<AtchmnflGroup, Long> {

  Iterable<AtchmnflGroup> findAllByPrjctId(String prjctId);

}
