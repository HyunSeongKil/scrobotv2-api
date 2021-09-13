package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Entity
@Table(name = "menu2")
public class Menu {

  @Id
  @Column(name = "menu_id")
  private String menuId;

  @Column(name = "menu_nm")
  private String menuNm;

  @Column(name = "prnts_menu_id")
  private String prntsMenuId;

  @Column(name = "url_nm")
  private String urlNm;

  @Column(name = "scrin_id")
  private String scrinId;

  @Column(name = "menu_ordr_value")
  private Integer menuOrdrValue;

  @Column(name = "prjct_id")
  private String prjctId;

  @ManyToOne(targetEntity = Prjct.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "prjct_id", insertable = false, updatable = false)
  private Prjct prjct;
}
