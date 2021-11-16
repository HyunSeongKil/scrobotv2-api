package kr.co.sootechsys.scrobot.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import kr.co.sootechsys.scrobot.domain.AtchmnflDto;
import kr.co.sootechsys.scrobot.entity.Atchmnfl;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.AtchmnflRepository;
import kr.co.sootechsys.scrobot.service.AtchmnflService;

@Service
public class AtchmnflServiceImpl implements AtchmnflService {

  @Value("${app.upload.file.path}")
  private String uploadFilePath;

  private AtchmnflRepository repo;

  public AtchmnflServiceImpl(AtchmnflRepository repo) {
    this.repo = repo;
  }


  Atchmnfl toEntity(AtchmnflDto dto) {
    Atchmnfl e = Atchmnfl.builder().atchmnflEtsion(dto.getAtchmnflEtsion()).atchmnflFileszValue(dto.getAtchmnflFileszValue()).atchmnflGroupId(dto.getAtchmnflGroupId())
        .atchmnflStorgPathValue(dto.getAtchmnflStorgPathValue()).encodingDstnctCd(dto.getEncodingDstnctCd()).originalFileNm(dto.getOriginalFileNm()).storgFileNm(dto.getStorgFileNm()).build();

    if (null != dto.getAtchmnflId() && 0 != dto.getAtchmnflId()) {
      e.setAtchmnflId(dto.getAtchmnflId());
    } else {
      e.setRegistDt(new Date());
    }

    return e;
  }

  AtchmnflDto toDto(Atchmnfl e) {
    return AtchmnflDto.builder().atchmnflEtsion(e.getAtchmnflEtsion()).atchmnflFileszValue(e.getAtchmnflFileszValue()).atchmnflGroupId(e.getAtchmnflGroupId()).atchmnflId(e.getAtchmnflId())
        .atchmnflStorgPathValue(e.getAtchmnflStorgPathValue()).encodingDstnctCd(e.getEncodingDstnctCd()).originalFileNm(e.getOriginalFileNm()).registDt(e.getRegistDt()).storgFileNm(e.getStorgFileNm())
        .build();
  }

  @Override
  public Long regist(AtchmnflDto dto) {
    return repo.save(toEntity(dto)).getAtchmnflId();
  }


  @Override
  @Transactional
  public Long regist(Long atchmnflGroupId, MultipartFile file) throws IllegalStateException, IOException {
    String yyyy = LocalDate.now().getYear() + "";

    // 확장자
    String ext = Util.getFileExt(file.getOriginalFilename()).get();

    // 팡리명
    String filename = Util.getShortUuid() + "." + ext;

    //
    file.transferTo(Paths.get(uploadFilePath, yyyy, filename));

    AtchmnflDto dto = AtchmnflDto.builder().atchmnflEtsion(ext).atchmnflFileszValue(file.getSize()).atchmnflGroupId(atchmnflGroupId).atchmnflStorgPathValue(yyyy)
        .originalFileNm(file.getOriginalFilename()).storgFileNm(filename).build();

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

}
