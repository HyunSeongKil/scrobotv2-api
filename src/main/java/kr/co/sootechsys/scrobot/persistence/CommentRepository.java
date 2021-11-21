package kr.co.sootechsys.scrobot.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.scrobot.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
