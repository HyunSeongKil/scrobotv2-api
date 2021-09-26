package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sootechsys.scrobot.domain.PrjctDto;
import kr.co.sootechsys.scrobot.entity.Prjct;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.PrjctRepository;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.PrjctService;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;
import kr.co.sootechsys.scrobot.service.ScrinService;
import lombok.extern.slf4j.Slf4j;

@Service
public class PrjctServiceImpl implements PrjctService {
  private PrjctRepository repo;

  private ScrinGroupService scrinGroupService;
  private ScrinService scrinService;
  private CompnService compnService;

  public PrjctServiceImpl(PrjctRepository repo, ScrinGroupService scrinGroupService, ScrinService scrinService, CompnService compnService){
    this.repo = repo;
    this.scrinGroupService = scrinGroupService;
    this.scrinService = scrinService;
    this.compnService = compnService;

  }

  Prjct toEntity(PrjctDto dto) {
    return Prjct.builder().prjctId(Util.getShortUuid()).prjctNm(dto.getPrjctNm()).prjctCn(dto.getPrjctCn()).userId(dto.getUserId()).build();
  }

  PrjctDto toDto(Prjct e) {
    return PrjctDto.builder().prjctId(e.getPrjctId()).prjctNm(e.getPrjctNm()).prjctCn(e.getPrjctCn()).userId(e.getUserId()).build();
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

  @Override
  @Transactional
  public String copy(String oldPrjctId) {
     // 프로젝트 조회
     PrjctDto prjctDto = findById(oldPrjctId);
     // 화면그룹 목록 조회
     prjctDto.setScrinGroupDtos(scrinGroupService.findAllByPrjctId(prjctDto.getPrjctId()));
     prjctDto.getScrinGroupDtos().forEach(scrinGroupDto->{      
       // 화면 목록 조회
       scrinGroupDto.setScrinDtos(scrinService.findAllByScrinGroupId(scrinGroupDto.getScrinGroupId()));
 
       scrinGroupDto.getScrinDtos().forEach(scrinDto->{
         // 콤포넌트 목록 조회
         scrinDto.setCompnDtos(compnService.findAllByScrinId(scrinDto.getScrinId()));
       });
     });


    // 프로젝트 등록
    String newPrjctId = regist(prjctDto);

    prjctDto.getScrinGroupDtos().forEach(scrinGroupDto->{
      scrinGroupDto.setPrjctId(newPrjctId);

      // 화면 그룹 등록
      String scrinGroupId = scrinGroupService.regist(scrinGroupDto);

      scrinGroupDto.getScrinDtos().forEach(scrinDto->{
        scrinDto.setScrinGroupId(scrinGroupId);

        // 화면 등록
        String scrinId = scrinService.regist(scrinDto);

        scrinDto.getCompnDtos().forEach(compnDto->{
          compnDto.setScrinId(scrinId);

          // 콤포넌트 등록
          compnService.regist(compnDto);
        });
      });
    });

    return newPrjctId;
  }


}
