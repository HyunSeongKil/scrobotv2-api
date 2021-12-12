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
@Table(name = "prjct_user_mapng")
@ApiModel(value = "프로젝트-사용자 매핑")
public class PrjctUserMapng {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "prjct_user_mapng_id")
  @ApiModelProperty(value = "pk")
  private Long prjctUserMapngId;

  @Column(name = "prjct_id")
  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  @Column(name = "user_id")
  @ApiModelProperty(value = "사용자 아이디")
  private String userId;

  @Column(name = "mngr_at")
  @ApiModelProperty(value = "관리자 여부")
  private String mngrAt;

  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;
}
