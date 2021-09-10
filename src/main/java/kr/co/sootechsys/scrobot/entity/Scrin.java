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

  @Id
  @Column(name = "scrin_id")
  private String scrinId;
  
  @Column(name = "scrin_nm")
  private String scrinNm;

  @Column(name = "scrin_group_id")
  private String scrinGroupId;

  @Column(name = "scrin_se_code")
  private String scrinSeCode;


  @ManyToOne(targetEntity = ScrinGroup.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "scrin_group_id", insertable = false, updatable = false)
  private ScrinGroup scrinGroup;
}
