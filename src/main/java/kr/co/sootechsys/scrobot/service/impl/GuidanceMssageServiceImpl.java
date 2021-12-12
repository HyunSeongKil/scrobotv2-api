package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sootechsys.scrobot.domain.GuidanceMssageDto;
import kr.co.sootechsys.scrobot.entity.GuidanceMssage;
import kr.co.sootechsys.scrobot.persistence.GuidanceMssageRepository;
import kr.co.sootechsys.scrobot.service.GuidanceMssageService;

@Service
public class GuidanceMssageServiceImpl implements GuidanceMssageService {

  private GuidanceMssageRepository repo;

  public GuidanceMssageServiceImpl(GuidanceMssageRepository repo) {
    this.repo = repo;

  }

  GuidanceMssage toEntity(GuidanceMssageDto dto) {
    GuidanceMssage e = GuidanceMssage.builder().guidanceMssageCn(dto.getGuidanceMssageCn())
        .guidanceMssageNm(dto.getGuidanceMssageNm()).prjctId(dto.getPrjctId()).registerId(dto.getRegisterId())
        .registerNm(dto.getRegisterNm())
        .build();

    if (null == dto.getGuidanceMssageId() || 0 >= dto.getGuidanceMssageId()) {
      e.setRegistDt(new Date());
    } else {
      e.setGuidanceMssageId(dto.getGuidanceMssageId());
      e.setRegistDt(dto.getRegistDt());
    }

    return e;
  }

  GuidanceMssageDto toDto(GuidanceMssage e) {
    return GuidanceMssageDto.builder().guidanceMssageCn(e.getGuidanceMssageCn())
        .guidanceMssageId(e.getGuidanceMssageId()).guidanceMssageNm(e.getGuidanceMssageNm()).prjctId(e.getPrjctId())
        .registDt(e.getRegistDt())
        .registerId(e.getRegisterId()).registerNm(e.getRegisterNm()).build();
  }

  @Override
  @Transactional
  public Long regist(GuidanceMssageDto dto) {
    return repo.save(toEntity(dto)).getGuidanceMssageId();
  }

  @Override
  @Transactional
  public void update(GuidanceMssageDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  @Transactional
  public void deleteById(Long guidanceMssageId) {

    //
    repo.deleteById(guidanceMssageId);
  }

  @Override
  public List<GuidanceMssageDto> findAllByPrjctId(String prjctId) {
    List<GuidanceMssageDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId, Sort.by("guidanceMssageNm")).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
