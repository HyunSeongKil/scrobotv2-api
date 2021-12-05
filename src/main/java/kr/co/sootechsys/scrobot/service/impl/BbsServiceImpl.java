package kr.co.sootechsys.scrobot.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.nio.file.Paths;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.jpa.domain.Specification;

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
        .registerNm(dto.getRegisterNm()).fixingAt(dto.getFixingAt()).inqryTyCd(dto.getInqryTyCd()).build();

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
        .registDt(e.getRegistDt()).registerId(e.getRegisterId()).registerNm(e.getRegisterNm()).fixingAt(e.getFixingAt())
        .inqryTyCd(e.getInqryTyCd())
        .build();
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

    // TODO delete 첨부파일

    //
    repo.deleteById(bbsId);
  }

  @Override
  public Map<String, Object> findAll(SearchBbsDto searchDto, Pageable pageable) {
    Page<Bbs> page = repo.findAll(new Specification<Bbs>() {
      @Override
      public Predicate toPredicate(Root<Bbs> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // 명
        if (null != searchDto.getBbsSjNm() && 0 < searchDto.getBbsSjNm().length()) {
          predicates
              .add(criteriaBuilder.and(criteriaBuilder.like(root.get("bbsSjNm"), "%" + searchDto.getBbsSjNm() + "%")));
        }

        // 내용
        if (null != searchDto.getBbsCn() && 0 < searchDto.getBbsCn().length()) {
          predicates
              .add(criteriaBuilder.and(criteriaBuilder.like(root.get("bbsCn"), "%" + searchDto.getBbsCn() + "%")));
        }

        // 구분
        if (null != searchDto.getBbsSeCd() && 0 < searchDto.getBbsSeCd().length()) {
          predicates
              .add(criteriaBuilder.and(criteriaBuilder.like(root.get("bbsSeCd"), "%" + searchDto.getBbsSeCd() + "%")));
        }

        // 질의 유형
        if (null != searchDto.getInqryTyCd() && 0 < searchDto.getInqryTyCd().length()) {
          predicates
              .add(criteriaBuilder
                  .and(criteriaBuilder.like(root.get("inqryTyCd"), "%" + searchDto.getInqryTyCd() + "%")));
        }

        // 시작 일자
        // 종료 일자

        query.orderBy(criteriaBuilder.desc(root.get("registDt")));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);

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

  @Override
  @Transactional
  public void updt(BbsDto dto, List<MultipartFile> files) throws IllegalStateException, IOException {
    // 첨부파일 존재하면
    if (null != files && 0 < files.size()) {
      // 첨부파일 그룹아이디 미존재이면 생성&등록
      if (null == dto.getAtchmnflGroupId()) {
        dto.setAtchmnflGroupId(atchmnflService.regist(files));
      } else {
        for (MultipartFile file : files) {
          atchmnflService.regist(dto.getAtchmnflGroupId(), file);
        }
      }
    }

    //
    repo.save(toEntity(dto));
  }

}
