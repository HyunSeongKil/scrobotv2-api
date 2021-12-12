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
@ApiModel(value = "안내 메시지")
public class GuidanceMssageDto {
  @ApiModelProperty(value = "pk")
  private Long guidanceMssageId;

  @ApiModelProperty(value = "안내 메시지 명")
  private String guidanceMssageNm;

  @ApiModelProperty(value = "안내 메시지 내용")
  private String guidanceMssageCn;

  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  @ApiModelProperty(value = "등록자 아이디")
  private String registerId;

  @ApiModelProperty(value = "등록자 명")
  private String registerNm;

  @ApiModelProperty(value = "등록 일시")
  private Date registDt;
}
