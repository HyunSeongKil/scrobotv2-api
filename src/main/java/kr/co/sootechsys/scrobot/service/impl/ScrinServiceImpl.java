package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.sootechsys.scrobot.domain.ScrinDto;
import kr.co.sootechsys.scrobot.entity.Scrin;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.ScrinRepository;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;
import kr.co.sootechsys.scrobot.service.ScrinService;

@Service
public class ScrinServiceImpl implements ScrinService {

  private ScrinRepository repo;
  private ScrinGroupService scrinGroupService;

  public ScrinServiceImpl(ScrinRepository repo, ScrinGroupService scrinGroupService){
    this.repo = repo;
    this.scrinGroupService = scrinGroupService;
  }

  ScrinDto toDto(Scrin e){
    return ScrinDto.builder()
      .scrinId(e.getScrinId())
      .scrinNm(e.getScrinNm())
      .scrinSeCode(e.getScrinSeCode())
      .scrinGroupId(e.getScrinGroupId())
      .build();
  }

  Scrin toEntity(ScrinDto dto){
    return Scrin.builder()
      .scrinId(Util.getShortUuid())
      .scrinNm(dto.getScrinNm())
      .scrinSeCode(dto.getScrinSeCode())
      .scrinGroupId(dto.getScrinGroupId())
      .build();
  }



  @Override
  public String regist(ScrinDto dto) {
    return repo.save(toEntity(dto)).getScrinId();
  }

  @Override
  public void updt(ScrinDto dto) {
    if(null == find(dto.getScrinId())){
      return;
    }

    Scrin e = toEntity(dto);
    e.setScrinId(dto.getScrinId());

    repo.save(e);
  }

  @Override
  public void delete(String scrinId) {
    // TODO delete 컴포넌트

    repo.deleteById(scrinId);
    
  }

  @Override
  public ScrinDto find(String scrinId) {
    Optional<Scrin> opt = repo.findById(scrinId);
    if(opt.isPresent()){
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public List<ScrinDto> findAllByScrinGroupId(String scrinGroupId) {
    List<ScrinDto> dtos = new ArrayList<>();

    repo.findAllByScrinGroupId(scrinGroupId).forEach(e->{
      dtos.add(toDto(e));
    });


    return dtos;
  }

  @Override
  public List<ScrinDto> findAllByPrjctId(String prjctId) {
    List<ScrinDto> scrinDtos = new ArrayList<>();
    scrinGroupService.findAllByPrjctId(prjctId).forEach(scrinGroupDto->{      
      scrinDtos.addAll(findAllByScrinGroupId(scrinGroupDto.getScrinGroupId()));
    });

    return scrinDtos;
  }

 
  
}
