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
public class ScrinDto {
  
  private String scrinId;
  private String scrinNm;
  private String scrinSeCode;
  private String scrinGroupId;
  private ScrinGroupDto scrinGroup;
}
