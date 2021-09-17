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
public class PrjctTrgetSysMapngDto {

  private Long prjctTrgetSysMapngId;

  private String prjctId;

  private String trgetSysId;
}
