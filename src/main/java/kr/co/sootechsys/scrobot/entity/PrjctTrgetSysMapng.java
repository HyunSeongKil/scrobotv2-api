package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 프로젝트-대상 시스템 매핑
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prjct_trget_sys_mapng")
@Api(description = "프로젝트-대상시스템 매핑")
public class PrjctTrgetSysMapng {

  /**
   * 프로젝트-대상 시스템 매핑 아이디
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "prjct_trget_sys_mapng_id")
  @ApiModelProperty(value = "프로젝트-대상시스템 매핑 아이디")
  private Long prjctTrgetSysMapngId;

  /**
   * 프로젝트 아이디
   */
  @Column(name = "prjct_id")
  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  /**
   * 대상 시스템 아이디
   */
  @Column(name = "trget_sys_id")
  @ApiModelProperty(value = "대상 시스템 아이디")
  private String trgetSysId;
}
