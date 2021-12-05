package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.sootechsys.scrobot.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Query(value = "SELECT a FROM Comment a INNER JOIN BbsCommentMapng mapng ON a.commentId = mapng.commentId WHERE mapng.bbsId = :bbsId ORDER BY a.registDt  ")
  Iterable<Comment> findAllByBbsId(@Param("bbsId") Long bbsId);

  @Query(value = "DELETE FROM Comment a WHERE a.commentId IN (SELECT mapng.commentId FROM BbsCommentMapng mapng WHERE mapng.bbsId = :bbsId) ")
  void deleteAllByBbsId(@Param("bbsId") Long bbsId);
}
