package kr.co.sootechsys.scrobot.service.impl;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.CmmnCodeDto;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchCmmnCodeDto;
import kr.co.sootechsys.scrobot.entity.CmmnCode;
import kr.co.sootechsys.scrobot.persistence.CmmnCodeRepository;
import kr.co.sootechsys.scrobot.service.CmmnCodeService;

/**
 * 공통 코드
 */
@Service
@Api(value = "공통코드 서비스")
public class CmmnCodeServiceImpl implements CmmnCodeService {

  private CmmnCodeRepository repo;

  public CmmnCodeServiceImpl(CmmnCodeRepository repo) {
    this.repo = repo;
  }

  CmmnCode toEntity(CmmnCodeDto dto) {
    CmmnCode e = CmmnCode.builder().build();
    e.setCmmnCode(dto.getCmmnCode());
    e.setCmmnCodeNm(dto.getCmmnCodeNm());
    e.setPrntsCmmnCode(dto.getPrntsCmmnCode());
    e.setUseAt(dto.getUseAt());
    e.setCmmnCodeCn(dto.getCmmnCodeCn());
    e.setRegisterId(dto.getRegisterId());
    e.setRegisterNm(dto.getRegisterNm());

    if (null == dto.getCmmnCodeId() || 0 > dto.getCmmnCodeId()) {
      e.setRegistDt(new Date());
    } else {
      e.setCmmnCodeId(dto.getCmmnCodeId());
    }

    return e;
  }

  CmmnCodeDto toDto(CmmnCode e) {
    CmmnCodeDto dto = CmmnCodeDto.builder().build();
    dto.setCmmnCode(e.getCmmnCode());
    dto.setCmmnCodeId(e.getCmmnCodeId());
    dto.setCmmnCodeNm(e.getCmmnCodeNm());
    dto.setPrntsCmmnCode(e.getPrntsCmmnCode());
    dto.setUseAt(e.getUseAt());
    dto.setCmmnCodeCn(e.getCmmnCodeCn());
    dto.setRegistDt(e.getRegistDt());
    dto.setRegisterId(e.getRegisterId());
    dto.setRegisterNm(e.getRegisterNm());

    return dto;
  }

  @Override
  public List<CmmnCodeDto> findAllByPrntsCmmnCode(String prntsCmmnCode) {
    // TODO cache

    List<CmmnCodeDto> dtos = new ArrayList<>();

    repo.findAllByPrntsCmmnCode(prntsCmmnCode).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public CmmnCodeDto findById(Long cmmnCodeId) {
    // TODO cache

    Optional<CmmnCode> opt = repo.findById(cmmnCodeId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public CmmnCodeDto findByPrntsCmmnCodeAndCmmnCode(String prntsCmmnCode, String cmmnCode) {

    Optional<CmmnCode> opt = repo.findByPrntsCmmnCodeAndCmmnCode(prntsCmmnCode, cmmnCode);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public List<CmmnCodeDto> findAll() {
    List<CmmnCodeDto> dtos = new ArrayList<>();

    repo.findAll().forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public List<Map<String, Object>> parseExcel(MultipartFile excelFile) throws Exception {
    Map<Integer, String> cellNm = Map.of(0, "cmmnCode", 1, "cmmnCodeNm", 2, "cmmnCodeCn", 3, "prntsCmmnCode");

    List<Map<String, Object>> list = new ArrayList<>();

    try {
      // xlsx
      XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
      Sheet sheet = workbook.getSheetAt(0);
      int rowCo = sheet.getPhysicalNumberOfRows();
      // 1st는 제목이라 skip함
      for (int i = 1; i < rowCo; i++) {
        Row row = sheet.getRow(i);
        int cellCo = row.getPhysicalNumberOfCells();

        Map<String, Object> map = new HashMap<>();
        list.add(map);

        for (int j = 0; j < cellCo; j++) {
          Cell cell = row.getCell(j);

          map.put(cellNm.get(j), cell.getStringCellValue());
        }
      }

    } catch (Exception e) {
      throw e;
    }

    return list;
  }

  @Override
  @Transactional
  public Long regist(CmmnCodeDto dto) {
    return repo.save(toEntity(dto)).getCmmnCodeId();
  }

  @Override
  @Transactional
  public int registByBulk(List<CmmnCodeDto> dtos) {
    if (null == dtos || 0 == dtos.size()) {
      return 0;
    }

    dtos.forEach(dto -> {
      regist(dto);
    });

    return dtos.size();
  }

  @Override
  public PageableResult findAll(SearchCmmnCodeDto searchDto, Pageable pageable) {
    Page<CmmnCode> page = repo.findAll(new Specification<CmmnCode>() {
      @Override
      public Predicate toPredicate(Root<CmmnCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // 코드
        if (null != searchDto.getCmmnCode() && 0 < searchDto.getCmmnCode().length()) {
          predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("cmmnCode"), "%" + searchDto.getCmmnCode() + "%")));
        }

        // 명
        if (null != searchDto.getCmmnCodeNm() && 0 < searchDto.getCmmnCodeNm().length()) {
          predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("cmmnCodeNm"), "%" + searchDto.getCmmnCodeNm() + "%")));
        }



        query.orderBy(criteriaBuilder.asc(root.get("prntsCmmnCode")));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);

    List<CmmnCodeDto> dtos = new ArrayList<>();
    page.getContent().forEach(e -> {
      dtos.add(toDto(e));
    });

    return PageableResult.builder().data(dtos).number(page.getNumber()).numberOfElements(page.getNumberOfElements()).pageable(page.getPageable()).size(page.getSize()).sort(page.getSort())
        .totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).build();

  }

  @Override
  public void deleteById(Long cmmnCodeId) {
    repo.deleteById(cmmnCodeId);
  }

  @Override
  public void update(CmmnCodeDto dto) {
    repo.save(toEntity(dto));
  }

}
