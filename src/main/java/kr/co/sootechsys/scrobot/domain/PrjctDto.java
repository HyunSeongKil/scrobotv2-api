package kr.co.sootechsys.scrobot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 프로젝트
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrjctDto {
  /**
   * 프로젝트 아이디
   */
  private String prjctId;

  /**
   * 프로젝트 명
   */
  private String prjctNm;

  /**
   * 사용자 명
   */
  private String userId;
}
