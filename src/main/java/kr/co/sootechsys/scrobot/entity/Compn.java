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
@Table(name = "compn")
public class Compn {

  @Id
  @Column(name = "compn_id")
  private String compnId;

  @Column(name = "compn_nm")
  private String compnNm;

  @Column(name = "eng_abrv_nm")
  private String engAbrvNm;

  @Column(name = "hngl_abrv_nm")
  private String hnglAbrvNm;

  @Column(name = "compn_cn")
  private String compnCn;

  @Column(name = "scrin_id")
  private String scrinId;

  @ManyToOne(targetEntity = Scrin.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "scrin_id", insertable = false, updatable = false)
  private Scrin scrin;
}
