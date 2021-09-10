package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.co.sootechsys.scrobot.domain.ScrinGroupDto;
import kr.co.sootechsys.scrobot.entity.ScrinGroup;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.ScrinGroupRepository;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;

@Service
public class ScrinGroupServiceImpl implements ScrinGroupService {

  private ScrinGroupRepository repo;

  public ScrinGroupServiceImpl(ScrinGroupRepository repo){
    this.repo = repo;
  }

  ScrinGroupDto toDto(ScrinGroup e){
    return ScrinGroupDto.builder()
      .prjctId(e.getPrjctId())
      .scrinGroupId(e.getScrinGroupId())
      .scrinGroupNm(e.getScrinGroupNm())
      .build();
  }

  ScrinGroup toEntity(ScrinGroupDto dto){
    return ScrinGroup.builder()
      .scrinGroupId(Util.getShortUuid())
      .scrinGroupNm(dto.getScrinGroupNm())
      .prjctId(dto.getPrjctId())
      .build();
  }

  @Override
  public String regist(ScrinGroupDto dto) {
    return repo.save(toEntity(dto)).getPrjctId();
  }

  @Override
  public void updt(ScrinGroupDto dto) {
    if(null == find(dto.getScrinGroupId())){
      return;
    }

    ScrinGroup e = toEntity(dto);
    e.setScrinGroupId(dto.getScrinGroupId());

    repo.save(e);
  }

  @Override
  public void deleteById(String scrinGroupId) {
    // TODO delete 화면

    repo.deleteById(scrinGroupId);
    
  }

  @Override
  public ScrinGroupDto find(String scrinGroupId) {
    Optional<ScrinGroup> opt = repo.findById(scrinGroupId);
    if(opt.isPresent()){
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public List<ScrinGroupDto> findAllByPrjctId(String prjctId) {
    List<ScrinGroupDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e->{
      dtos.add(toDto(e));
    });

    return dtos;
  }
  
}
