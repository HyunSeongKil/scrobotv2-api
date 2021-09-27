package kr.co.sootechsys.scrobot.entity;

import java.util.Date;

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

/**
 * 프로젝트
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prjct2")
public class Prjct {
  /**
   * 프로젝트 아이디
   */
  @Id
  @Column(name = "prjct_id")
  private String prjctId;

  /**
   * 프로젝트 명
   */
  @Column(name = "prjct_nm")
  private String prjctNm;

  /**
   * 프로젝트 내용
   */
  @Column(name = "prjct_cn", length = 4000)
  private String prjctCn;

  /**
   * 사용자 아이디
   */
  @Column(name = "user_id")
  private String userId;

  /**
   * 생성 일시
   */
  @Column(name = "regist_dt")
  private Date registDt;

  /**
   * 수정 일시
   */
  @Column(name = "updt_dt")
  private Date updtDt;
}
