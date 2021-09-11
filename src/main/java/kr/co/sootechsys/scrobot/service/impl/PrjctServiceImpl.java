package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import kr.co.sootechsys.scrobot.domain.PrjctDto;
import kr.co.sootechsys.scrobot.entity.Prjct;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.PrjctRepository;
import kr.co.sootechsys.scrobot.service.PrjctService;
import lombok.extern.slf4j.Slf4j;

@Service
public class PrjctServiceImpl implements PrjctService {
  private PrjctRepository repo;

  public PrjctServiceImpl(PrjctRepository repo) {
    this.repo = repo;

  }

  Prjct toEntity(PrjctDto dto) {
    return Prjct.builder().prjctId(Util.getShortUuid()).prjctNm(dto.getPrjctNm()).userId(dto.getUserId()).build();
  }

  PrjctDto toDto(Prjct e) {
    return PrjctDto.builder().prjctId(e.getPrjctId()).prjctNm(e.getPrjctNm()).userId(e.getUserId()).build();
  }


  @Override
  public String regist(PrjctDto dto) {
    return repo.save(toEntity(dto)).getPrjctId();
  }

  @Override
  public void updt(PrjctDto dto) {
    Optional<Prjct> opt = repo.findById(dto.getUserId());
    if (opt.isEmpty()) {
      return;
    }

    Prjct e = toEntity(dto);
    e.setPrjctId(dto.getUserId());

    repo.save(e);
  }

  @Override
  public void delete(String prjctId) {
    // TODO delete 화면그룹

    // TODO delete 메뉴


    repo.deleteById(prjctId);
  }

  @Override
  public PrjctDto findById(String prjctId) {
    Optional<Prjct> opt = repo.findById(prjctId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public List<PrjctDto> findAllByUserId(String userId) {
    List<PrjctDto> dtos = new ArrayList<>();

    repo.findAllByUserId(userId).forEach(e -> {
      dtos.add(toDto(e));
    });


    return dtos;
  }


}
