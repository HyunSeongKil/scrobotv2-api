package kr.co.sootechsys.scrobot.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import kr.co.sootechsys.scrobot.domain.DomainDto;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchDomainDto;
import kr.co.sootechsys.scrobot.entity.Domain;
import kr.co.sootechsys.scrobot.persistence.DomainRepository;
import kr.co.sootechsys.scrobot.service.DomainService;

@Service
public class DomainServiceImpl implements DomainService {

  private DomainRepository repo;

  public DomainServiceImpl(DomainRepository repo) {
    this.repo = repo;
  }

  Domain toEntity(DomainDto dto) {
    Domain e = Domain.builder().dataLtValue(dto.getDataLtValue()).dataTyCd(dto.getDataTyCd()).domainClNm(dto.getDomainClNm()).domainCn(dto.getDomainCn()).domainGroupNm(dto.getDomainGroupNm())
        .domainNm(dto.getDomainNm()).stdAt(dto.getStdAt()).build();

    if (null == dto.getDomainId() || 0 > dto.getDomainId()) {
      e.setRegistDt(new java.util.Date());
    } else {
      e.setDomainId(dto.getDomainId());
    }

    return e;
  }

  DomainDto toDto(Domain e) {
    return DomainDto.builder().domainId(e.getDomainId()).dataLtValue(e.getDataLtValue()).dataTyCd(e.getDataTyCd()).domainClNm(e.getDomainClNm()).domainCn(e.getDomainCn())
        .domainGroupNm(e.getDomainGroupNm()).domainNm(e.getDomainNm()).stdAt(e.getStdAt()).build();
  }

  @Override
  public Long regist(DomainDto dto) {
    return repo.save(toEntity(dto)).getDomainId();
  }

  @Override
  public void update(DomainDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  public void deleteById(Long domainId) {
    repo.deleteById(domainId);
  }

  @Override
  public DomainDto findById(Long domainId) {
    Optional<Domain> opt = repo.findById(domainId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public PageableResult findAll(SearchDomainDto searchDto, Pageable pageable) {
    Page<Domain> page = repo.findAll(new Specification<Domain>() {
      @Override
      public Predicate toPredicate(Root<Domain> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // 명
        if (null != searchDto.getDomainNm() && 0 < searchDto.getDomainNm().length()) {
          predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("domainNm"), "%" + searchDto.getDomainNm() + "%")));
        }

        // TODO 다른조건

        query.orderBy(criteriaBuilder.desc(root.get("domainNm")));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);

    List<DomainDto> dtos = new ArrayList<>();
    page.getContent().forEach(e -> {
      dtos.add(toDto(e));
    });

    return PageableResult.builder().data(dtos).number(page.getNumber()).numberOfElements(page.getNumberOfElements()).pageable(page.getPageable()).size(page.getSize()).sort(page.getSort())
        .totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).build();
  }

  @Override
  public List<Map<String, Object>> parseExcel(MultipartFile excelFile) throws Exception {
    //
    Map<Integer, String> cellNm = Map.of(0, "domainNm", 1, "domainCn", 2, "domainGroupNm", 3, "domainClNm", 4, "dataTyCd", 5, "dataLtValue", 6, "stdAt");

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

          map.put(cellNm.get(j), getCellValue(cell));
        }
      }

    } catch (Exception e) {
      throw e;
    }

    return list;
  }

  private Object getCellValue(Cell c) {
    switch (c.getCellType()) {
      case Cell.CELL_TYPE_NUMERIC:
        return c.getNumericCellValue();
      case Cell.CELL_TYPE_STRING:
        return c.getStringCellValue();
    }

    return "";
  }

  @Override
  public Integer registByBulk(List<DomainDto> dtos) {
    if (null == dtos || 0 == dtos.size()) {
      return 0;
    }

    dtos.forEach(dto -> {
      // TODO 입력값 검사


      regist(dto);
    });

    return dtos.size();
  }

}
