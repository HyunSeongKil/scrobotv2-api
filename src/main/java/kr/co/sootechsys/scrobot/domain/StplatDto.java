package kr.co.sootechsys.scrobot.domain;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "약관")
public class StplatDto {
  private Long stplatId;

  private String stplatNm;

  private String stplatCn;

  private String registerId;

  private String registerNm;

  private Date registDt;
}
