package kr.co.sootechsys.scrobot.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import io.swagger.annotations.Api;
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
@Api(value = "화면그룹 DTO")
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

  private List<ScrinDto> scrinDtos;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
