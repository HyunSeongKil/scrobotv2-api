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
@ApiModel(value = "프로젝트-안내메시지 매핑")
public class PrjctGuidanceMssageMapngDto {
  @ApiModelProperty(value = "pk")
  private Long prjctGuidanceMssageMapngId;

  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  @ApiModelProperty(value = "안내 메시지 아이디")
  private Long guidanceMssageId;
}
