package kr.co.sootechsys.scrobot.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.TrgetSysDto;
import kr.co.sootechsys.scrobot.entity.TrgetSys;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.TrgetSysRepository;
import kr.co.sootechsys.scrobot.service.TrgetSysService;
import lombok.extern.slf4j.Slf4j;

@Service
@Api(value = "대상시스템 서비스")
@Slf4j
public class TrgetSysServiceImpl implements TrgetSysService {
  @Value("${app.secret.key}")
  private String secretKey;

  private TrgetSysRepository repo;

  public TrgetSysServiceImpl(TrgetSysRepository repo) {
    this.repo = repo;
  }

  TrgetSys toEntity(TrgetSysDto dto) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException,
      NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    TrgetSys e = TrgetSys.builder().build();
    // e.setDbDriverNm(dto.getDbDriverNm());
    e.setDbPasswordNm(Util.encodeAes(secretKey, dto.getDbPasswordNm()));
    e.setDbUrlNm(dto.getDbUrlNm());
    e.setDbUserNm(dto.getDbUserNm());
    e.setDbTyNm(dto.getDbTyNm());
    e.setTrgetSysNm(dto.getTrgetSysNm());
    e.setDbNm(dto.getDbNm());
    e.setDbPortValue(dto.getDbPortValue());
    e.setDbHostNm(dto.getDbHostNm());

    if (null == dto.getTrgetSysId() || 0 == dto.getTrgetSysId().length()) {
      e.setTrgetSysId(Util.getShortUuid());
    } else {
      e.setTrgetSysId(dto.getTrgetSysId());
    }

    return e;
  }

  TrgetSysDto toDto(TrgetSys e) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    TrgetSysDto dto = TrgetSysDto.builder().build();
    // dto.setDbDriverNm(e.getDbDriverNm());
    dto.setDbPasswordNm(Util.decodeAes(secretKey, e.getDbPasswordNm()));
    dto.setDbTyNm(e.getDbTyNm());
    dto.setDbUrlNm(e.getDbUrlNm());
    dto.setDbUserNm(e.getDbUserNm());
    dto.setTrgetSysId(e.getTrgetSysId());
    dto.setTrgetSysNm(e.getTrgetSysNm());
    dto.setDbNm(e.getDbNm());
    dto.setDbPortValue(e.getDbPortValue());
    dto.setDbHostNm(e.getDbHostNm());

    return dto;
  }

  @Override
  public String regist(TrgetSysDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    return repo.save(toEntity(dto)).getTrgetSysId();
  }

  @Override
  public void updt(TrgetSysDto dto) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException,
      NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    if (null == findById(dto.getTrgetSysId())) {
      return;
    }

    TrgetSys e = toEntity(dto);
    e.setTrgetSysId(dto.getTrgetSysId());

    repo.save(e);
  }

  @Override
  public void delete(String trgetSysId) {
    repo.deleteById(trgetSysId);
  }

  @Override
  public TrgetSysDto findById(String trgetSysId) {
    Optional<TrgetSys> opt = repo.findById(trgetSysId);
    if (opt.isPresent()) {
      try {
        return toDto(opt.get());
      } catch (Exception e) {
        log.error("{}", e);
      }
    }

    //
    return null;
  }

  @Override
  public List<TrgetSysDto> findAll() {
    List<TrgetSysDto> dtos = new ArrayList<>();

    repo.findAll().forEach(e -> {
      try {
        dtos.add(toDto(e));
      } catch (Exception e1) {
        log.error("{}", e1);
      }
    });

    return dtos;
  }

  @Override
  public List<TrgetSysDto> findAllByPrjctId(String prjctId) {
    List<TrgetSysDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e -> {
      try {
        dtos.add(toDto(e));
      } catch (Exception e1) {
        log.error("{}", e1);
      }
    });

    return dtos;
  }

}
