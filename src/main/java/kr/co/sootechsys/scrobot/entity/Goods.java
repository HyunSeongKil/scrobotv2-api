package kr.co.sootechsys.scrobot.entity;

import java.util.Date;

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
@Table(name = "goods")
@ApiModel(value = "상품")
public class Goods {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "goods_id")
  @ApiModelProperty(value = "pk")
  private Long goodsId;

  @Column(name = "goods_key_nm")
  @ApiModelProperty(value = "상품 키 값")
  private String goodsKeyNm;

  @Column(name = "goods_nm")
  @ApiModelProperty(value = "상품 명")
  private String goodsNm;

  @Column(name = "goods_cn", length = 1000)
  @ApiModelProperty(value = "상품 내용")
  private String goodsCn;

  @Column(name = "goods_price_value")
  @ApiModelProperty(value = "상품 가격 값")
  private Integer goodsPriceValue;

  @Column(name = "register_id")
  @ApiModelProperty(value = "등록자 아이디")
  private String registerId;

  @Column(name = "register_nm")
  @ApiModelProperty(value = "등록자 명")
  private String registerNm;

  @Column(name = "regist_dt")
  @ApiModelProperty(value = "등록 일시")
  private Date registDt;
}
