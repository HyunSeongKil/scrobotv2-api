package kr.co.sootechsys.scrobot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
 * 화면
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scrin")
@ApiModel(value = "화면 엔티티")
public class Scrin {

  /**
   * 화면 아이디
   */
  @Id
  @Column(name = "scrin_id")
  @ApiModelProperty(value = "화면 아이디")
  private String scrinId;

  /**
   * 화면 명
   */
  @Column(name = "scrin_nm")
  @ApiModelProperty(value = "화면 명")
  private String scrinNm;

  /**
   * 프로젝트 아이디
   */
  @Column(name = "prjct_id")
  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  /**
   * 화면 그룹 아이디
   */
  @Column(name = "scrin_group_id")
  @ApiModelProperty(value = "화면 그룹 아이디")
  private String scrinGroupId;

  /**
   * 화면 구분 코드
   */
  @Column(name = "scrin_se_code")
  @ApiModelProperty(value = "화면 구분 코드")
  private String scrinSeCode;

  /**
   * (기준 데이터용)메뉴 아이디
   */
  @Column(name = "menu_id")
  @ApiModelProperty(value = "(기준 데이터용)메뉴 아이디")
  private String menuId;

  /**
   * 기준 데이터 명
   */
  @Column(name = "stdr_data_nm")
  @ApiModelProperty(value = "기준 데이터 명")
  private String stdrDataNm;

  /**
   * 등록 일시
   */
  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;

  /**
   * 화면 그룹 인스턴스
   */
  // @ManyToOne(targetEntity = ScrinGroup.class, fetch = FetchType.LAZY)
  // @JoinColumn(name = "scrin_group_id", insertable = false, updatable = false)
  // private ScrinGroup scrinGroup;
}
