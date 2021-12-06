package kr.co.sootechsys.scrobot.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchSecsnDto;
import kr.co.sootechsys.scrobot.domain.SecsnDto;
import kr.co.sootechsys.scrobot.entity.Secsn;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.SecsnRepository;
import kr.co.sootechsys.scrobot.service.SecsnService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SecsnServiceImpl implements SecsnService {

  @Value("${app.secret.key}")
  private String secretKey;

  private SecsnRepository repo;

  public SecsnServiceImpl(SecsnRepository repo) {
    this.repo = repo;
  }

  Secsn toEntity(SecsnDto dto) {
    Secsn e = Secsn.builder().imprvmCn(dto.getImprvmCn()).telno(dto.getTelno()).joinDt(dto.getJoinDt()).userNm(dto.getUserNm()).secsnReasonCn(dto.getSecsnReasonCn()).userId(dto.getUserId()).build();


    if (null == dto.getSecsnId() || 0 > dto.getSecsnId()) {
      e.setSecsnDt(new Date());
    } else {
      e.setSecsnId(dto.getSecsnId());
    }

    return e;
  }

  SecsnDto toDto(Secsn e) {
    return SecsnDto.builder().imprvmCn(e.getImprvmCn()).joinDt(e.getJoinDt()).secsnDt(e.getSecsnDt()).telno(e.getTelno()).userNm(e.getUserNm()).secsnId(e.getSecsnId())
        .secsnReasonCn(e.getSecsnReasonCn()).userId(e.getUserId()).build();
  }

  @Override
  public Long regist(SecsnDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    Secsn e = toEntity(dto);
    // 전화번호 암호화
    e.setTelno(Util.encodeAes(secretKey, dto.getTelno()));

    return repo.save(e).getSecsnId();
  }

  @Override
  public SecsnDto findById(Long secsnId)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    Optional<Secsn> opt = repo.findById(secsnId);
    if (opt.isPresent()) {
      SecsnDto dto = toDto(opt.get());
      // 전번 복화화
      dto.setTelno(Util.decodeAes(secretKey, opt.get().getTelno()));
      return dto;
    }

    return null;
  }

  @Override
  public List<SecsnDto> findAllByUserId(String userId) {
    List<SecsnDto> dtos = new ArrayList<>();

    repo.findAllByUserId(userId).forEach(e -> {
      SecsnDto dto = toDto(e);
      try {
        // 전화번호 복호화
        dto.setTelno(Util.decodeAes(secretKey, e.getTelno()));
      } catch (Exception e1) {
        log.error("{}", e1);
      }

      dtos.add(dto);
    });

    return dtos;
  }

  @Override
  public PageableResult findAll(SearchSecsnDto searchDto, Pageable pageable) {
    Page<Secsn> page = repo.findAll(new Specification<Secsn>() {
      @Override
      public Predicate toPredicate(Root<Secsn> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (null != searchDto.getUserId() && 0 < searchDto.getUserId().length()) {
          predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("userId"), "%" + searchDto.getUserId() + "%")));

        }

        if (null != searchDto.getUserNm() && 0 < searchDto.getUserNm().length()) {
          predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("userNm"), "%" + searchDto.getUserNm() + "%")));

        }



        query.orderBy(criteriaBuilder.desc(root.get("secsnDt")));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);

    List<SecsnDto> dtos = new ArrayList<>();
    page.getContent().forEach(e -> {
      SecsnDto dto = toDto(e);
      try {
        // 전화번호 복화화
        dto.setTelno(Util.decodeAes(secretKey, e.getTelno()));
      } catch (Exception e1) {
        log.error("{}", e1);
      }
      dtos.add(dto);
    });

    return PageableResult.builder().data(dtos).number(page.getNumber()).numberOfElements(page.getNumberOfElements()).pageable(page.getPageable()).size(page.getSize()).sort(page.getSort())
        .totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).build();

  }

}
