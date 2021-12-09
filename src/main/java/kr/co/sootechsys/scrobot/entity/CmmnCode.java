package kr.co.sootechsys.scrobot.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공통 코드
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cmmn_code")
@ApiModel(value = "공통 코드 엔티티")
public class CmmnCode {

  /**
   * 공통 코드 아이디
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cmmn_code_id")
  @ApiModelProperty(value = "공통 코드 아이디")
  private Long cmmnCodeId;

  /**
   * 공통 코드
   */
  @Column(name = "cmmn_code")
  @ApiModelProperty(value = "공통 코드")
  private String cmmnCode;

  /**
   * 공통 코드 명
   */
  @Column(name = "cmmn_code_nm")
  @ApiModelProperty(value = "공통 코드 명")
  private String cmmnCodeNm;

  /**
   * 부모 공통 코드
   */
  @Column(name = "prnts_cmmn_code")
  @ApiModelProperty(value = "부모 공통 코드")
  private String prntsCmmnCode;

  /**
   * 사용 여부
   */
  @Column(name = "use_at")
  @ApiModelProperty(value = "사용 여부")
  private String useAt;

  /**
   * 설명
   */
  @Column(name = "cmmn_code_cn")
  @ApiModelProperty(value = "공통 코드 설명")
  private String cmmnCodeCn;

  /**
   * 등록 일시
   */
  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;

  /**
   * 등록자 아이디
   */
  @Column(name = "register_id")
  @ApiModelProperty(value = "등록자 아이디")
  private String registerId;

  /**
   * 등록자 명
   */
  @Column(name = "register_nm")
  @ApiModelProperty(value = "등록자 명")
  private String registerNm;

}
