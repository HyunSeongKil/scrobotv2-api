package kr.co.sootechsys.scrobot.domain;

import java.util.Date;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Api(value = "회원 DTO")
public class UserDto {
  private String userId;
  private String userNm;

  private String password;

  private String sttusCode;

  private String telno;

  private Date lastLoginDt;

  private Date registDt;
}
