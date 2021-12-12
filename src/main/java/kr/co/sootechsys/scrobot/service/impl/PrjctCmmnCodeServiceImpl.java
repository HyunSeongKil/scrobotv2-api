package kr.co.sootechsys.scrobot.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.text.Keymap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.jpa.domain.Specification;

import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;
import kr.co.sootechsys.scrobot.domain.SearchPrjctCmmnCodeDto;
import kr.co.sootechsys.scrobot.entity.PrjctCmmnCode;
import kr.co.sootechsys.scrobot.persistence.PrjctCmmnCodeRepository;
import kr.co.sootechsys.scrobot.service.PrjctCmmnCodeService;

@Service
public class PrjctCmmnCodeServiceImpl implements PrjctCmmnCodeService {

  private PrjctCmmnCodeRepository repo;

  public PrjctCmmnCodeServiceImpl(PrjctCmmnCodeRepository repo) {
    this.repo = repo;
  }

  PrjctCmmnCode toEntity(PrjctCmmnCodeDto dto) {
    PrjctCmmnCode e = PrjctCmmnCode.builder().cmmnCode(dto.getCmmnCode()).cmmnCodeNm(dto.getCmmnCodeNm())
        .prjctId(dto.getPrjctId()).prntsCmmnCode(dto.getPrntsCmmnCode()).registerId(dto.getRegisterId())
        .registerNm(dto.getRegisterNm()).useAt(dto.getUseAt()).cmmnCodeCn(dto.getCmmnCodeCn())
        .registDt(dto.getRegistDt()).build();

    if (null == dto.getPrjctCmmnCodeId() || 0 >= dto.getPrjctCmmnCodeId()) {
      e.setRegistDt(new Date());
    } else {
      e.setPrjctCmmnCodeId(dto.getPrjctCmmnCodeId());
    }

    return e;
  }

  PrjctCmmnCodeDto toDto(PrjctCmmnCode e) {
    return PrjctCmmnCodeDto.builder().cmmnCode(e.getCmmnCode()).cmmnCodeNm(e.getCmmnCodeNm())
        .prjctCmmnCodeId(e.getPrjctCmmnCodeId()).prjctId(e.getPrjctId())
        .prntsCmmnCode(e.getPrntsCmmnCode()).registDt(e.getRegistDt()).registerId(e.getRegisterId())
        .registerNm(e.getRegisterNm()).useAt(e.getUseAt()).cmmnCodeCn(e.getCmmnCodeCn()).build();
  }

  @Override
  @Transactional
  public Long regist(PrjctCmmnCodeDto dto) {
    return repo.save(toEntity(dto)).getPrjctCmmnCodeId();
  }

  @Override
  @Transactional
  public List<Long> regist(List<PrjctCmmnCodeDto> dtos) {
    List<Long> ids = new ArrayList<>();

    dtos.forEach(dto -> {
      ids.add(regist(dto));
    });

    return ids;
  }

  @Override
  public void update(PrjctCmmnCodeDto dto) {
    repo.save(toEntity(dto));
  }

  @Override
  public void deleteById(Long prjctCmmnCodeId) {
    repo.deleteById(prjctCmmnCodeId);
  }

  @Override
  public List<PrjctCmmnCodeDto> findAllByPrjctId(String prjctId) {
    List<PrjctCmmnCodeDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public PrjctCmmnCodeDto findByCmmnCode(String cmmnCode) {
    Optional<PrjctCmmnCode> opt = repo.findByCmmnCode(cmmnCode);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    //
    return null;
  }

  @Override
  public List<PrjctCmmnCodeDto> findAllByPrjctIdAndPrntsCmmnCode(String prjctId, String prntsCmmnCode) {
    List<PrjctCmmnCodeDto> dtos = new ArrayList<>();

    repo.findAllByPrjctIdAndPrntsCmmnCode(prjctId, prntsCmmnCode).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public List<Map<String, Object>> parseExcel(MultipartFile mf) throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();

    // 엑셀파일의 cell 순서 중요
    Map<Integer, String> keyMap = Map.of(0, "cmmnCode", 1, "cmmnCodeNm", 2, "cmmnCodeCn", 3, "prntsCmmnCode");

    Workbook workbook = null;

    if (mf.getOriginalFilename().endsWith(".xlsx")) {
      workbook = new XSSFWorkbook(mf.getInputStream());
    } else if (mf.getOriginalFilename().endsWith(".xls")) {
      workbook = new HSSFWorkbook(mf.getInputStream());
    }

    if (null == workbook) {
      return list;
    }

    Sheet sheet = workbook.getSheetAt(0);
    // 0은 제목줄이라 skip
    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
      Row row = sheet.getRow(i);
      Map<String, Object> map = new LinkedHashMap<>();
      list.add(map);

      for (int j = 0; j < row.getLastCellNum(); j++) {
        Cell cell = row.getCell(j);

        String key = keyMap.get(j);
        map.put(key, cell.getStringCellValue());
      }
    }

    return list;
  }

  @Override
  public PageableResult findAll(SearchPrjctCmmnCodeDto searchDto, Pageable pageable) {
    Page<PrjctCmmnCode> page = repo.findAll(new Specification<PrjctCmmnCode>() {
      @Override
      public Predicate toPredicate(Root<PrjctCmmnCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        //
        if (null != searchDto.getPrjctId() && 0 < searchDto.getPrjctId().length()) {
          predicates.add(
              criteriaBuilder.and(criteriaBuilder.like(root.get("prjctId"), "%" + searchDto.getPrjctId() + "%")));
        }

        // 코드
        if (null != searchDto.getCmmnCode() && 0 < searchDto.getCmmnCode().length()) {
          predicates.add(
              criteriaBuilder.and(criteriaBuilder.like(root.get("cmmnCode"), "%" + searchDto.getCmmnCode() + "%")));
        }

        // 명
        if (null != searchDto.getCmmnCodeNm() && 0 < searchDto.getCmmnCodeNm().length()) {
          predicates.add(
              criteriaBuilder.and(criteriaBuilder.like(root.get("cmmnCodeNm"), "%" + searchDto.getCmmnCodeNm() + "%")));
        }

        query.orderBy(criteriaBuilder.desc(root.get("prntsCmmnCode")));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);

    List<PrjctCmmnCodeDto> dtos = new ArrayList<>();
    page.getContent().forEach(e -> {
      dtos.add(toDto(e));
    });

    return PageableResult.builder().data(dtos).number(page.getNumber()).numberOfElements(page.getNumberOfElements())
        .pageable(page.getPageable()).size(page.getSize()).sort(page.getSort())
        .totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).build();
  }

  @Override
  public PrjctCmmnCodeDto findById(Long prjctCmmnCodeId) {
    Optional<PrjctCmmnCode> opt = repo.findById(prjctCmmnCodeId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

}
