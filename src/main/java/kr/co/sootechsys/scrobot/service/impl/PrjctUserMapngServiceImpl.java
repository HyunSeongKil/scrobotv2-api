package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.sootechsys.scrobot.domain.PrjctUserMapngDto;
import kr.co.sootechsys.scrobot.entity.PrjctUserMapng;
import kr.co.sootechsys.scrobot.persistence.PrjctUserMapngRepository;
import kr.co.sootechsys.scrobot.service.PrjctUserMapngService;

@Service
public class PrjctUserMapngServiceImpl implements PrjctUserMapngService {
  private PrjctUserMapngRepository repo;

  public PrjctUserMapngServiceImpl(PrjctUserMapngRepository repo) {
    this.repo = repo;
  }

  PrjctUserMapng toEntity(PrjctUserMapngDto dto) {
    PrjctUserMapng e = PrjctUserMapng.builder().prjctId(dto.getPrjctId()).userId(dto.getUserId())
        .mngrAt(dto.getMngrAt()).build();

    if (null == dto.getPrjctUserMapngId() || 0 >= dto.getPrjctUserMapngId()) {
      //
    } else {
      e.setPrjctUserMapngId(dto.getPrjctUserMapngId());
    }

    return e;
  }

  PrjctUserMapngDto toDto(PrjctUserMapng e) {
    return PrjctUserMapngDto.builder().prjctId(e.getPrjctId()).prjctUserMapngId(e.getPrjctUserMapngId())
        .userId(e.getUserId()).mngrAt(e.getMngrAt()).build();
  }

  @Override
  public Long regist(PrjctUserMapngDto dto) {
    return repo.save(toEntity(dto)).getPrjctUserMapngId();
  }

  @Override
  public void update(PrjctUserMapngDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  public void deleteById(Long prjctUserMapngId) {
    repo.deleteById(prjctUserMapngId);
  }

  @Override
  public List<PrjctUserMapngDto> findAllByUserId(String userId) {
    List<PrjctUserMapngDto> dtos = new ArrayList<>();

    repo.findAllByUserId(userId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public List<PrjctUserMapngDto> findAllByPrjctId(String prjctId) {
    List<PrjctUserMapngDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
