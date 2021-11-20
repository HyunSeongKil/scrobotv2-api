package kr.co.sootechsys.scrobot.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "atchmnfl_group")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "첨부파일 그룹 엔티티")
public class AtchmnflGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "atchmnfl_group_id")
  @ApiModelProperty(value = "첨부파일 그룹 아이디")
  private Long atchmnflGroupId;

  @Column(name = "prjct_id")
  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;
}
