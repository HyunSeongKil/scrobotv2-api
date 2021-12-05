package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.entity.BbsCommentMapng;

public interface BbsCommentMapngRepository extends JpaRepository<BbsCommentMapng, Long> {

  void deleteByCommentId(Long commentId);

  void deleteAllByBbsId(Long bbsId);

}
