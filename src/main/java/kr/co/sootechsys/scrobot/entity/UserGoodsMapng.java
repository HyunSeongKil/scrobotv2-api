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
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_goods_mapng")
public class UserGoodsMapng {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_goods_mapng_id")
  private Long userGoodsMapngId;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "goods_id")
  private Long goodsId;

  @Column(name = "regist_dt")
  private Date registDt;
}
