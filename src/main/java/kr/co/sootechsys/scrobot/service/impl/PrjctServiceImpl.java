package kr.co.sootechsys.scrobot.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.NotWritablePropertyException;
import org.springframework.jdbc.core.metadata.OracleCallMetaDataProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.CompnDto;
import kr.co.sootechsys.scrobot.domain.GuidanceMssageDto;
import kr.co.sootechsys.scrobot.domain.MenuDto;
import kr.co.sootechsys.scrobot.domain.PrjctCmmnCodeDto;
import kr.co.sootechsys.scrobot.domain.PrjctDto;
import kr.co.sootechsys.scrobot.domain.PrjctTrgetSysMapngDto;
import kr.co.sootechsys.scrobot.domain.PrjctUserMapngDto;
import kr.co.sootechsys.scrobot.domain.ScrinDto;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.entity.Prjct;
import kr.co.sootechsys.scrobot.entity.PrjctCmmnCode;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.PrjctRepository;
import kr.co.sootechsys.scrobot.service.CompnService;
import kr.co.sootechsys.scrobot.service.GuidanceMssageService;
import kr.co.sootechsys.scrobot.service.MenuService;
import kr.co.sootechsys.scrobot.service.PrjctCmmnCodeService;
import kr.co.sootechsys.scrobot.service.PrjctService;
import kr.co.sootechsys.scrobot.service.PrjctTrgetSysMapngService;
import kr.co.sootechsys.scrobot.service.PrjctUserMapngService;
import kr.co.sootechsys.scrobot.service.ScrinGroupService;
import kr.co.sootechsys.scrobot.service.ScrinService;
import kr.co.sootechsys.scrobot.service.TrgetSysService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Api(value = "???????????? ?????????")
public class PrjctServiceImpl implements PrjctService {
  private PrjctRepository repo;

  private ScrinGroupService scrinGroupService;
  private ScrinService scrinService;
  private CompnService compnService;
  private MenuService menuService;
  private TrgetSysService trgetSysService;
  private PrjctTrgetSysMapngService prjctTrgetSysMapngService;
  private PrjctCmmnCodeService prjctCmmnCodeService;
  private GuidanceMssageService guidanceMssageService;
  private PrjctUserMapngService prjctUserMapngService;

  public PrjctServiceImpl(PrjctRepository repo, PrjctTrgetSysMapngService prjctTrgetSysMapngService,
      TrgetSysService trgetSysService, ScrinGroupService scrinGroupService, ScrinService scrinService,
      CompnService compnService, MenuService menuService, PrjctCmmnCodeService prjctCmmnCodeService,
      GuidanceMssageService guidanceMssageService, PrjctUserMapngService prjctUserMapngService) {
    this.repo = repo;
    this.scrinGroupService = scrinGroupService;
    this.scrinService = scrinService;
    this.compnService = compnService;
    this.menuService = menuService;
    this.trgetSysService = trgetSysService;
    this.prjctTrgetSysMapngService = prjctTrgetSysMapngService;
    this.prjctCmmnCodeService = prjctCmmnCodeService;
    this.guidanceMssageService = guidanceMssageService;
    this.prjctUserMapngService = prjctUserMapngService;

  }

  Prjct toEntity(PrjctDto dto) {
    Prjct e = Prjct.builder().prjctNm(dto.getPrjctNm()).prjctCn(dto.getPrjctCn()).userId(dto.getUserId())
        .updtDt(new Date()).build();

    if (null == dto.getPrjctId() || 0 == dto.getPrjctId().length()) {
      e.setPrjctId(Util.getShortUuid());
      e.setRegistDt(new Date());
    } else {
      e.setPrjctId(dto.getPrjctId());
      e.setRegistDt(dto.getRegistDt());
    }

    return e;
  }

  PrjctDto toDto(Prjct e) {
    return PrjctDto.builder().registDt(e.getRegistDt()).prjctId(e.getPrjctId()).prjctNm(e.getPrjctNm())
        .prjctCn(e.getPrjctCn()).userId(e.getUserId()).updtDt(e.getUpdtDt()).build();
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
          // delete ????????????
          compnService.delete(compnDto.getCompnId());
        });

        // delete ??????
        scrinService.delete(scrinDto.getScrinId());
      });

      // delete ????????????
      scrinGroupService.deleteById(scrinGroupDto.getScrinGroupId());
    });

    // delete ??????
    menuService.deleteByPrjctId(prjctId);

    // delete ????????????
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
  public String copy(String oldPrjctId)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    class InnerClass {
      /**
       * ???????????? ??????
       */
      String copyPrjct(String oldPrjctId) {
        // 1
        PrjctDto dto = findById(oldPrjctId);
        // 2
        dto.setPrjctId(null);
        dto.setPrjctNm(dto.getPrjctNm() + "(?????????)");
        // 3
        return regist(dto);

      }

      /**
       * ????????????-????????? ?????? ??????
       * 
       * @param oldPrjctId
       * @param newPrjctId
       */
      void copyPrjctUserMapng(String oldPrjctId, String newPrjctId) {
        // 1. get old list
        List<PrjctUserMapngDto> dtos = prjctUserMapngService.findAllByPrjctId(oldPrjctId);

        dtos.forEach(x -> {
          // 2. change value
          x.setPrjctId(newPrjctId);
          x.setPrjctUserMapngId(null);
          // 3. new regist
          prjctUserMapngService.regist(x);
        });
      }

      /**
       * ????????????-??????????????? ?????? ??????
       * 
       * @param oldPrjctId
       * @param newPrjctId
       * @param newTrgetSysId
       */
      void copyPrjctTrgetSysMapng(String oldPrjctId, String newPrjctId, String newTrgetSysId) {
        // 1. get old list
        PrjctTrgetSysMapngDto dto = prjctTrgetSysMapngService.findByPrjctId(oldPrjctId);
        if (null == dto) {
          return;
        }

        // 2. change value
        dto.setPrjctTrgetSysMapngId(null);
        dto.setPrjctId(newPrjctId);
        dto.setTrgetSysId(newTrgetSysId);
        // 3. new regist
        prjctTrgetSysMapngService.regist(dto);
      }

      /**
       * ?????? ????????? ??????
       * 
       * @param oldPrjctId
       * @return
       * @throws InvalidKeyException
       * @throws UnsupportedEncodingException
       * @throws NoSuchAlgorithmException
       * @throws NoSuchPaddingException
       * @throws InvalidAlgorithmParameterException
       * @throws IllegalBlockSizeException
       * @throws BadPaddingException
       */
      String copyTrgetSys(String oldPrjctId)
          throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
          InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        List<TrgetSysDto> dtos = trgetSysService.findAllByPrjctId(oldPrjctId);

        String trgetSysId = null;

        for (TrgetSysDto dto : dtos) {
          // 2
          dto.setTrgetSysId(null);
          // 3 new regist
          trgetSysId = trgetSysService.regist(dto);

        }

        return trgetSysId;
      }

      /**
       * ?????? ????????? ??????
       * 
       * @param oldPrjctId
       * @param newPrjctId
       */
      void copyGuidanceMessage(String oldPrjctId, String newPrjctId) {
        // 1
        List<GuidanceMssageDto> dtos = guidanceMssageService.findAllByPrjctId(oldPrjctId);

        for (GuidanceMssageDto dto : dtos) {
          // 2
          dto.setGuidanceMssageId(null);
          dto.setPrjctId(newPrjctId);
          // 3
          guidanceMssageService.regist(dto);
        }
      }

      /**
       * ???????????? ?????? ?????? ??????
       * 
       * @param oldPrjctId
       * @param newPrjctId
       */
      void copyPrjctCmmnCode(String oldPrjctId, String newPrjctId) {
        // 1
        List<PrjctCmmnCodeDto> dtos = prjctCmmnCodeService.findAllByPrjctId(oldPrjctId);

        for (PrjctCmmnCodeDto dto : dtos) {
          // 2
          dto.setPrjctCmmnCodeId(null);
          dto.setPrjctId(newPrjctId);
          // 3
          prjctCmmnCodeService.regist(dto);
        }
      }

      /**
       * ?????? ??????
       * 
       * @param oldPrjctId
       * @param newPrjctId
       */
      void copyMenu(String oldPrjctId, String newPrjctId) {

        List<String> oldMenus = new ArrayList<>();
        List<String> newMenus = new ArrayList<>();

        // 1
        List<MenuDto> dtos = menuService.findAllByPrjctId(oldPrjctId);

        for (MenuDto dto : dtos) {
          oldMenus.add(dto.getMenuId());

          // 2
          dto.setMenuId(null);
          dto.setPrjctId(newPrjctId);
          // 3
          String newMenuId = menuService.regist(dto);
          newMenus.add(newMenuId);
        }

        // ????????? ????????? ?????? ??????
        dtos = menuService.findAllByPrjctId(newPrjctId);
        for (MenuDto dto : dtos) {
          if ("-".equals(dto.getPrntsMenuId())) {
            continue;
          }

          // ?????? ??????????????? ??????
          String newPrntsMenuId = getNewMenuId(dto.getPrntsMenuId(), oldMenus, newMenus);
          dto.setPrntsMenuId(newPrntsMenuId);
          // ????????????
          menuService.updt(dto);
        }

        // ?????? ???????????? ???????????? ??????????????? ????????????
        List<ScrinDto> scrinDtos = scrinService.findAllByPrjctId(newPrjctId);
        for (ScrinDto dto : scrinDtos) {
          log.debug("old {}", dto);
          String newMenuId = getNewMenuId(dto.getMenuId(), oldMenus, newMenus);
          dto.setMenuId(newMenuId);
          //
          scrinService.updt(dto);
          log.debug("new {}", dto);
        }
      }

      String getNewMenuId(String oldMenuId, List<String> oldMenus, List<String> newMenus) {
        for (int i = 0; i < oldMenus.size(); i++) {
          if (oldMenuId.equals(oldMenus.get(i))) {
            return newMenus.get(i);
          }
        }

        return null;
      }

      /**
       * ??????&???????????? ??????
       * 
       * @param oldPrjctId
       * @param newPrjctId
       */
      void copyScrinAndCompn(String oldPrjctId, String newPrjctId) {
        // 1
        List<ScrinDto> scrinDtos = scrinService.findAllByPrjctId(oldPrjctId);

        for (ScrinDto dto : scrinDtos) {
          // 1
          dto.setCompnDtos(compnService.findAllByScrinId(dto.getScrinId()));
        }

        // ??????
        for (ScrinDto scrinDto : scrinDtos) {
          // 2
          scrinDto.setScrinId(null);
          scrinDto.setPrjctId(newPrjctId);
          // 3
          String newScrinId = scrinService.regist(scrinDto);

          // ????????????
          for (CompnDto compnDto : scrinDto.getCompnDtos()) {
            // 2
            compnDto.setCompnId(null);
            compnDto.setScrinId(newScrinId);
            // 3
            compnService.regist(compnDto);
          }
        }
      }
    }
    ;

    //
    InnerClass ic = new InnerClass();

    // ???????????? ??????
    String newPrjctId = ic.copyPrjct(oldPrjctId);
    log.debug("newPrjctId: {}", newPrjctId);
    // ??????&???????????? ??????
    ic.copyScrinAndCompn(oldPrjctId, newPrjctId);
    // ?????? ??????
    ic.copyMenu(oldPrjctId, newPrjctId);
    // ???????????? ????????????
    ic.copyPrjctCmmnCode(oldPrjctId, newPrjctId);
    // ?????? ?????????
    ic.copyGuidanceMessage(oldPrjctId, newPrjctId);
    // ???????????????
    String newTrgetSysId = ic.copyTrgetSys(oldPrjctId);
    // ????????????-??????????????? ??????
    ic.copyPrjctTrgetSysMapng(oldPrjctId, newPrjctId, newTrgetSysId);
    // ????????????-????????? ??????
    ic.copyPrjctUserMapng(oldPrjctId, newPrjctId);

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
