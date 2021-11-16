package kr.co.sootechsys.scrobot.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtchmnflGroupDto {
  private Long atchmnflGroupId;

  private String prjctId;

  private Date registDt;

}
