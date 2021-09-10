package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 화면 그룹
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scrin_group")
public class ScrinGroup {

  @Id
  @Column(name = "scrin_group_id")
  private String scrinGroupId;
  
  @Column(name = "scrin_group_nm")
  private String scrinGroupNm;

  @Column(name = "prjct_id")
  private String prjctId;

  /**
   * 공백으로 구분된 영문 약어 명. 배포시 테이블명이 됨
   */
  @Column(name = "eng_abrv_nm")
  private String engAbrvNm;

  @ManyToOne(targetEntity = Prjct.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "prjct_id", insertable = false, updatable = false)
  private Prjct prjct;
}
