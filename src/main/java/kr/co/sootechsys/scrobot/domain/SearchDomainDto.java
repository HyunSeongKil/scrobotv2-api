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
@ApiModel(value = "도메인")
public class SearchDomainDto {

  @ApiModelProperty(value = "도메인 명")
  private String domainNm;

  @ApiModelProperty(value = "도메인 내용")
  private String domainCn;

  @ApiModelProperty(value = "도메인 그룹 코드")
  private String domainGroupCd;

  @ApiModelProperty(value = "도메인 분류 코드")
  private String domainClCd;

  @ApiModelProperty(value = "데이터 타입 코드")
  private String dataTyCd;

  @ApiModelProperty(value = "표준 여부")
  private String stdAt;

}
