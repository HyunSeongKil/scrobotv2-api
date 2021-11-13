package kr.co.sootechsys.scrobot.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

  @Id
  @Column(name = "user_id")
  private String userId;

  @Column(name = "user_nm")
  private String userNm;

  @Column(name = "password", length = 1000)
  private String password;

  @Column(name = "sttus_code")
  private String sttusCode;

  @Column(name = "telno")
  private String telno;

  @Column(name = "last_login_dt")
  private Date lastLoginDt;

  @Column(name = "regist_dt")
  private Date registDt;
}
