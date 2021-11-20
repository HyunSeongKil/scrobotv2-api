package kr.co.sootechsys.scrobot.entity;

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
 * 화면 그룹
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scrin_group")
@ApiModel(value = "화면 그룹 엔티티")
public class ScrinGroup {

  /**
   * 화면 그룹 아이디
   */
  @Id
  @Column(name = "scrin_group_id")
  @ApiModelProperty(value = "화면 그룹 아이디")
  private String scrinGroupId;

  /**
   * 화면 그룹 명
   */
  @Column(name = "scrin_group_nm")
  @ApiModelProperty(value = "화면 그룹 명")
  private String scrinGroupNm;

  /**
   * 프로젝트 아이디
   */
  @Column(name = "prjct_id")
  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  /**
   * 공백으로 구분된 영문 약어 명. 배포시 테이블명이 됨
   */
  @Column(name = "eng_abrv_nm")
  @ApiModelProperty(value = "영문 약어 명")
  private String engAbrvNm;

  /**
   * 프로젝트 인스턴스
   */
  @ManyToOne(targetEntity = Prjct.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "prjct_id", insertable = false, updatable = false)
  private Prjct prjct;
}
