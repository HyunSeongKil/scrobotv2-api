package kr.co.sootechsys.scrobot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 화면
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrinDto {

  /**
   * 화면 아이디
   */
  private String scrinId;

  /**
   * 화면 명
   */
  private String scrinNm;

  /**
   * 화면 구분 코드
   */
  private String scrinSeCode;

  /**
   * 화면 그룹 아이디
   */
  private String scrinGroupId;

  /**
   * 화면 그룹 인스턴스
   */
  private ScrinGroupDto scrinGroup;
}
