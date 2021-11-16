package kr.co.sootechsys.scrobot.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
public class AtchmnflGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "atchmnfl_group_id")
  private Long atchmnflGroupId;

  @Column(name = "regist_dt")
  private Date registDt;
}
