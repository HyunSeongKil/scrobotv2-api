package kr.co.sootechsys.scrobot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 프로젝트
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prjct2")
@Api(description = "프로젝트")
public class Prjct {
  /**
   * 프로젝트 아이디
   */
  @Id
  @Column(name = "prjct_id")
  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  /**
   * 프로젝트 명
   */
  @Column(name = "prjct_nm")
  @ApiModelProperty(value = "프로젝트 명")
  private String prjctNm;

  /**
   * 프로젝트 내용
   */
  @Column(name = "prjct_cn", length = 4000)
  @ApiModelProperty(value = "프로젝트 내용")
  private String prjctCn;

  /**
   * 사용자 아이디
   */
  @Column(name = "user_id")
  @ApiModelProperty(value = "사용자 아이디")
  private String userId;

  /**
   * 생성 일시
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
}
