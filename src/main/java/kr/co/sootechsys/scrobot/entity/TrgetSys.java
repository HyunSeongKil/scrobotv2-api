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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trget_sys")
public class TrgetSys {

  @Id
  @Column(name = "trget_sys_id")
  private String trgetSysId;

  @Column(name = "trget_sys_nm")
  private String trgetSysNm;

  @Column(name = "db_ty_nm")
  private String dbTyNm;

  @Column(name = "db_driver_nm")
  private String dbDriverNm;

  @Column(name = "db_url_nm")
  private String dbUrlNm;

  @Column(name = "db_user_nm")
  private String dbUserNm;

  @Column(name = "db_password_nm")
  private String dbPasswordNm;

  @Column(name = "db_nm")
  private String dbNm;

}
