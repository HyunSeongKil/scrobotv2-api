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
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
@Api(description = "사용자")
public class User {

  @Id
  @Column(name = "user_id")
  @ApiModelProperty(value = "사용자 아이디")
  private String userId;

  @Column(name = "user_nm")
  @ApiModelProperty(value = "사용자 명")
  private String userNm;

  @Column(name = "password", length = 1000)
  @ApiModelProperty(value = "비밀번호")
  private String password;

  @Column(name = "sttus_code")
  @ApiModelProperty(value = "상태 코드")
  private String sttusCode;

  @Column(name = "telno")
  @ApiModelProperty(value = "전화번호")
  private String telno;

  @Column(name = "last_login_dt")
  @ApiModelProperty(value = "최근 로그인 일시")
  private Date lastLoginDt;

  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;
}
