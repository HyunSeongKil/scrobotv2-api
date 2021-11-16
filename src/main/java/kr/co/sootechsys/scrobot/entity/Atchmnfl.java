package kr.co.sootechsys.scrobot.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "atchmnfl")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Atchmnfl {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "atchmnfl_id")
  private Long atchmnflId;

  @Column(name = "atchmnfl_group_id")
  private Long atchmnflGroupId;

  /**
   * 첨부파일 저장 경로 값
   */
  @Column(name = "atchmnfl_storg_path_value")
  private String atchmnflStorgPathValue;

  /**
   * 저장 파일 명
   */
  @Column(name = "storg_file_nm")
  private String storgFileNm;

  /**
   * 원본 파일 명
   */
  @Column(name = "original_file_nm")
  private String originalFileNm;

  /**
   * 첨부파일 크기 값
   */
  @Column(name = "atchmnfl_filesz_value")
  private Long atchmnflFileszValue;

  /**
   * 첨부파일 확장자
   */
  @Column(name = "atchmnfl_etsion")
  private String atchmnflEtsion;

  /**
   * 인코딩구분코드
   */
  @Column(name = "encoding_dstnct_cd")
  private String encodingDstnctCd;

  @Column(name = "regist_dt")
  private Date registDt;

}
