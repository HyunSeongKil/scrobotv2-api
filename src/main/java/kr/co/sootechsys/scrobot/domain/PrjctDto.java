package kr.co.sootechsys.scrobot.domain;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import io.swagger.annotations.Api;
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
@Api(value = "프로젝트 DTO")
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
   * 프로젝트 내용
   */
  private String prjctCn;

  /**
   * 사용자 명
   */
  private String userId;


  /**
   * 등록 일시
   */
  private Date registDt;

  /**
   * 수정 일시
   */
  private Date updtDt;

  private List<ScrinGroupDto> scrinGroupDtos;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
