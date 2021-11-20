package kr.co.sootechsys.scrobot.domain;

import java.util.Date;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Api(value = "첨부파일 DTO")
public class AtchmnflDto {
  private Long atchmnflId;

  private Long atchmnflGroupId;

  /**
   * 첨부파일 저장 경로 값
   */
  private String atchmnflStorgPathValue;

  /**
   * 저장 파일 명
   */
  private String storgFileNm;

  /**
   * 원본 파일 명
   */
  private String originalFileNm;

  /**
   * 첨부파일 크기 값
   */
  private Long atchmnflFileszValue;

  /**
   * 첨부파일 확장자
   */
  private String atchmnflEtsion;

  /**
   * 인코딩구분코드
   */
  private String encodingDstnctCd;

  private Date registDt;

}
