package kr.co.sootechsys.scrobot.domain;

import java.util.Date;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공통 코드
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Api(value = "공통코드 DTO")
public class CmmnCodeDto {

  /**
   * 공통 코드 아이디
   */
  private Long cmmnCodeId;

  /**
   * 공통 코드
   */
  private String cmmnCode;

  /**
   * 공통 코드 명
   */
  private String cmmnCodeNm;

  /**
   * 부모 공통 코드
   */
  private String prntsCmmnCode;

  /**
   * 사용 여부
   */
  private String useAt;

  private String cmmnCodeCn;

  @ApiModelProperty(value = "등록 일시")
  private Date registDt;

  /**
   * 등록자 아이디
   */
  @ApiModelProperty(value = "등록자 아이디")
  private String registerId;

  /**
   * 등록자 명
   */
  @ApiModelProperty(value = "등록자 명")
  private String registerNm;
}
