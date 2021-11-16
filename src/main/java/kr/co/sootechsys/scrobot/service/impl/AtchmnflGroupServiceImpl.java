package kr.co.sootechsys.scrobot.service.impl;

import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.sootechsys.scrobot.domain.AtchmnflGroupDto;
import kr.co.sootechsys.scrobot.entity.AtchmnflGroup;
import kr.co.sootechsys.scrobot.persistence.AtchmnflGroupRepository;
import kr.co.sootechsys.scrobot.service.AtchmnflGroupService;

@Service
public class AtchmnflGroupServiceImpl implements AtchmnflGroupService {

  private AtchmnflGroupRepository repo;


  public AtchmnflGroupServiceImpl(AtchmnflGroupRepository repo) {
    this.repo = repo;
  }

  AtchmnflGroup toEntity(AtchmnflGroupDto dto) {
    AtchmnflGroup e = AtchmnflGroup.builder().build();

    if (null != dto.getAtchmnflGroupId() && 0 != dto.getAtchmnflGroupId()) {
      e.setAtchmnflGroupId(dto.getAtchmnflGroupId());
    } else {
      e.setRegistDt(new Date());
    }

    return e;
  }

  AtchmnflGroupDto toDto(AtchmnflGroup e) {
    return AtchmnflGroupDto.builder().atchmnflGroupId(e.getAtchmnflGroupId()).build();
  }

  @Override
  @Transactional
  public Long regist() {
    return repo.save(toEntity(AtchmnflGroupDto.builder().build())).getAtchmnflGroupId();
  }

}
