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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "domain")
@ApiModel(value = "도메인")
public class Domain {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "domain_id")
  @ApiModelProperty(value = "pk")
  private Long domainId;

  @Column(name = "domain_nm")
  @ApiModelProperty(value = "도메인 명")
  private String domainNm;

  @Column(name = "domain_cn")
  @ApiModelProperty(value = "도메인 내용")
  private String domainCn;

  @Column(name = "domain_group_cd")
  @ApiModelProperty(value = "도메인 그룹 코드")
  private String domainGroupCd;

  @Column(name = "domain_cl_cd")
  @ApiModelProperty(value = "도메인 분류 코드")
  private String domainClCd;

  @Column(name = "data_ty_cd")
  @ApiModelProperty(value = "데이터 타입 코드")
  private String dataTyCd;

  @Column(name = "data_lt_value")
  @ApiModelProperty(value = "데이터 길이 값")
  private Integer dataLtValue;

  @Column(name = "std_at")
  @ApiModelProperty(value = "표준 여부")
  private String stdAt;

  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;
}
