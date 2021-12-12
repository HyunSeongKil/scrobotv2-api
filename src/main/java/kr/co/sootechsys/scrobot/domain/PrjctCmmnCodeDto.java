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

@ApiModel(value = "프로젝트 공통 코드")
public class PrjctCmmnCodeDto {
  @ApiModelProperty(value = "pk")
  private Long prjctCmmnCodeId;

  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  @ApiModelProperty(value = "공통 코드")
  private String cmmnCode;

  @ApiModelProperty(value = "공통 코드 명")
  private String cmmnCodeNm;

  @ApiModelProperty(value = "공통 코드 설명")
  private String cmmnCodeCn;

  @ApiModelProperty(value = "부모 공통 코드 ")
  private String prntsCmmnCode;

  @ApiModelProperty(value = "사용 여부")
  private String useAt;

  @ApiModelProperty(value = "등록자 아이디")
  private String registerId;

  @ApiModelProperty(value = "등록자 명")
  private String registerNm;

  @ApiModelProperty(value = "등록 일시")
  private Date registDt;
}
