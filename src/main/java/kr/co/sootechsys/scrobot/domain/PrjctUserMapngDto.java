package kr.co.sootechsys.scrobot.domain;

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
@ApiModel(value = "프로젝트-사용자 매핑")
public class PrjctUserMapngDto {
  @ApiModelProperty(value = "pk")
  private Long prjctUserMapngId;

  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  @ApiModelProperty(value = "사용자 아이디")
  private String userId;

  @ApiModelProperty(value = "관리자 여부")
  private String mngrAt;
}
