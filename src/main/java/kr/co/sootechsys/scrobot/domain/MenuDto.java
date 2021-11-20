package kr.co.sootechsys.scrobot.domain;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 메뉴
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Api(value = " 메뉴 DTO")
public class MenuDto {
  /**
   * 메뉴 아이디
   */
  private String menuId;

  /**
   * 메뉴 명
   */
  private String menuNm;

  /**
   * 부모 메뉴 아이디
   */
  private String prntsMenuId;

  /**
   * url 명
   */
  private String urlNm;

  /**
   * 화면 아이디
   */
  private String scrinId;


  /**
   * 메뉴 순서 값
   */
  private Integer menuOrdrValue;

  /**
   * 프로젝트 아이디
   */
  private String prjctId;

  /**
   * 프로젝트 인스턴스
   */
  private PrjctDto prjct;
}
