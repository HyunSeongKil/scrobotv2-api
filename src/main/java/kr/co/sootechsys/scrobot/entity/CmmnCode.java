package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * 공통 코드
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cmmn_code")
public class CmmnCode {

  /**
   * 공통 코드 아이디
   */
  @Id
  @Column(name = "cmmn_code_id")
  private Long cmmnCodeId;

  /**
   * 공통 코드
   */
  @Column(name = "cmmn_code")
  private String cmmnCode;

  /**
   * 공통 코드 명
   */
  @Column(name = "cmmn_code_nm")
  private String cmmnCodeNm;


  /**
   * 부모 공통 코드
   */
  @Column(name = "prnts_cmmn_code")
  private String prntsCmmnCode;

  /**
   * 사용 여부
   */
  @Column(name = "use_at")
  private String useAt;

}
