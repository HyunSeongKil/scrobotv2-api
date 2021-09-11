package kr.co.sootechsys.scrobot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompnDto {

  private String compnId;
  private String compnNm;
  private String compnCn;
  private String hnglAbrvNm;
  private String engAbrvNm;
  private String scrinId;

  private ScrinDto scrin;

}
