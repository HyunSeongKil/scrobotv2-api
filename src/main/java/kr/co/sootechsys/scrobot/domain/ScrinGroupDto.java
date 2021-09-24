package kr.co.sootechsys.scrobot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 화면 그룹
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrinGroupDto {
  /**
   * 화면 그룹 아이디
   */
  private String scrinGroupId;

  /**
   * 화면 그룹 명
   */
  private String scrinGroupNm;

  /**
   * 프로젝트 아이디
   */
  private String prjctId;

  /**
   * 영문 약어 명
   */
  private String engAbrvNm;

  /**
   * 프로젝트 인스턴스
   */
  private PrjctDto prjctDto;
}
