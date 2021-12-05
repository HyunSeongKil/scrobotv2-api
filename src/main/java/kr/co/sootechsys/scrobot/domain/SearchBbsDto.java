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
@ApiModel(value = "게시판 검색 dto")
public class SearchBbsDto {

  @ApiModelProperty(value = "게시판 제목 명")
  private String bbsSjNm;

  @ApiModelProperty(value = "게시판 내용")
  @Builder.Default
  private String bbsCn = "";

  @Builder.Default
  private String bbsSeCd = "";

  @Builder.Default
  @ApiModelProperty(value = "질의 유형")
  private String inqryTyCd = "";

  @Builder.Default
  @ApiModelProperty(value = "시작 일자")
  private String fromDe = "";

  @Builder.Default
  @ApiModelProperty(value = "종료 일자")
  private String toDe = "";

  // private String registerId;

  // private String registerNm;

}
