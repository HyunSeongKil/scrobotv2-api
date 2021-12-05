package kr.co.sootechsys.scrobot.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sootechsys.scrobot.domain.BbsCommentMapngDto;
import kr.co.sootechsys.scrobot.entity.BbsCommentMapng;
import kr.co.sootechsys.scrobot.persistence.BbsCommentMapngRepository;

@Service
public class BbsCommentMapngServiceImpl implements BbsCommentMapngService {

  private BbsCommentMapngRepository repo;

  public BbsCommentMapngServiceImpl(BbsCommentMapngRepository repo) {
    this.repo = repo;
  }

  BbsCommentMapngDto toDto(BbsCommentMapng e) {
    return BbsCommentMapngDto.builder().bbsCommentMapngId(e.getBbsCommentMapngId()).bbsId(e.getBbsId())
        .commentId(e.getCommentId()).build();
  }

  BbsCommentMapng toEntity(BbsCommentMapngDto dto) {
    BbsCommentMapng e = BbsCommentMapng.builder().bbsId(dto.getBbsId()).commentId(dto.getCommentId()).build();

    if (null == dto.getBbsCommentMapngId() || 0 > dto.getBbsCommentMapngId()) {
      //
    } else {
      e.setBbsCommentMapngId(dto.getBbsCommentMapngId());
    }

    return e;
  }

  @Override
  @Transactional
  public void deleteByCommentId(Long commentId) {
    repo.deleteByCommentId(commentId);
  }

  @Override
  @Transactional
  public void deleteAllByBbsId(Long bbsId) {
    repo.deleteAllByBbsId(bbsId);
  }

  @Override
  public Long regist(BbsCommentMapngDto dto) {
    return repo.save(toEntity(dto)).getBbsCommentMapngId();
  }

}
