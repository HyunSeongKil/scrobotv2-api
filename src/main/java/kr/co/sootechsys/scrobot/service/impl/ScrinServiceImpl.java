package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.CompnDto;
import kr.co.sootechsys.scrobot.domain.ScrinDto;
import kr.co.sootechsys.scrobot.entity.Scrin;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.ScrinRepository;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;
import kr.co.sootechsys.scrobot.service.ScrinService;

@Service
@Api(value = "화면 서비스")
public class ScrinServiceImpl implements ScrinService {

  private ScrinRepository repo;
  private ScrinGroupService scrinGroupService;
  private CompnService compnService;

  public ScrinServiceImpl(ScrinRepository repo, ScrinGroupService scrinGroupService, CompnService compnService) {
    this.repo = repo;
    this.scrinGroupService = scrinGroupService;
    this.compnService = compnService;
  }

  ScrinDto toDto(Scrin e) {
    return ScrinDto.builder().scrinId(e.getScrinId()).scrinNm(e.getScrinNm()).scrinSeCode(e.getScrinSeCode())
        .scrinGroupId(e.getScrinGroupId()).menuId(e.getMenuId()).prjctId(e.getPrjctId()).build();
  }

  Scrin toEntity(ScrinDto dto) {
    return Scrin.builder().scrinId(dto.getScrinSeCode() + Util.getShortUuid()).scrinNm(dto.getScrinNm())
        .scrinSeCode(dto.getScrinSeCode()).scrinGroupId(dto.getScrinGroupId()).menuId(dto.getMenuId())
        .prjctId(dto.getPrjctId())
        .registDt(new Date()).build();
  }

  @Override
  @Transactional
  public String copy(String srcScrinId, ScrinDto trgetDto) {
    // 화면 복사
    String trgetScrinId = copyScrin(srcScrinId, trgetDto);

    // 콤포넌트 복사
    copyCompn(srcScrinId, trgetScrinId);

    return trgetScrinId;

  }

  /**
   * 콤포넌트 복사
   * 
   * @param srcScrinId   원본 화면아이디
   * @param trgetScrinId 대상 화면아이디
   */
  void copyCompn(String srcScrinId, String trgetScrinId) {
    // 원본 콤포넌트 목록
    List<CompnDto> srcCompnDtos = compnService.findAllByScrinId(srcScrinId);

    srcCompnDtos.forEach(x -> {
      x.setCompnId("");
      x.setScrinId(trgetScrinId);

      compnService.regist(x);
    });

  }

  /**
   * 화면 복사
   * 
   * @param srcScrinId 원본 화면아이디
   * @param trgetDto   대상 dto
   * @returns 대상 화면아이디
   */
  @Transactional
  String copyScrin(String srcScrinId, ScrinDto trgetDto) {
    // 원본 화면정보 조회
    Optional<Scrin> opt = repo.findById(srcScrinId);
    if (opt.isEmpty()) {
      return null;
    }
    ScrinDto srcScrinDto = toDto(opt.get());

    // 대상 화면정보 설정
    trgetDto.setScrinGroupId(srcScrinDto.getScrinGroupId());

    // 저장
    return repo.save(toEntity(trgetDto)).getScrinId();

  }

  @Override
  public String regist(ScrinDto dto) {
    return repo.save(toEntity(dto)).getScrinId();
  }

  @Override
  @Transactional
  public void updt(ScrinDto dto) {
    Optional<Scrin> opt = repo.findById(dto.getScrinId());
    if (opt.isEmpty()) {
      return;
    }

    Scrin e = opt.get();
    e.setScrinNm(dto.getScrinNm());
    e.setScrinSeCode(dto.getScrinSeCode());

    repo.save(e);
  }

  @Override
  @Transactional
  public void delete(String scrinId) {
    // delete 컴포넌트
    compnService.deleteByScrinId(scrinId);

    repo.deleteById(scrinId);

  }

  @Override
  public ScrinDto find(String scrinId) {
    Optional<Scrin> opt = repo.findById(scrinId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public List<ScrinDto> findAllByScrinGroupId(String scrinGroupId) {
    List<ScrinDto> dtos = new ArrayList<>();

    repo.findAllByScrinGroupId(scrinGroupId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public List<ScrinDto> findAllByPrjctId(String prjctId) {
    List<ScrinDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
