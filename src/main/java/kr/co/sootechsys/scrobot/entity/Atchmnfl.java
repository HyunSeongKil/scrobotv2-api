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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "첨부파일 엔티티")
public class Atchmnfl {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "atchmnfl_id")
  @ApiModelProperty(value = "첨부파일 아이디")
  private Long atchmnflId;

  @Column(name = "atchmnfl_group_id")
  private Long atchmnflGroupId;

  /**
   * 첨부파일 저장 경로 값
   */
  @Column(name = "atchmnfl_storg_path_value")
  @ApiModelProperty(value = "첨부파일 저장 경로")
  private String atchmnflStorgPathValue;

  /**
   * 저장 파일 명
   */
  @Column(name = "storg_file_nm")
  @ApiModelProperty(value = "저장 파일 명")
  private String storgFileNm;

  /**
   * 원본 파일 명
   */
  @Column(name = "original_file_nm")
  @ApiModelProperty(value = "원본 파일 명")
  private String originalFileNm;

  /**
   * 첨부파일 크기 값
   */
  @Column(name = "atchmnfl_filesz_value")
  @ApiModelProperty(value = "첨부파일 크기")
  private Long atchmnflFileszValue;

  /**
   * 첨부파일 확장자
   */
  @Column(name = "atchmnfl_etsion")
  @ApiModelProperty(value = "첨부파일 확장자")
  private String atchmnflEtsion;

  /**
   * 인코딩구분코드
   */
  @Column(name = "encoding_dstnct_cd")
  @ApiModelProperty(value = "첨부파일 구분 코드")
  private String encodingDstnctCd;

  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;

}
