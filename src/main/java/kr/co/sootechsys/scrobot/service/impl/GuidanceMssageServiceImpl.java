package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.sootechsys.scrobot.domain.GuidanceMssageDto;
import kr.co.sootechsys.scrobot.entity.GuidanceMssage;
import kr.co.sootechsys.scrobot.persistence.GuidanceMssageRepository;
import kr.co.sootechsys.scrobot.service.GuidanceMssageService;
import kr.co.sootechsys.scrobot.service.PrjctGuidanceMssageMapngService;

@Service
public class GuidanceMssageServiceImpl implements GuidanceMssageService {

  private GuidanceMssageRepository repo;
  private PrjctGuidanceMssageMapngService prjctGuidanceMssageMapngService;

  public GuidanceMssageServiceImpl(GuidanceMssageRepository repo,
      PrjctGuidanceMssageMapngService prjctGuidanceMssageMapngService) {
    this.repo = repo;
    this.prjctGuidanceMssageMapngService = prjctGuidanceMssageMapngService;
  }

  GuidanceMssage toEntity(GuidanceMssageDto dto) {
    GuidanceMssage e = GuidanceMssage.builder().guidanceMssageCn(dto.getGuidanceMssageCn())
        .guidanceMssageNm(dto.getGuidanceMssageNm()).registerId(dto.getRegisterId()).registerNm(dto.getRegisterNm())
        .build();

    if (null == dto.getGuidanceMssageId() || 0 >= dto.getGuidanceMssageId()) {
      e.setRegistDt(new Date());
    } else {
      e.setGuidanceMssageId(dto.getGuidanceMssageId());
    }

    return e;
  }

  GuidanceMssageDto toDto(GuidanceMssage e) {
    return GuidanceMssageDto.builder().guidanceMssageCn(e.getGuidanceMssageCn())
        .guidanceMssageId(e.getGuidanceMssageId()).guidanceMssageNm(e.getGuidanceMssageNm()).registDt(e.getRegistDt())
        .registerId(e.getRegisterId()).registerNm(e.getRegisterNm()).build();
  }

  @Override
  public Long regist(GuidanceMssageDto dto) {
    return repo.save(toEntity(dto)).getGuidanceMssageId();
  }

  @Override
  public void update(GuidanceMssageDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  public void deleteById(Long guidanceMssageId) {
    // delete mapng
    prjctGuidanceMssageMapngService.deleteByGuidanceMssageId(guidanceMssageId);

    //
    repo.deleteById(guidanceMssageId);
  }

  @Override
  public List<GuidanceMssageDto> findAllByPrjctId(String prjctId) {
    List<GuidanceMssageDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
