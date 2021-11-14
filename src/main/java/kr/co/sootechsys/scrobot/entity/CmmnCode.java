package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * 공통 코드
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cmmn_code")
@Api(description = "공통 코드")
public class CmmnCode {

  /**
   * 공통 코드 아이디
   */
  @Id
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

}
