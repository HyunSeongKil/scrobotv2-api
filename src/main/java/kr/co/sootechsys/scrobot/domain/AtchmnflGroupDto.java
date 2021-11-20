package kr.co.sootechsys.scrobot.domain;

import java.util.Date;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Api(value = "첨부파일 그룹 DTO")
public class AtchmnflGroupDto {
  private Long atchmnflGroupId;

  private String prjctId;

  private Date registDt;

}
