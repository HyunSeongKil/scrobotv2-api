package kr.co.sootechsys.scrobot.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGoodsMapngDto {
  private Long userGoodsMapngId;

  private String userId;

  private Long goodsId;

  private Date registDt;
}
