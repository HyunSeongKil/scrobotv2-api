package kr.co.sootechsys.scrobot.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.AtchmnflDto;
import kr.co.sootechsys.scrobot.entity.Atchmnfl;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.AtchmnflRepository;
import kr.co.sootechsys.scrobot.service.AtchmnflGroupService;
import kr.co.sootechsys.scrobot.service.AtchmnflService;

@Service
@Api(value = "첨부파일 서비스")
public class AtchmnflServiceImpl implements AtchmnflService {

  @Value("${app.upload.file.path}")
  private String uploadFilePath;

  private AtchmnflRepository repo;
  private AtchmnflGroupService atchmnflGroupService;

  public AtchmnflServiceImpl(AtchmnflRepository repo, AtchmnflGroupService atchmnflGroupService) {
    this.repo = repo;
    this.atchmnflGroupService = atchmnflGroupService;
  }

  Atchmnfl toEntity(AtchmnflDto dto) {
    Atchmnfl e = Atchmnfl.builder().atchmnflEtsion(dto.getAtchmnflEtsion())
        .atchmnflFileszValue(dto.getAtchmnflFileszValue()).atchmnflGroupId(dto.getAtchmnflGroupId())
        .atchmnflStorgPathValue(dto.getAtchmnflStorgPathValue()).encodingDstnctCd(dto.getEncodingDstnctCd())
        .originalFileNm(dto.getOriginalFileNm()).storgFileNm(dto.getStorgFileNm()).build();

    if (null != dto.getAtchmnflId() && 0 != dto.getAtchmnflId()) {
      e.setAtchmnflId(dto.getAtchmnflId());
    } else {
      e.setRegistDt(new Date());
    }

    return e;
  }

  AtchmnflDto toDto(Atchmnfl e) {
    return AtchmnflDto.builder().atchmnflEtsion(e.getAtchmnflEtsion()).atchmnflFileszValue(e.getAtchmnflFileszValue())
        .atchmnflGroupId(e.getAtchmnflGroupId()).atchmnflId(e.getAtchmnflId())
        .atchmnflStorgPathValue(e.getAtchmnflStorgPathValue()).encodingDstnctCd(e.getEncodingDstnctCd())
        .originalFileNm(e.getOriginalFileNm()).registDt(e.getRegistDt()).storgFileNm(e.getStorgFileNm()).build();
  }

  @Override
  public Long regist(AtchmnflDto dto) {
    return repo.save(toEntity(dto)).getAtchmnflId();
  }

  @Override
  @Transactional
  public Long regist(List<MultipartFile> files) throws IllegalStateException, IOException {
    if (null == files || 0 == files.size()) {
      return null;
    }

    Long atchmnflGroupId = atchmnflGroupService.regist(null);
    for (MultipartFile mf : files) {
      AtchmnflDto dto = transferTo(atchmnflGroupId, mf);
      repo.save(toEntity(dto));
    }

    return atchmnflGroupId;
  }

  @Override
  @Transactional
  public Long regist(Long atchmnflGroupId, MultipartFile file) throws IllegalStateException, IOException {
    String yyyy = LocalDate.now().getYear() + "";

    // 확장자
    String ext = Util.getFileExt(file.getOriginalFilename()).get();

    // 팡리명
    String filename = Util.getShortUuid() + "." + ext;

    // 파일저장
    file.transferTo(Paths.get(uploadFilePath, yyyy, filename));

    // dto 생성
    AtchmnflDto dto = AtchmnflDto.builder().atchmnflEtsion(ext).atchmnflFileszValue(file.getSize())
        .atchmnflGroupId(atchmnflGroupId).atchmnflStorgPathValue(yyyy).originalFileNm(file.getOriginalFilename())
        .storgFileNm(filename).build();

    //
    return repo.save(toEntity(dto)).getAtchmnflId();
  }

  @Override
  public AtchmnflDto findById(Long atchmnflId) {
    Optional<Atchmnfl> opt = repo.findById(atchmnflId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public File getFile(Long atchmnflId) {
    AtchmnflDto dto = findById(atchmnflId);
    if (null == dto) {
      return null;
    }

    return Paths.get(uploadFilePath, dto.getAtchmnflStorgPathValue(), dto.getStorgFileNm()).toFile();
  }

  @Override
  public List<AtchmnflDto> findAllByPrjctId(String prjctId) {
    List<AtchmnflDto> dtos = new ArrayList<>();
    repo.findAllByPrjctId(prjctId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  public AtchmnflDto transferTo(Long atchmnflGroupId, MultipartFile mf) throws IllegalStateException, IOException {
    // dto 생성
    AtchmnflDto dto = AtchmnflDto.builder().atchmnflEtsion(Util.getFileExt(mf.getOriginalFilename()).get())
        .atchmnflFileszValue(mf.getSize()).atchmnflGroupId(atchmnflGroupId)
        .atchmnflStorgPathValue(LocalDate.now().getYear() + "").originalFileNm(mf.getOriginalFilename())
        .registDt(new Date()).storgFileNm(Util.getShortUuid() + "." + Util.getFileExt(mf.getOriginalFilename()).get())
        .build();

    // 파일 저장
    mf.transferTo(Paths.get(uploadFilePath, dto.getAtchmnflStorgPathValue(), dto.getStorgFileNm()));

    return dto;

  }

  @Override
  public List<AtchmnflDto> findAllByAtchmnflGroupId(Long atchmnflGroupId) {
    List<AtchmnflDto> dtos = new ArrayList<>();

    repo.findAllByAtchmnflGroupId(atchmnflGroupId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

}
