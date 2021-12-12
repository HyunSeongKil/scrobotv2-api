package kr.co.sootechsys.scrobot.domain;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
@Api(value = "화면 DTO")
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
   * 프로젝트 아이디
   */
  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  /**
   * 화면 구분 코드
   */
  private String scrinSeCode;

  /**
   * 화면 그룹 아이디
   */
  private String scrinGroupId;

  /**
   * (기준 데이터용)메뉴 아이디
   */
  private String menuId;

  @ApiModelProperty(value = "기준 데이터 명")
  private String stdrDataNm;

  /**
   * 등록 일시
   */
  private Date registDt;

  /**
   * 화면 그룹 인스턴스
   */
  // private ScrinGroupDto scrinGroup;

  private List<CompnDto> compnDtos;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
