package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * 대상 시스템
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trget_sys")
@ApiModel(value = "대상 시스템 엔티티")
public class TrgetSys {
  /**
   * 대상 시스템 아이디
   */
  @Id
  @Column(name = "trget_sys_id")
  @ApiModelProperty(value = "대상 시스템 아이디")
  private String trgetSysId;

  /**
   * 대상 시스템 명
   */
  @Column(name = "trget_sys_nm")
  @ApiModelProperty(value = "대상 시스템 명")
  private String trgetSysNm;

  /**
   * 디비 타입 명
   */
  @Column(name = "db_ty_nm")
  @ApiModelProperty(value = "디비 타입 명")
  private String dbTyNm;

  /**
   * 디비 드라이버 명
   */
  // @Column(name = "db_driver_nm")
  // private String dbDriverNm;

  /**
   * 디비 url 명
   */
  @Column(name = "db_url_nm")
  @ApiModelProperty(value = "디비 url 명")
  private String dbUrlNm;

  /**
   * 디비 사용자 명
   */
  @Column(name = "db_user_nm")
  @ApiModelProperty(value = "디비 사용자 명")
  private String dbUserNm;

  /**
   * 디비 비밀번호 명
   */
  @Column(name = "db_password_nm")
  @ApiModelProperty(value = "(암호화된)디비 비밀번호 명")
  private String dbPasswordNm;

  /**
   * 디비 명
   */
  @Column(name = "db_nm")
  @ApiModelProperty(value = "디비 명")
  private String dbNm;

  /**
   * 디비 호스트 명
   */
  @Column(name = "db_host_nm")
  @ApiModelProperty(value = "디비 호스트 명")
  private String dbHostNm;

  /**
   * 디비 포트 값
   */
  @Column(name = "db_port_value")
  @ApiModelProperty(value = "디비 포트 값")
  private Integer dbPortValue;

}
