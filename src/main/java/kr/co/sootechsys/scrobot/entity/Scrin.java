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
 * 화면
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scrin")
public class Scrin {

  /**
   * 화면 아이디
   */
  @Id
  @Column(name = "scrin_id")
  private String scrinId;

  /**
   * 화면 명
   */
  @Column(name = "scrin_nm")
  private String scrinNm;

  /**
   * 화면 그룹 아이디
   */
  @Column(name = "scrin_group_id")
  private String scrinGroupId;

  /**
   * 화면 구분 코드
   */
  @Column(name = "scrin_se_code")
  private String scrinSeCode;

  /**
   * 화면 그룹 인스턴스
   */
  @ManyToOne(targetEntity = ScrinGroup.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "scrin_group_id", insertable = false, updatable = false)
  private ScrinGroup scrinGroup;
}
