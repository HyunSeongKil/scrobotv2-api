package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
public class TrgetSys {
  /**
   * 대상 시스템 아이디
   */
  @Id
  @Column(name = "trget_sys_id")
  private String trgetSysId;

  /**
   * 대상 시스템 명
   */
  @Column(name = "trget_sys_nm")
  private String trgetSysNm;

  /**
   * 디비 타입 명
   */
  @Column(name = "db_ty_nm")
  private String dbTyNm;

  /**
   * 디비 드라이버 명
   */
  @Column(name = "db_driver_nm")
  private String dbDriverNm;

  /**
   * 디비 url 명
   */
  @Column(name = "db_url_nm")
  private String dbUrlNm;

  /**
   * 디비 사용자 명
   */
  @Column(name = "db_user_nm")
  private String dbUserNm;

  /**
   * 디비 비밀번호 명
   */
  @Column(name = "db_password_nm")
  private String dbPasswordNm;

  /**
   * 디비 명
   */
  @Column(name = "db_nm")
  private String dbNm;

}
