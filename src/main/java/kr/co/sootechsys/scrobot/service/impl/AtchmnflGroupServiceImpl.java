package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.AtchmnflGroupDto;
import kr.co.sootechsys.scrobot.entity.AtchmnflGroup;
import kr.co.sootechsys.scrobot.persistence.AtchmnflGroupRepository;
import kr.co.sootechsys.scrobot.service.AtchmnflGroupService;

@Service
@Api(value = "첨부파일그룹 서비스")
public class AtchmnflGroupServiceImpl implements AtchmnflGroupService {

  private AtchmnflGroupRepository repo;


  public AtchmnflGroupServiceImpl(AtchmnflGroupRepository repo) {
    this.repo = repo;
  }

  AtchmnflGroup toEntity(AtchmnflGroupDto dto) {
    AtchmnflGroup e = AtchmnflGroup.builder().prjctId(dto.getPrjctId()).build();

    if (null != dto.getAtchmnflGroupId() && 0 != dto.getAtchmnflGroupId()) {
      e.setAtchmnflGroupId(dto.getAtchmnflGroupId());
    } else {
      e.setRegistDt(new Date());
    }

    return e;
  }

  AtchmnflGroupDto toDto(AtchmnflGroup e) {
    return AtchmnflGroupDto.builder().atchmnflGroupId(e.getAtchmnflGroupId()).prjctId(e.getPrjctId()).registDt(e.getRegistDt()).build();
  }

  @Override
  @Transactional
  public Long regist(String prjctId) {
    return repo.save(toEntity(AtchmnflGroupDto.builder().prjctId(prjctId).build())).getAtchmnflGroupId();
  }

  @Override
  public List<AtchmnflGroupDto> findAllByPrjctId(String prjctId) {
    List<AtchmnflGroupDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e -> {
      dtos.add(toDto(e));
    });


    return dtos;
  }

}
