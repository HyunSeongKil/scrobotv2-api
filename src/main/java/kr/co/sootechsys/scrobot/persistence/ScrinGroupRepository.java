package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.entity.ScrinGroup;

/**
 * 화면 그룹
 */
@Api(value = "화면그룹 레포지토리")
public interface ScrinGroupRepository extends JpaRepository<ScrinGroup, String> {

  /**
   * 프로젝트 아이디로 목록 조회
   * 
   * @param prjctId 프로젝트 아이디
   * @return
   */
  List<ScrinGroup> findAllByPrjctId(String prjctId);

}
