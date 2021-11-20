package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;
import kr.co.sootechsys.scrobot.entity.PrjctTrgetSysMapng;
import kr.co.sootechsys.scrobot.persistence.PrjctTrgetSysMapngRepository;
import kr.co.sootechsys.scrobot.service.PrjctTrgetSysMapngService;


@Service
@Api(value = "프로젝트-대상시스템 서비스")
public class PrjctTrgetSysMapngServiceImpl implements PrjctTrgetSysMapngService {

  private PrjctTrgetSysMapngRepository repo;

  public PrjctTrgetSysMapngServiceImpl(PrjctTrgetSysMapngRepository repo) {
    this.repo = repo;
  }

  PrjctTrgetSysMapngDto toDto(PrjctTrgetSysMapng e) {
    PrjctTrgetSysMapngDto dto = PrjctTrgetSysMapngDto.builder().build();
    dto.setPrjctTrgetSysMapngId(e.getPrjctTrgetSysMapngId());
    dto.setPrjctId(e.getPrjctId());
    dto.setTrgetSysId(e.getTrgetSysId());

    return dto;
  }

  PrjctTrgetSysMapng toEntity(PrjctTrgetSysMapngDto dto) {
    PrjctTrgetSysMapng e = PrjctTrgetSysMapng.builder().build();
    e.setPrjctId(dto.getPrjctId());
    e.setTrgetSysId(dto.getTrgetSysId());


    return e;
  }

  @Override
  public Long regist(String prjctId, String trgetSysId) {
    return regist(PrjctTrgetSysMapngDto.builder().prjctId(prjctId).trgetSysId(trgetSysId).build());
  }

  @Override
  public Long regist(PrjctTrgetSysMapngDto dto) {
    Optional<PrjctTrgetSysMapng> opt = repo.findByPrjctIdAndTrgetSysId(dto.getPrjctId(), dto.getTrgetSysId());
    if (opt.isPresent()) {
      return opt.get().getPrjctTrgetSysMapngId();
    }

    return repo.save(toEntity(dto)).getPrjctTrgetSysMapngId();
  }

  @Override
  public PrjctTrgetSysMapngDto findByPrjctId(String prjctId) {

    Optional<PrjctTrgetSysMapng> opt = repo.findByPrjctId(prjctId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public PrjctTrgetSysMapngDto findByTrgetSysId(String trgetSysId) {
    Optional<PrjctTrgetSysMapng> opt = repo.findByTrgetSysId(trgetSysId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public void deleteByPrjctId(String prjctId) {
    PrjctTrgetSysMapngDto dto = findByPrjctId(prjctId);
    if (null != dto) {
      repo.deleteById(dto.getPrjctTrgetSysMapngId());
    }

  }

  @Override
  public void deleteByTrgetSysId(String trgetSysId) {
    PrjctTrgetSysMapngDto dto = findByTrgetSysId(trgetSysId);
    if (null != dto) {
      repo.deleteById(dto.getPrjctTrgetSysMapngId());
    }
  }

}
