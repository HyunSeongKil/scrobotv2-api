package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;
import kr.co.sootechsys.scrobot.entity.PrjctCmmnCode;
import kr.co.sootechsys.scrobot.persistence.PrjctCmmnCodeRepository;
import kr.co.sootechsys.scrobot.service.PrjctCmmnCodeService;

@Service
public class PrjctCmmnCodeServiceImpl implements PrjctCmmnCodeService {

  private PrjctCmmnCodeRepository repo;

  public PrjctCmmnCodeServiceImpl(PrjctCmmnCodeRepository repo) {
    this.repo = repo;
  }

  PrjctCmmnCode toEntity(PrjctCmmnCodeDto dto) {
    PrjctCmmnCode e = PrjctCmmnCode.builder().cmmnCode(dto.getCmmnCode()).cmmnCodeNm(dto.getCmmnCodeNm())
        .prjctId(dto.getPrjctId()).prntsPrjctCmmnCode(dto.getPrntsPrjctCmmnCode()).registerId(dto.getRegisterId())
        .registerNm(dto.getRegisterNm()).useAt(dto.getUseAt()).build();

    if (null == dto.getPrjctCmmnCodeId() || 0 >= dto.getPrjctCmmnCodeId()) {
      e.setRegistDt(new Date());
    } else {
      e.setPrjctCmmnCodeId(dto.getPrjctCmmnCodeId());
    }

    return e;
  }

  PrjctCmmnCodeDto toDto(PrjctCmmnCode e) {
    return PrjctCmmnCodeDto.builder().cmmnCode(e.getCmmnCode()).cmmnCodeNm(e.getCmmnCodeNm())
        .prjctCmmnCodeId(e.getPrjctCmmnCodeId()).prjctId(e.getPrjctId())
        .prntsPrjctCmmnCode(e.getPrntsPrjctCmmnCode()).registDt(e.getRegistDt()).registerId(e.getRegisterId())
        .registerNm(e.getRegisterNm()).useAt(e.getUseAt()).build();
  }

  @Override
  public Long regist(PrjctCmmnCodeDto dto) {
    return repo.save(toEntity(dto)).getPrjctCmmnCodeId();
  }

  @Override
  public void update(PrjctCmmnCodeDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  public void deleteById(Long prjctCmmnCodeId) {
    repo.deleteById(prjctCmmnCodeId);
  }

  @Override
  public List<PrjctCmmnCodeDto> findAllByPrjctId(String prjctId) {
    List<PrjctCmmnCodeDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public PrjctCmmnCodeDto findByCmmnCode(String cmmnCode) {
    Optional<PrjctCmmnCode> opt = repo.findByCmmnCode(cmmnCode);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    //
    return null;
  }

  @Override
  public List<PrjctCmmnCodeDto> findAllByPrjctIdAndPrntsPrjctCmmnCode(String prjctId, String prntsPrjctCmmnCode) {
    List<PrjctCmmnCodeDto> dtos = new ArrayList<>();

    repo.findAllByPrjctIdAndPrntsPrjctCmmnCode(prjctId, prntsPrjctCmmnCode).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
