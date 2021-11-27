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
@Table(name = "guidance_mssage")
@ApiModel(value = "안내 메시지")
public class GuidanceMssage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "guidance_mssage_id")
  @ApiModelProperty(value = "pk")
  private Long guidanceMssageId;

  @Column(name = "guidance_mssage_nm")
  @ApiModelProperty(value = "안내 메시지 명")
  private String guidanceMssageNm;

  @Column(name = "guidance_mssage_cn")
  @ApiModelProperty(value = "안내 메시지 내용")
  private String guidanceMssageCn;

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
