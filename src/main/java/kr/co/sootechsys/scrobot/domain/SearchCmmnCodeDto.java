package kr.co.sootechsys.scrobot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCmmnCodeDto {
  private String cmmnCode;

  private String cmmnCodeNm;
}
