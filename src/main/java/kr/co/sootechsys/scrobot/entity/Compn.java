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
 * 콤포넌트
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compn")
public class Compn {

  /**
   * 콤포넌트 아이디
   */
  @Id
  @Column(name = "compn_id")
  private String compnId;

  /**
   * 콤포넌트 명
   */
  @Column(name = "compn_nm")
  private String compnNm;

  /**
   * 영문 약어 명
   */
  @Column(name = "eng_abrv_nm")
  private String engAbrvNm;

  /**
   * 한글 약어 명
   */
  @Column(name = "hngl_abrv_nm")
  private String hnglAbrvNm;


  /**
   * 콤포넌트 내용
   */
  @Column(name = "compn_cn", length = 4000)
  private String compnCn;

  /**
   * 콤포넌트 구분 코드
   */
  @Column(name = "compn_se_code")
  private String compnSeCode;


  /**
   * 화면 아이디
   */
  @Column(name = "scrin_id")
  private String scrinId;

  /**
   * 화면 인스턴스
   */
  @ManyToOne(targetEntity = Scrin.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "scrin_id", insertable = false, updatable = false)
  private Scrin scrin;
}
