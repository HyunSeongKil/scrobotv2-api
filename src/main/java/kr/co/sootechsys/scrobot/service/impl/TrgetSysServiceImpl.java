package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.entity.TrgetSys;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.TrgetSysRepository;
import kr.co.sootechsys.scrobot.service.TrgetSysService;

@Service
@Api(value = "대상시스템 서비스")
public class TrgetSysServiceImpl implements TrgetSysService {

  private TrgetSysRepository repo;

  public TrgetSysServiceImpl(TrgetSysRepository repo) {
    this.repo = repo;
  }

  TrgetSys toEntity(TrgetSysDto dto) {
    TrgetSys e = TrgetSys.builder().build();
    e.setTrgetSysId(Util.getShortUuid());
    // e.setDbDriverNm(dto.getDbDriverNm());
    e.setDbPasswordNm(dto.getDbPasswordNm());
    e.setDbUrlNm(dto.getDbUrlNm());
    e.setDbUserNm(dto.getDbUserNm());
    e.setDbTyNm(dto.getDbTyNm());
    e.setTrgetSysNm(dto.getTrgetSysNm());
    e.setDbNm(dto.getDbNm());

    return e;
  }

  TrgetSysDto toDto(TrgetSys e) {
    TrgetSysDto dto = TrgetSysDto.builder().build();
    // dto.setDbDriverNm(e.getDbDriverNm());
    dto.setDbPasswordNm(e.getDbPasswordNm());
    dto.setDbTyNm(e.getDbTyNm());
    dto.setDbUrlNm(e.getDbUrlNm());
    dto.setDbUserNm(e.getDbUserNm());
    dto.setTrgetSysId(e.getTrgetSysId());
    dto.setTrgetSysNm(e.getTrgetSysNm());
    dto.setDbNm(e.getDbNm());

    return dto;
  }

  @Override
  public String regist(TrgetSysDto dto) {
    return repo.save(toEntity(dto)).getTrgetSysId();
  }

  @Override
  public void updt(TrgetSysDto dto) {
    if (null == findById(dto.getTrgetSysId())) {
      return;
    }

    TrgetSys e = toEntity(dto);
    e.setTrgetSysId(dto.getTrgetSysId());

    repo.save(e);
  }

  @Override
  public void delete(String trgetSysId) {
    repo.deleteById(trgetSysId);
  }

  @Override
  public TrgetSysDto findById(String trgetSysId) {
    Optional<TrgetSys> opt = repo.findById(trgetSysId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    //
    return null;
  }

  @Override
  public List<TrgetSysDto> findAll() {
    List<TrgetSysDto> dtos = new ArrayList<>();

    repo.findAll().forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
