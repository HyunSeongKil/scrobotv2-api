package kr.co.sootechsys.scrobot.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 대상 시스템
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrgetSysDto {
  /**
   * 대상 시스템 아이디
   */
  private String trgetSysId;


  /**
   * 대상 시스템 명
   */
  private String trgetSysNm;

  /**
   * 디비 타입 명
   */
  private String dbTyNm;


  /**
   * 디비 드라이버 명
   */
  private String dbDriverNm;

  /**
   * 디비 url 명
   */
  private String dbUrlNm;

  /**
   * 디비 사용자 명
   */
  private String dbUserNm;

  /**
   * 디비 비밀번호 명
   */
  private String dbPasswordNm;

  /**
   * 디비 명
   */
  private String dbNm;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
