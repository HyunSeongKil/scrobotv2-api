package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.entity.PrjctGuidanceMssageMapng;

public interface PrjctGuidanceMssageMapngRepository extends JpaRepository<PrjctGuidanceMssageMapng, Long> {

  @ApiOperation(value = "안내메시지아이디로 삭제")
  void deleteByGuidanceMssageId(Long guidanceMssageId);

}
