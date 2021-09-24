package kr.co.sootechsys.scrobot.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.entity.Prjct;


/**
 * 프로젝트
 */
public interface PrjctRepository extends JpaRepository<Prjct, String> {

  /**
   * 사용자 아이디로 목록 조회
   * 
   * @param userId
   * @return
   */
  List<Prjct> findAllByUserId(String userId);

}
