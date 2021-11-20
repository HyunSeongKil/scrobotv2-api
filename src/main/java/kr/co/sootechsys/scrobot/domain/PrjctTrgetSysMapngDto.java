package kr.co.sootechsys.scrobot.domain;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 프로젝트 - 대상 시스템 매핑
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Api(value = "프로젝트-대상 시스템 매핑 DTO")
public class PrjctTrgetSysMapngDto {

  /**
   * 프로젝트-대상 시스템 매핑 아이디
   */
  private Long prjctTrgetSysMapngId;

  /**
   * 프로젝트 아이디
   */
  private String prjctId;

  /**
   * 대상 시스템 아이디
   */
  private String trgetSysId;
}
