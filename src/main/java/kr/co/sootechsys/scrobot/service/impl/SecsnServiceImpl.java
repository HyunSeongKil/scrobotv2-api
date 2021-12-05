package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.co.sootechsys.scrobot.domain.SecsnDto;
import kr.co.sootechsys.scrobot.entity.Secsn;
import kr.co.sootechsys.scrobot.persistence.SecsnRepository;
import kr.co.sootechsys.scrobot.service.SecsnService;

@Service
public class SecsnServiceImpl implements SecsnService {

  private SecsnRepository repo;

  public SecsnServiceImpl(SecsnRepository repo) {
    this.repo = repo;
  }

  Secsn toEntity(SecsnDto dto) {
    Secsn e = Secsn.builder().secsnReasonCn(dto.getSecsnReasonCn()).userId(dto.getUserId()).build();

    if (null == dto.getSecsnId() || 0 > dto.getSecsnId()) {
      e.setRegistDt(new Date());
    } else {
      e.setSecsnId(dto.getSecsnId());
    }

    return e;
  }

  SecsnDto toDto(Secsn e) {
    return SecsnDto.builder().registDt(e.getRegistDt()).secsnId(e.getSecsnId()).secsnReasonCn(e.getSecsnReasonCn())
        .userId(e.getUserId()).build();
  }

  @Override
  public Long regist(SecsnDto dto) {
    return repo.save(toEntity(dto)).getSecsnId();
  }

  @Override
  public SecsnDto findById(Long secsnId) {
    Optional<Secsn> opt = repo.findById(secsnId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public List<SecsnDto> findAllByUserId(String userId) {
    List<SecsnDto> dtos = new ArrayList<>();

    repo.findAllByUserId(userId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
