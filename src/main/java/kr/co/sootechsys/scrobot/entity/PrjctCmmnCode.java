package kr.co.sootechsys.scrobot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prjct_cmmn_code")
@ApiModel(value = "프로젝트 공통 코드")
public class PrjctCmmnCode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "prjct_cmmn_code_id")
  @ApiModelProperty(value = "pk")
  private Long prjctCmmnCodeId;

  @Column(name = "prjct_id")
  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  @Column(name = "cmmn_code")
  @ApiModelProperty(value = "공통 코드")
  private String cmmnCode;

  @Column(name = "cmmn_code_nm")
  @ApiModelProperty(value = "공통 코드 명")
  private String cmmnCodeNm;

  @Column(name = "cmmn_code_cn")
  @ApiModelProperty(value = "공통 코드 설명")
  private String cmmnCodeCn;

  @Column(name = "prnts_cmmn_code")
  @ApiModelProperty(value = "부모 공통 코드 ")
  private String prntsCmmnCode;

  @Column(name = "use_at")
  @ApiModelProperty(value = "사용 여부")
  private String useAt;

  @Column(name = "register_id")
  @ApiModelProperty(value = "등록자 아이디")
  private String registerId;

  @Column(name = "register_nm")
  @ApiModelProperty(value = "등록자 명")
  private String registerNm;

  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;
}
