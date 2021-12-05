package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bbs")
@ApiModel(value = "게시판")
public class Bbs {
  @Id
  @Column(name = "bbs_id")
  @ApiModelProperty(value = "게시판 아이디")
  private Long bbsId;

  @Column(name = "bbs_se_cd")
  @ApiModelProperty(value = "게시판 구분 코드")
  private String bbsSeCd;

  @Column(name = "bbs_sj_nm")
  @ApiModelProperty(value = "게시판 제목 명")
  private String bbsSjNm;

  @Column(name = "bbs_cn")
  @ApiModelProperty(value = "게시판 내용")
  private String bbsCn;

  @Column(name = "atchmnfl_group_id")
  @ApiModelProperty(value = "첨부파일 그룹 아이디")
  private Long atchmnflGroupId;

  @Column(name = "inqire_co")
  @ApiModelProperty(value = "조회 횟수")
  private Long inqireCo;

  @Column(name = "fixing_at")
  @ApiModelProperty(value = "고정 여부")
  private String fixingAt;

  @Column(name = "inqry_ty_cd")
  @ApiModelProperty(value = "(FAQ)문의 유형 코드")
  private String inqryTyCd;

  @Column(name = "register_id")
  private String registerId;

  @Column(name = "register_nm")
  private String registerNm;

  @Column(name = "regist_dt")
  private java.util.Date registDt;

}
