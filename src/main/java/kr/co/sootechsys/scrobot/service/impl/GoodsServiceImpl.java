package kr.co.sootechsys.scrobot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;

import kr.co.sootechsys.scrobot.domain.GoodsDto;
import kr.co.sootechsys.scrobot.domain.SearchGoodsDto;
import kr.co.sootechsys.scrobot.entity.Goods;
import kr.co.sootechsys.scrobot.persistence.GoodsRepository;
import kr.co.sootechsys.scrobot.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

  private GoodsRepository repo;

  public GoodsServiceImpl(GoodsRepository repo) {
    this.repo = repo;
  }

  Goods toEntity(GoodsDto dto) {
    Goods e = Goods.builder().goodsCn(dto.getGoodsCn()).goodsKeyNm(dto.getGoodsKeyNm()).goodsNm(dto.getGoodsNm())
        .goodsPriceValue(dto.getGoodsPriceValue()).registerId(dto.getRegisterId()).registerNm(dto.getRegisterNm())
        .build();

    if (null == dto.getGoodsId() || 0 > dto.getGoodsId()) {
      e.setRegistDt(new Date());
    } else {
      e.setGoodsId(dto.getGoodsId());
    }

    return e;
  }

  GoodsDto toDto(Goods e) {
    return GoodsDto.builder().goodsCn(e.getGoodsCn()).goodsId(e.getGoodsId()).goodsKeyNm(e.getGoodsKeyNm())
        .goodsNm(e.getGoodsNm()).goodsPriceValue(e.getGoodsPriceValue()).registDt(e.getRegistDt())
        .registerId(e.getRegisterId()).registerNm(e.getRegisterNm()).build();
  }

  @Override
  @Transactional
  public Long regist(GoodsDto dto) {
    return repo.save(toEntity(dto)).getGoodsId();
  }

  @Override
  @Transactional
  public void update(GoodsDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  @Transactional
  public void deleteById(Long goodsId) {
    repo.deleteById(goodsId);
  }

  @Override
  public GoodsDto findById(Long goodsId) {
    Optional<Goods> opt = repo.findById(goodsId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public Map<String, Object> findAll(SearchGoodsDto searchDto, Pageable pageable) {
    Page<Goods> page = repo.findAll(new Specification<Goods>() {
      @Override
      public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // 키
        if (null != searchDto.getGoodsKeyNm() && 0 < searchDto.getGoodsKeyNm().length()) {
          predicates
              .add(criteriaBuilder
                  .and(criteriaBuilder.like(root.get("goodsKeyNm"), "%" + searchDto.getGoodsKeyNm() + "%")));
        }

        // 명
        if (null != searchDto.getGoodsNm() && 0 < searchDto.getGoodsNm().length()) {
          predicates
              .add(criteriaBuilder.and(criteriaBuilder.like(root.get("goodsNm"), "%" + searchDto.getGoodsNm() + "%")));
        }

        query.orderBy(criteriaBuilder.desc(root.get("goodsNm")));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);

    List<GoodsDto> dtos = new ArrayList<>();
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
  public List<GoodsDto> findAllByUserId(String userId) {
    List<GoodsDto> dtos = new ArrayList<>();

    repo.findAllByUserId(userId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
