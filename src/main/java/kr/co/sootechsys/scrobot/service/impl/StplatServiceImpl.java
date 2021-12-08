package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchStplatDto;
import kr.co.sootechsys.scrobot.domain.StplatDto;
import kr.co.sootechsys.scrobot.entity.Stplat;
import kr.co.sootechsys.scrobot.persistence.StplatRepository;
import kr.co.sootechsys.scrobot.service.StplatService;

@Service
public class StplatServiceImpl implements StplatService {

  private StplatRepository repo;

  public StplatServiceImpl(StplatRepository repo) {
    this.repo = repo;
  }

  private Stplat toEntity(StplatDto dto) {
    Stplat e = Stplat.builder().registerId(dto.getRegisterId()).registerNm(dto.getRegisterNm()).stplatCn(dto.getStplatCn()).stplatNm(dto.getStplatNm()).build();

    if (null == dto.getStplatId() || 0 > dto.getStplatId()) {
      e.setRegistDt(new Date());
    } else {
      e.setStplatId(dto.getStplatId());
    }

    return e;
  }

  private StplatDto toDto(Stplat e) {
    return StplatDto.builder().stplatId(e.getStplatId()).registDt(e.getRegistDt()).registerId(e.getRegisterId()).registerNm(e.getRegisterNm()).stplatCn(e.getStplatCn()).stplatNm(e.getStplatNm())
        .build();

  }

  @Override
  public Long regist(StplatDto dto) {
    return repo.save(toEntity(dto)).getStplatId();
  }

  @Override
  public void update(StplatDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  public void deleteById(Long stplatId) {
    repo.deleteById(stplatId);
  }

  @Override
  public StplatDto findById(Long stplatId) {
    Optional<Stplat> opt = repo.findById(stplatId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;

  }

  @Override
  public PageableResult findAll(SearchStplatDto searchDto, Pageable pageable) {
    Page<Stplat> page = repo.findAll(new Specification<Stplat>() {
      @Override
      public Predicate toPredicate(Root<Stplat> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // 명
        if (null != searchDto.getStplatNm() && 0 < searchDto.getStplatNm().length()) {
          predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("stplatNm"), "%" + searchDto.getStplatNm() + "%")));
        }

        // 내용
        if (null != searchDto.getStplatCn() && 0 < searchDto.getStplatCn().length()) {
          predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("stplatCn"), "%" + searchDto.getStplatCn() + "%")));
        }


        query.orderBy(criteriaBuilder.desc(root.get("registDt")));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);

    List<StplatDto> dtos = new ArrayList<>();
    page.getContent().forEach(e -> {
      dtos.add(toDto(e));
    });

    return PageableResult.builder().data(dtos).number(page.getNumber()).numberOfElements(page.getNumberOfElements()).pageable(page.getPageable()).size(page.getSize()).sort(page.getSort())
        .totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).build();
  }

}
