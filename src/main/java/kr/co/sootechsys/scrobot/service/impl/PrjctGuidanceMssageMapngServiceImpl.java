package kr.co.sootechsys.scrobot.service.impl;

import org.springframework.stereotype.Service;

import kr.co.sootechsys.scrobot.domain.PrjctGuidanceMssageMapngDto;
import kr.co.sootechsys.scrobot.entity.PrjctGuidanceMssageMapng;
import kr.co.sootechsys.scrobot.persistence.PrjctGuidanceMssageMapngRepository;
import kr.co.sootechsys.scrobot.service.PrjctGuidanceMssageMapngService;

@Service
public class PrjctGuidanceMssageMapngServiceImpl implements PrjctGuidanceMssageMapngService {

  private PrjctGuidanceMssageMapngRepository repo;

  public PrjctGuidanceMssageMapngServiceImpl(PrjctGuidanceMssageMapngRepository repo) {
    this.repo = repo;
  }

  PrjctGuidanceMssageMapng toEntity(PrjctGuidanceMssageMapngDto dto) {
    PrjctGuidanceMssageMapng e = PrjctGuidanceMssageMapng.builder().guidanceMssageId(dto.getGuidanceMssageId())
        .prjctId(dto.getPrjctId()).build();

    if (null == dto.getPrjctGuidanceMssageMapngId() || 0 >= dto.getPrjctGuidanceMssageMapngId()) {
      //
    } else {
      e.setPrjctGuidanceMssageMapngId(dto.getPrjctGuidanceMssageMapngId());
    }

    return e;
  }

  PrjctGuidanceMssageMapngDto toDto(PrjctGuidanceMssageMapng e) {
    return PrjctGuidanceMssageMapngDto.builder().guidanceMssageId(e.getGuidanceMssageId())
        .prjctGuidanceMssageMapngId(e.getPrjctGuidanceMssageMapngId()).prjctId(e.getPrjctId()).build();
  }

  @Override
  public Long regist(PrjctGuidanceMssageMapngDto dto) {
    return repo.save(toEntity(dto)).getPrjctGuidanceMssageMapngId();
  }

  @Override
  public void update(PrjctGuidanceMssageMapngDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  public void deleteById(Long prjctGuidanceMssageMapngId) {
    repo.deleteById(prjctGuidanceMssageMapngId);
  }

}
