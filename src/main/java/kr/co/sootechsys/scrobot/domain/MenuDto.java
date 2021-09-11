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
public class MenuDto {
  private String menuId;

  private String menuNm;

  private String prntsMenuId;

  private String urlNm;

  private Integer menuOrdrValue;

  private String prjctId;

  private PrjctDto prjct;
}
