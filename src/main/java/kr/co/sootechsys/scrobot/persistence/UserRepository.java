package kr.co.sootechsys.scrobot.persistence;

import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.sootechsys.scrobot.entity.User;

@Api(value = "회원 레포지토리")
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

  @Query(value = "SELECT a FROM User a INNER JOIN PrjctUserMapng mapng ON a.userId = mapng.userId WHERE mapng.prjctId = :prjctId")
  @ApiOperation(value = "프로젝트아이디로 사용자 목록 조회")
  Iterable<User> findAllByPrjctId(@Param("prjctId") String prjctId);

  Optional<User> findByUserNmAndTelno(String userNm, String encTelno);

}
