package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sootechsys.scrobot.domain.BbsCommentMapngDto;
import kr.co.sootechsys.scrobot.domain.CommentDto;
import kr.co.sootechsys.scrobot.entity.BbsCommentMapng;
import kr.co.sootechsys.scrobot.entity.Comment;
import kr.co.sootechsys.scrobot.persistence.CommentRepository;
import kr.co.sootechsys.scrobot.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

  private CommentRepository repo;
  private BbsCommentMapngService bbsCommentMapngService;

  public CommentServiceImpl(CommentRepository repo, BbsCommentMapngService bbsCommentMapngService) {
    this.repo = repo;
    this.bbsCommentMapngService = bbsCommentMapngService;
  }

  Comment toEntity(CommentDto dto) {
    Comment e = Comment.builder().commentCn(dto.getCommentCn()).commentSjNm(dto.getCommentSjNm())
        .registerId(dto.getRegisterId()).registerNm(dto.getRegisterNm()).build();

    if (null == dto.getCommentId() || 0 > dto.getCommentId()) {
      e.setCommentId(System.currentTimeMillis());
      e.setRegistDt(new Date());
    } else {
      //
      e.setCommentId(dto.getCommentId());
    }

    return e;
  }

  CommentDto toDto(Comment e) {
    return CommentDto.builder().commentCn(e.getCommentCn()).commentId(e.getCommentId()).commentSjNm(e.getCommentSjNm())
        .registDt(e.getRegistDt()).registerId(e.getRegisterId()).registerNm(e.getRegisterNm()).build();
  }

  @Override
  @Transactional
  public Long regist(CommentDto dto) {
    return repo.save(toEntity(dto)).getCommentId();
  }

  @Override
  @Transactional
  public Long registByBbsId(CommentDto dto, Long bbsId) {
    Long commentId = regist(dto);

    bbsCommentMapngService.regist(BbsCommentMapngDto.builder().bbsId(bbsId).commentId(commentId).build());

    return commentId;
  }

  @Override
  @Transactional
  public void update(CommentDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  @Transactional
  public void deleteById(Long commentId) {
    bbsCommentMapngService.deleteByCommentId(commentId);

    repo.deleteById(commentId);

  }

  @Override
  public List<CommentDto> findAllByBbsId(Long bbsId) {
    List<CommentDto> dtos = new ArrayList<>();

    repo.findAllByBbsId(bbsId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
