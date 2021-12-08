package kr.co.sootechsys.scrobot.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stplat")
@ApiModel(value = "약관")
public class Stplat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "stplat_id")
  private Long stplatId;

  @Column(name = "stplat_nm")
  private String stplatNm;

  @Column(name = "stplat_cn")
  private String stplatCn;

  @Column(name = "register_id")
  private String registerId;

  @Column(name = "register_nm")
  private String registerNm;

  @Column(name = "regist_dt")
  private Date registDt;
}
