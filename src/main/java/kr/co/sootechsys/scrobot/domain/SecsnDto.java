package kr.co.sootechsys.scrobot.domain;

import java.util.Date;

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
@ApiModel(value = "탈퇴")
public class SecsnDto {
  private Long secsnId;

  private String userId;

  private String userNm;

  private String telno;

  private String secsnReasonCn;

  @ApiModelProperty(value = "개선 내용")
  private String imprvmCn;

  @ApiModelProperty(value = "가입 일시")
  private Date joinDt;

  @ApiModelProperty(value = "탈퇴 일시")
  private Date secsnDt;

}
