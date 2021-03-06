package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.CompnDto;
import kr.co.sootechsys.scrobot.entity.Compn;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.CompnRepository;
import kr.co.sootechsys.scrobot.service.CompnService;

@Service
@Api(value = "콤포넌트 서비스")
public class CompnServiceImpl implements CompnService {

  private CompnRepository repo;

  public CompnServiceImpl(CompnRepository repo) {
    this.repo = repo;
  }


  Compn toEntity(CompnDto dto) {
    Compn e = Compn.builder().build();
    e.setCompnId(Util.getShortUuid());
    e.setCompnCn(dto.getCompnCn());
    e.setCompnNm(dto.getCompnNm());
    e.setEngAbrvNm(dto.getEngAbrvNm());
    e.setScrinId(dto.getScrinId());
    e.setHnglAbrvNm(dto.getHnglAbrvNm());
    e.setCompnSeCode(dto.getCompnSeCode());
    e.setOrdrValue(dto.getOrdrValue());
    e.setRegistDt(new Date());
    e.setUpdtDt(new Date());

    return e;
  }

  CompnDto toDto(Compn e) {
    CompnDto dto = CompnDto.builder().build();
    dto.setCompnCn(e.getCompnCn());
    dto.setCompnId(e.getCompnId());
    dto.setCompnNm(e.getCompnNm());
    dto.setEngAbrvNm(e.getEngAbrvNm());
    dto.setScrinId(e.getScrinId());
    dto.setHnglAbrvNm(e.getHnglAbrvNm());
    dto.setCompnSeCode(e.getCompnSeCode());
    dto.setOrdrValue(e.getOrdrValue());
    dto.setRegistDt(e.getRegistDt());
    dto.setUpdtDt(e.getUpdtDt());

    // TODO ScrinDto

    return dto;
  }


  @Override
  @Transactional
  public void deleteByScrinId(String scrinId) {
    repo.deleteByScrinId(scrinId);
  }

  @Override
  @Transactional
  public String regist(CompnDto dto) {
    return repo.save(toEntity(dto)).getCompnId();
  }


  @Override
  @Transactional
  public String regist(List<CompnDto> dtos) {
    dtos.forEach(dto -> {
      regist(dto);
    });
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public void updt(CompnDto dto) {
    if (null == findById(dto.getCompnId())) {
      return;
    }

    Compn e = toEntity(dto);
    e.setCompnId(dto.getCompnId());

    repo.save(e);

  }

  @Override
  public void delete(String compnId) {
    if (null == findById(compnId)) {
      return;
    }

    repo.deleteById(compnId);
  }

  @Override
  public CompnDto findById(String compnId) {
    Optional<Compn> opt = repo.findById(compnId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }


    return null;
  }

  @Override
  public List<CompnDto> findAllByScrinId(String scrinId) {
    List<CompnDto> dtos = new ArrayList<>();

    repo.findAllByScrinId(scrinId).forEach(e -> {
      dtos.add(toDto(e));
    });


    return dtos;
  }



}
