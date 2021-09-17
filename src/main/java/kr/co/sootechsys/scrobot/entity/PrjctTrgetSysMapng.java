package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "prjct_trget_sys_mapng")
public class PrjctTrgetSysMapng {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "prjct_trget_sys_mapng_id")
  private Long prjctTrgetSysMapngId;

  @Column(name = "prjct_id")
  private String prjctId;

  @Column(name = "trget_sys_id")
  private String trgetSysId;
}
