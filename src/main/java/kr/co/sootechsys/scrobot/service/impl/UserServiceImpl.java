package kr.co.sootechsys.scrobot.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.jayway.jsonpath.Option;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchUserDto;
import kr.co.sootechsys.scrobot.domain.UserDto;
import kr.co.sootechsys.scrobot.entity.User;
import kr.co.sootechsys.scrobot.misc.Util;
import kr.co.sootechsys.scrobot.persistence.UserRepository;
import kr.co.sootechsys.scrobot.service.JwtService;
import kr.co.sootechsys.scrobot.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Api(value = "회원 서비스")
public class UserServiceImpl implements UserService {

  @Value("${app.secret.key}")
  private String secretKey;

  private UserRepository repo;
  private JwtService jwtService;

  public UserServiceImpl(UserRepository repo, JwtService jwtService) {
    this.repo = repo;
    this.jwtService = jwtService;
  }

  User toEntity(UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    User e = User.builder().password(Util.encodeAes(secretKey, dto.getPassword())).userId(dto.getUserId()).userNm(dto.getUserNm()).registDt(new Date()).telno(dto.getTelno()).build();

    return e;
  }

  UserDto toDto(User e)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    return UserDto.builder().userId(e.getUserId()).userNm(e.getUserNm()).password(e.getPassword()).registDt(e.getRegistDt()).lastLoginDt(e.getLastLoginDt()).sttusCode(e.getSttusCode())
        .telno(Util.decodeAes(secretKey, e.getTelno())).build();
  }

  @Override
  @Transactional
  public Map<String, Object> signin(UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    Optional<User> opt = repo.findById(dto.getUserId());
    if (opt.isEmpty()) {
      // 회원 없음
      return Map.of("e", "E-USER_ID");
    }

    UserDto mbDto = toDto(opt.get());
    String cipherText = Util.sha512(dto.getPassword());
    log.debug("{} {}", mbDto.getPassword(), cipherText);

    if (!mbDto.getPassword().equals(cipherText)) {
      // 비번 틀림
      return Map.of("e", "E-PASSWORD");
    }

    // TODO 상태 점검

    // 마지막 로그인 일시 저장
    User e = opt.get();
    e.setLastLoginDt(new Date());
    repo.save(e);

    // jwt 생성
    String jwt = jwtService.createToken(mbDto, 1000 * 60L * 10L); // 10분
    String refreshToken = jwtService.createToken(mbDto, 1000 * 60L * 60L * 24L); // 1day
    log.debug("{} {}", jwt, jwtService.getUserId(jwt));

    return Map.of("accessToken", jwt, "refreshToken", refreshToken);
  }

  @Override
  public void join(UserDto dto)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    repo.save(toEntity(dto));

  }

  @Override
  public UserDto findById(String userId)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    Optional<User> opt = repo.findById(userId);
    if (opt.isPresent()) {
      return toDto(opt.get());
    }

    return null;

  }

  @Override
  public String reIssueToken(String refreshToken)
      throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
    boolean b = jwtService.varifyToken(refreshToken);
    if (!b) {
      throw new RuntimeException("");
    }

    String userId = jwtService.getUserId(refreshToken);
    UserDto dto = findById(userId);
    String jwt = jwtService.createToken(dto, 1000 * 60L * 10L); // 10분

    return jwt;
  }

  @Override
  public List<UserDto> findAllByPrjctId(String prjctId) {
    List<UserDto> dtos = new ArrayList<>();

    repo.findAllByPrjctId(prjctId).forEach(e -> {
      try {
        dtos.add(toDto(e));
      } catch (Exception e1) {
        log.error("{}", e1);
      }
    });

    return dtos;
  }

  @Override
  public String findIdByUserNmAndTelno(String userNm, String telno)
      throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    String encTelno = Util.encodeAes(secretKey, telno);
    Optional<User> opt = repo.findByUserNmAndTelno(userNm, encTelno);

    if (opt.isPresent()) {
      return opt.get().getUserId();
    }

    return "";
  }

  @Override
  public PageableResult findAll(SearchUserDto searchDto, Pageable pageable) {
    Page<User> page = repo.findAll(new Specification<User>() {
      @Override
      public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // 명
        if (null != searchDto.getUserNm() && 0 < searchDto.getUserNm().length()) {
          predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("userNm"), "%" + searchDto.getUserNm() + "%")));
        }


        // TODO 다른조건

        query.orderBy(criteriaBuilder.desc(root.get("userNm")));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);

    List<UserDto> dtos = new ArrayList<>();
    page.getContent().forEach(e -> {
      try {
        dtos.add(toDto(e));
      } catch (Exception e1) {
        log.error("{}", e);
      }
    });

    return PageableResult.builder().data(dtos).number(page.getNumber()).numberOfElements(page.getNumberOfElements()).pageable(page.getPageable()).size(page.getSize()).sort(page.getSort())
        .totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).build();
  }

}
