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


/**
 * 메뉴
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu2")
public class Menu {

  /**
   * 메뉴 아이디
   */
  @Id
  @Column(name = "menu_id")
  private String menuId;


  /**
   * 메뉴 명
   */
  @Column(name = "menu_nm")
  private String menuNm;

  /**
   * 부모 메뉴 아이디
   */
  @Column(name = "prnts_menu_id")
  private String prntsMenuId;

  /**
   * url 명
   */
  @Column(name = "url_nm")
  private String urlNm;

  /**
   * 화면 아이디
   */
  @Column(name = "scrin_id")
  private String scrinId;

  /**
   * 메뉴 순서 값
   */
  @Column(name = "menu_ordr_value")
  private Integer menuOrdrValue;

  /**
   * 프로젝트 아이디
   */
  @Column(name = "prjct_id")
  private String prjctId;

  /**
   * 프로젝트 인스턴스
   */
  @ManyToOne(targetEntity = Prjct.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "prjct_id", insertable = false, updatable = false)
  private Prjct prjct;
}
