package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import kr.co.sootechsys.scrobot.domain.CmmnCodeDto;
import kr.co.sootechsys.scrobot.entity.CmmnCode;
import kr.co.sootechsys.scrobot.persistence.CmmnCodeRepository;
import kr.co.sootechsys.scrobot.service.CmmnCodeService;


/**
 * 공통 코드
 */
@Service
public class CmmnCodeServiceImpl implements CmmnCodeService {

  private CmmnCodeRepository repo;

  public CmmnCodeServiceImpl(CmmnCodeRepository repo) {
    this.repo = repo;
  }

  CmmnCode toEntity(CmmnCodeDto dto) {
    CmmnCode e = CmmnCode.builder().build();
    e.setCmmnCode(dto.getCmmnCode());
    e.setCmmnCodeNm(dto.getCmmnCodeNm());
    e.setPrntsCmmnCode(dto.getPrntsCmmnCode());
    e.setUseAt(dto.getUseAt());

    return e;
  }

  CmmnCodeDto toDto(CmmnCode e) {
    CmmnCodeDto dto = CmmnCodeDto.builder().build();
    dto.setCmmnCode(e.getCmmnCode());
    dto.setCmmnCodeId(e.getCmmnCodeId());
    dto.setCmmnCodeNm(e.getCmmnCodeNm());
    dto.setPrntsCmmnCode(e.getPrntsCmmnCode());
    dto.setUseAt(e.getUseAt());

    return dto;
  }

  @Override
  public List<CmmnCodeDto> findAllByPrntsCmmnCode(String prntsCmmnCode) {
    // TODO cache


    List<CmmnCodeDto> dtos = new ArrayList<>();

    repo.findAllByPrntsCmmnCode(prntsCmmnCode).forEach(e -> {
      dtos.add(toDto(e));
    });


    return dtos;
  }

  @Override
  public CmmnCodeDto findById(Long cmmnCodeId) {
    // TODO cache


    Optional<CmmnCode> opt = repo.findById(cmmnCodeId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }


    return null;
  }

  @Override
  public CmmnCodeDto findByPrntsCmmnCodeAndCmmnCode(String prntsCmmnCode, String cmmnCode) {

    Optional<CmmnCode> opt = repo.findByPrntsCmmnCodeAndCmmnCode(prntsCmmnCode, cmmnCode);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public List<CmmnCodeDto> findAll() {
    List<CmmnCodeDto> dtos = new ArrayList<>();

    repo.findAll().forEach(e -> {
      dtos.add(toDto(e));
    });


    return dtos;
  }

}
