package kr.co.sootechsys.scrobot.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
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
public class TrgetSysDto {

  private String trgetSysId;

  private String trgetSysNm;

  private String dbTyNm;

  private String dbDriverNm;

  private String dbUrlNm;

  private String dbUserNm;

  private String dbPasswordNm;

  private String dbNm;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
