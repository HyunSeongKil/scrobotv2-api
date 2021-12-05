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
@ApiModel(value = "게시판")
public class BbsDto {
  @ApiModelProperty(value = "게시판 아이디")
  private Long bbsId;

  @ApiModelProperty(value = "게시판 구분 코드")
  private String bbsSeCd;

  @ApiModelProperty(value = "게시판 제목 명")
  private String bbsSjNm;

  @ApiModelProperty(value = "게시판 내용")
  private String bbsCn;

  @ApiModelProperty(value = "첨부파일 그룹 아이디")
  private Long atchmnflGroupId;

  @ApiModelProperty(value = "조회 횟수")
  private Long inqireCo;

  @ApiModelProperty(value = "질문 구분 코드")
  private String qaaSeCd;

  @ApiModelProperty(value = "고정 여부")
  private String fixingAt;

  @ApiModelProperty(value = "(FAQ)문의 유형 코드")
  private String inqryTyCd;

  private String registerId;

  private String registerNm;

  private java.util.Date registDt;

}
