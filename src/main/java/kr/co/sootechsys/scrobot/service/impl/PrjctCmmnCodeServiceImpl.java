package kr.co.sootechsys.scrobot.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;
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
        .prjctId(dto.getPrjctId()).prntsPrjctCmmnCode(dto.getPrntsPrjctCmmnCode()).registerId(dto.getRegisterId())
        .registerNm(dto.getRegisterNm()).useAt(dto.getUseAt()).build();

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
        .prntsPrjctCmmnCode(e.getPrntsPrjctCmmnCode()).registDt(e.getRegistDt()).registerId(e.getRegisterId())
        .registerNm(e.getRegisterNm()).useAt(e.getUseAt()).build();
  }

  @Override
  public Long regist(PrjctCmmnCodeDto dto) {
    return repo.save(toEntity(dto)).getPrjctCmmnCodeId();
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
  public List<PrjctCmmnCodeDto> findAllByPrjctIdAndPrntsPrjctCmmnCode(String prjctId, String prntsPrjctCmmnCode) {
    List<PrjctCmmnCodeDto> dtos = new ArrayList<>();

    repo.findAllByPrjctIdAndPrntsPrjctCmmnCode(prjctId, prntsPrjctCmmnCode).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public List<Map<String, Object>> parseExcel(MultipartFile mf) throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();

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
    for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
      Row row = sheet.getRow(i);
      Map<String, Object> map = new LinkedHashMap<>();
      list.add(map);

      for (int j = 0; j < row.getLastCellNum(); j++) {
        Cell cell = row.getCell(j);

        String key = (0 == i ? "header_" : "data_" + j);
        map.put(key, cell.getStringCellValue());
      }
    }

    return list;
  }

}
