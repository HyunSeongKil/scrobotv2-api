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
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 콤포넌트
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compn")
@Api(description = "콤포넌트")
public class Compn {

  /**
   * 콤포넌트 아이디
   */
  @Id
  @Column(name = "compn_id")
  @ApiModelProperty(value = "콤포넌트 아이디")
  private String compnId;

  /**
   * 콤포넌트 명
   */
  @Column(name = "compn_nm")
  @ApiModelProperty(value = "")
  private String compnNm;

  /**
   * 영문 약어 명
   */
  @Column(name = "eng_abrv_nm")
  @ApiModelProperty(value = "영문 약어 명")
  private String engAbrvNm;

  /**
   * 한글 약어 명
   */
  @Column(name = "hngl_abrv_nm")
  @ApiModelProperty(value = "한글 약어 명")
  private String hnglAbrvNm;

  /**
   * 콤포넌트 내용
   */
  @Column(name = "compn_cn", length = 4000)
  @ApiModelProperty(value = "콤포넌트 내용")
  private String compnCn;

  /**
   * 콤포넌트 구분 코드
   */
  @Column(name = "compn_se_code")
  @ApiModelProperty(value = "콤포넌트 구분 코드")
  private String compnSeCode;

  /**
   * 화면 아이디
   */
  @Column(name = "scrin_id")
  @ApiModelProperty(value = "화면 아이디")
  private String scrinId;

  /**
   * 순서 값
   */
  @Column(name = "ordr_value")
  @ApiModelProperty(value = "순서 값")
  private Integer ordrValue;

  /**
   * 등록 일시
   */
  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;

  /**
   * 수정 일시
   */
  @Column(name = "updt_dt")
  @ApiModelProperty(value = "수정 일시")
  private Date updtDt;

  /**
   * 화면 인스턴스
   */
  @ManyToOne(targetEntity = Scrin.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "scrin_id", insertable = false, updatable = false)
  private Scrin scrin;
}
