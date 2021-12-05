package kr.co.sootechsys.scrobot.service;

import java.util.List;

import kr.co.sootechsys.scrobot.domain.CommentDto;

public interface CommentService {
  Long regist(CommentDto dto);

  void update(CommentDto dto);

  void deleteById(Long commentId);

  List<CommentDto> findAllByBbsId(Long bbsId);

  Long registByBbsId(CommentDto dto, Long bbsId);
}
