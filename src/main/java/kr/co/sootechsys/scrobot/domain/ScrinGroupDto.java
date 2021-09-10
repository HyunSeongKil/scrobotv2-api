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
public class ScrinGroupDto {
  
  private String scrinGroupId;
  private String scrinGroupNm;
  private String prjctId;
  private PrjctDto prjctDto;
}
