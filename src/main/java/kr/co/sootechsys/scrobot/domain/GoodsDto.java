package kr.co.sootechsys.scrobot.domain;

import java.util.Date;

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
@ApiModel(value = "상품")
public class GoodsDto {
  @ApiModelProperty(value = "pk")
  private Long goodsId;

  @ApiModelProperty(value = "상품 키 값")
  private String goodsKeyNm;

  @ApiModelProperty(value = "상품 명")
  private String goodsNm;

  @ApiModelProperty(value = "상품 내용")
  private String goodsCn;

  @ApiModelProperty(value = "상품 가격 값")
  private Integer goodsPriceValue;

  @ApiModelProperty(value = "등록자 아이디")
  private String registerId;

  @ApiModelProperty(value = "등록자 명")
  private String registerNm;

  @ApiModelProperty(value = "등록 일시")
  private Date registDt;
}
