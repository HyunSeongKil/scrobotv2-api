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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.PrjctDto;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.entity.Prjct;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.PrjctRepository;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.MenuService;
import kr.co.sootechsys.scrobot.service.PrjctService;
import kr.co.sootechsys.scrobot.service.PrjctTrgetSysMapngService;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;
import kr.co.sootechsys.scrobot.service.ScrinService;
import kr.co.sootechsys.scrobot.service.TrgetSysService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Api(value = "프로젝트 서비스")
public class PrjctServiceImpl implements PrjctService {
  private PrjctRepository repo;

  private ScrinGroupService scrinGroupService;
  private ScrinService scrinService;
  private CompnService compnService;
  private MenuService menuService;
  private TrgetSysService trgetSysService;
  private PrjctTrgetSysMapngService prjctTrgetSysMapngService;

  public PrjctServiceImpl(PrjctRepository repo, PrjctTrgetSysMapngService prjctTrgetSysMapngService,
      TrgetSysService trgetSysService, ScrinGroupService scrinGroupService, ScrinService scrinService,
      CompnService compnService, MenuService menuService) {
    this.repo = repo;
    this.scrinGroupService = scrinGroupService;
    this.scrinService = scrinService;
    this.compnService = compnService;
    this.menuService = menuService;
    this.trgetSysService = trgetSysService;
    this.prjctTrgetSysMapngService = prjctTrgetSysMapngService;

  }

  Prjct toEntity(PrjctDto dto) {
    return Prjct.builder().registDt(new Date()).prjctId(Util.getShortUuid()).prjctNm(dto.getPrjctNm())
        .prjctCn(dto.getPrjctCn()).userId(dto.getUserId()).build();
  }

  PrjctDto toDto(Prjct e) {
    return PrjctDto.builder().registDt(e.getRegistDt()).prjctId(e.getPrjctId()).prjctNm(e.getPrjctNm())
        .prjctCn(e.getPrjctCn()).userId(e.getUserId()).build();
  }

  @Override
  public String regist(PrjctDto dto) {
    return repo.save(toEntity(dto)).getPrjctId();
  }

  @Override
  public void updt(PrjctDto dto) {
    Optional<Prjct> opt = repo.findById(dto.getPrjctId());
    if (opt.isEmpty()) {
      return;
    }

    Prjct e = opt.get();
    e.setPrjctCn(dto.getPrjctCn());
    e.setPrjctNm(dto.getPrjctNm());
    e.setUpdtDt(new Date());
    e.setUserId(dto.getUserId());

    repo.save(e);
  }

  @Override
  @Transactional
  public void delete(String prjctId) {
    scrinGroupService.findAllByPrjctId(prjctId).forEach(scrinGroupDto -> {
      scrinService.findAllByScrinGroupId(scrinGroupDto.getScrinGroupId()).forEach(scrinDto -> {
        compnService.findAllByScrinId(scrinDto.getScrinId()).forEach(compnDto -> {
          // delete 콤포넌트
          compnService.delete(compnDto.getCompnId());
        });

        // delete 화면
        scrinService.delete(scrinDto.getScrinId());
      });

      // delete 화면그룹
      scrinGroupService.deleteById(scrinGroupDto.getScrinGroupId());
    });

    // delete 메뉴
    menuService.deleteByPrjctId(prjctId);

    // delete 프로젝트
    repo.deleteById(prjctId);
  }

  @Override
  public PrjctDto findById(String prjctId) {
    Optional<Prjct> opt = repo.findById(prjctId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;
  }

  @Override
  public List<PrjctDto> findAllByUserId(String userId) {
    List<PrjctDto> dtos = new ArrayList<>();

    repo.findAllByUserId(userId).forEach(e -> {
      dtos.add(toDto(e));
    });

    return dtos;
  }

  @Override
  @Transactional
  public String copy(String oldPrjctId) {
    // 프로젝트 조회
    PrjctDto prjctDto = findById(oldPrjctId);
    // 화면그룹 목록 조회
    prjctDto.setScrinGroupDtos(scrinGroupService.findAllByPrjctId(prjctDto.getPrjctId()));
    prjctDto.getScrinGroupDtos().forEach(scrinGroupDto -> {
      // 화면 목록 조회
      scrinGroupDto.setScrinDtos(scrinService.findAllByScrinGroupId(scrinGroupDto.getScrinGroupId()));

      scrinGroupDto.getScrinDtos().forEach(scrinDto -> {
        // 콤포넌트 목록 조회
        scrinDto.setCompnDtos(compnService.findAllByScrinId(scrinDto.getScrinId()));
      });
    });

    // 프로젝트 등록
    prjctDto.setPrjctNm(prjctDto.getPrjctNm() + "(복사본)");
    log.debug("{}", prjctDto);
    String newPrjctId = regist(prjctDto);

    prjctDto.getScrinGroupDtos().forEach(scrinGroupDto -> {
      scrinGroupDto.setPrjctId(newPrjctId);

      // 화면 그룹 등록
      log.debug("{}", scrinGroupDto);
      String newScrinGroupId = scrinGroupService.regist(scrinGroupDto);

      scrinGroupDto.getScrinDtos().forEach(scrinDto -> {
        scrinDto.setScrinGroupId(newScrinGroupId);

        // 화면 등록
        log.debug("{}", scrinDto);
        String newScrinId = scrinService.regist(scrinDto);

        scrinDto.getCompnDtos().forEach(compnDto -> {
          compnDto.setScrinId(newScrinId);

          // 콤포넌트 등록
          log.debug("{}", compnDto);
          compnService.regist(compnDto);
        });
      });
    });

    return newPrjctId;
  }

  @Override
  @Transactional
  public void update(PrjctDto dto, TrgetSysDto trgetSysDto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    updt(dto);

    if (null == trgetSysDto.getTrgetSysId() || 0 == trgetSysDto.getTrgetSysId().length()) {
      //
      String trgetSysId = trgetSysService.regist(trgetSysDto);
      prjctTrgetSysMapngService.regist(dto.getPrjctId(), trgetSysId);
    } else {
      trgetSysService.updt(trgetSysDto);
    }

  }

  @Override
  @Transactional
  public String regist(PrjctDto dto, TrgetSysDto trgetSysDto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    String prjctId = regist(dto);

    String trgetSysId = trgetSysService.regist(trgetSysDto);

    prjctTrgetSysMapngService.regist(prjctId, trgetSysId);

    return prjctId;
  }

}
