package kr.co.sootechsys.scrobot.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
  private String userId;
  private String userNm;

  private String password;

  private String sttusCode;

  private Date lastLoginDt;

  private Date registDt;
}
