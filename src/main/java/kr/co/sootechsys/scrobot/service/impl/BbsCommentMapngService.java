package kr.co.sootechsys.scrobot.service.impl;

import kr.co.sootechsys.scrobot.domain.BbsCommentMapngDto;

public interface BbsCommentMapngService {
  void deleteByCommentId(Long commentId);

  void deleteAllByBbsId(Long bbsId);

  Long regist(BbsCommentMapngDto dto);
}
