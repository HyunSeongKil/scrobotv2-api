package kr.co.sootechsys.scrobot.domain;

import java.util.Date;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 컴포넌트
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Api(value = "콤포넌트 DTO")
public class CompnDto {

  /**
   * 컴포넌트 아이디
   */
  private String compnId;

  /**
   * 컴포넌트 명
   */
  private String compnNm;

  /**
   * 컴포넌트 내용
   */
  private String compnCn;

  /**
   * 컴포넌트 구분 코드
   */
  private String compnSeCode;

  /**
   * 한글 약어 명
   */
  private String hnglAbrvNm;

  /**
   * 영문 약어 명
   */
  private String engAbrvNm;

  /**
   * 화면 아이디
   */
  private String scrinId;

  /**
   * 화면
   */
  private ScrinDto scrin;

  /**
   * 순서 값
   */
  private Integer ordrValue;

  private Date registDt;

  private Date updtDt;

}
