package kr.co.sootechsys.scrobot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

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
@Table(name = "secsn")
@ApiModel(value = "탈퇴")
public class Secsn {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "secsn_id")
  private Long secsnId;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "user_nm")
  private String userNm;

  @Column(name = "telno")
  private String telno;

  @Column(name = "secsn_reason_cn", length = 1000)
  private String secsnReasonCn;


  @Column(name = "imprvm_cn", length = 1000)
  @ApiModelProperty(value = "개선 내용")
  private String imprvmCn;


  @Column(name = "join_dt")
  private Date joinDt;

  @Column(name = "secsn_dt")
  private Date secsnDt;
}
