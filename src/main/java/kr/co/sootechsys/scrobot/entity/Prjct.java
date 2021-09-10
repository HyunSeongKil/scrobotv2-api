package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prjct")
public class Prjct {
  @Id
  @Column(name = "prjct_id")
  private String prjctId;

  @Column(name = "prjct_nm")
  private String prjctNm;

  @Column(name = "user_id")
  private String userId;
}
