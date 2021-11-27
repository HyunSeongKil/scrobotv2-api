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
@Table(name = "prjct_guidance_mssage_mapng")
@ApiModel(value = "프로젝트-안내메시지 매핑")
public class PrjctGuidanceMssageMapng {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "prjct_guidance_mssage_mapng_id")
  @ApiModelProperty(value = "pk")
  private Long prjctGuidanceMssageMapngId;

  @Column(name = "prjct_id")
  @ApiModelProperty(value = "프로젝트 아이디")
  private String prjctId;

  @Column(name = "guidance_mssage_id")
  @ApiModelProperty(value = "안내 메시지 아이디")
  private Long guidanceMssageId;
}
