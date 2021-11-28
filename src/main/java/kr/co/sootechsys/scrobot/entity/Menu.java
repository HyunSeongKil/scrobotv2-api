package kr.co.sootechsys.scrobot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Entity
@Table(name = "menu2")
@ApiModel(value = "메뉴 엔티티")
public class Menu {

  /**
   * 메뉴 아이디
   */
  @Id
  @Column(name = "menu_id")
  @ApiModelProperty(value = "메뉴 아이디")
  private String menuId;

  /**
   * 메뉴 명
   */
  @Column(name = "menu_nm")
  @ApiModelProperty(value = "메뉴 명")
  private String menuNm;

  /**
   * 부모 메뉴 아이디
   */
  @Column(name = "prnts_menu_id")
  @ApiModelProperty(value = "부모 메뉴 아이디")
  private String prntsMenuId;

  /**
   * url 명
   */
  @Column(name = "url_nm")
  @ApiModelProperty(value = "url 명")
  private String urlNm;

  /**
   * 화면 아이디
   */
  @Column(name = "scrin_id")
  @ApiModelProperty(value = "화면 아이디")
  private String scrinId;

  /**
   * 메뉴 순서 값
   */
  @Column(name = "menu_ordr_value", nullable = false)
  @ApiModelProperty(value = "메뉴 순서 ")
  private Integer menuOrdrValue;

  /**
   * 프로젝트 아이디
   */
  @Column(name = "prjct_id")
  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  /**
   * 등록 일시
   */
  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;

  /**
   * 프로젝트 인스턴스
   */
  @ManyToOne(targetEntity = Prjct.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "prjct_id", insertable = false, updatable = false)
  private Prjct prjct;
}
