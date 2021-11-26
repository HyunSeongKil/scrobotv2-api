package kr.co.sootechsys.scrobot.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.nio.file.Paths;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sootechsys.scrobot.domain.AtchmnflDto;
import kr.co.sootechsys.scrobot.domain.BbsDto;
import kr.co.sootechsys.scrobot.domain.SearchBbsDto;
import kr.co.sootechsys.scrobot.entity.Bbs;
import kr.co.sootechsys.scrobot.persistence.BbsRepository;
import kr.co.sootechsys.scrobot.service.AtchmnflGroupService;
import kr.co.sootechsys.scrobot.service.AtchmnflService;
import kr.co.sootechsys.scrobot.service.BbsService;

@Service
public class BbsServiceImpl implements BbsService {

  private BbsRepository repo;
  private AtchmnflGroupService atchmnflGroupService;
  private AtchmnflService atchmnflService;

  public BbsServiceImpl(BbsRepository repo, AtchmnflGroupService atchmnflGroupService,
      AtchmnflService atchmnflService) {
    this.repo = repo;
    this.atchmnflGroupService = atchmnflGroupService;
    this.atchmnflService = atchmnflService;
  }

  Bbs toEntity(BbsDto dto) {
    Bbs e = Bbs.builder().atchmnflGroupId(dto.getAtchmnflGroupId()).bbsCn(dto.getBbsCn()).bbsSeCd(dto.getBbsSeCd())
        .bbsSjNm(dto.getBbsSjNm()).qaaSeCd(dto.getQaaSeCd()).registerId(dto.getRegisterId())
        .registerNm(dto.getRegisterNm()).build();

    if (null == dto.getBbsId() || 0 >= dto.getBbsId()) {
      e.setBbsId(System.nanoTime());
      e.setRegistDt(new Date());
      e.setInqireCo(0L);
    } else {
      e.setBbsId(dto.getBbsId());
    }

    return e;
  }

  BbsDto toDto(Bbs e) {
    return BbsDto.builder().atchmnflGroupId(e.getAtchmnflGroupId()).bbsCn(e.getBbsCn()).bbsId(e.getBbsId())
        .bbsSeCd(e.getBbsSeCd()).bbsSjNm(e.getBbsSjNm()).inqireCo(e.getInqireCo()).qaaSeCd(e.getQaaSeCd())
        .registDt(e.getRegistDt()).registerId(e.getRegisterId()).registerNm(e.getRegisterNm()).build();
  }

  @Override
  @Transactional
  public Long regist(BbsDto dto) {
    return repo.save(toEntity(dto)).getBbsId();
  }

  @Override
  @Transactional
  public void updt(BbsDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  @Transactional
  public void deleteById(Long bbsId) {
    // TODO delete comment

    repo.deleteById(bbsId);
  }

  @Override
  public Map<String, Object> findAll(SearchBbsDto searchDto, Pageable pageable) {
    Page<Bbs> page = null;

    if (0 == searchDto.getBbsSjNm().length() && 0 == searchDto.getBbsCn().length()) {
      page = repo.findAllByBbsSeCd(searchDto.getBbsSeCd(), pageable);
    }

    if (0 < searchDto.getBbsSjNm().length() && 0 == searchDto.getBbsCn().length()) {
      page = repo.findAllByBbsSeCdAndBbsSjNmContains(searchDto.getBbsSeCd(), searchDto.getBbsSjNm(), pageable);

    }

    if (0 == searchDto.getBbsSjNm().length() && 0 < searchDto.getBbsCn().length()) {
      page = repo.findAllByBbsSeCdAndBbsCnLike(searchDto.getBbsSeCd(), searchDto.getBbsCn(), pageable);
    }

    if (0 < searchDto.getBbsSjNm().length() && 0 < searchDto.getBbsCn().length()) {
      page = repo.findAllByBbsSeCdAndBbsSjNmLikeAndBbsCnLike(searchDto.getBbsSeCd(), searchDto.getBbsSjNm(),
          searchDto.getBbsCn(), pageable);
    }

    List<BbsDto> dtos = new ArrayList<>();
    page.getContent().forEach(e -> {
      dtos.add(toDto(e));
    });

    Map<String, Object> map = new HashMap<>();
    map.put("data", dtos);
    map.put("number", page.getNumber());
    map.put("numberOfElements", page.getNumberOfElements());
    map.put("pageable", page.getPageable());
    map.put("size", page.getSize());
    map.put("sort", page.getSort());
    map.put("totalElements", page.getTotalElements());
    map.put("totalPages", page.getTotalPages());

    return map;
  }

  @Override
  public BbsDto findById(Long bbsId) {
    Optional<Bbs> opt = repo.findById(bbsId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  @Transactional
  public Long regist(BbsDto dto, List<MultipartFile> files) throws IllegalStateException, IOException {
    if (null == files || 0 == files.size()) {
      return regist(dto);
    }

    //
    Long atchmnflGroupId = atchmnflGroupService.regist(null);

    for (MultipartFile mf : files) {
      atchmnflService.regist(atchmnflGroupId, mf);
    }

    //
    dto.setAtchmnflGroupId(atchmnflGroupId);
    return repo.save(toEntity(dto)).getBbsId();
  }

  @Override

  @Async
  public void increaseInqireCoAsync(Long bbsId) {
    Optional<Bbs> opt = repo.findById(bbsId);
    if (opt.isPresent()) {
      Bbs e = opt.get();
      if (null == e.getInqireCo()) {
        e.setInqireCo(1L);
      } else {
        e.setInqireCo(e.getInqireCo() + 1);
      }

      repo.save(e);
    }

  }

}
