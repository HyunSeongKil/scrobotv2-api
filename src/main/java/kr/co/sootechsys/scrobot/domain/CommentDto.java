package kr.co.sootechsys.scrobot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
  private Long commentId;

  private String commentSjNm;

  private String commentCn;

  private String registerId;

  private String registerNm;

  private java.util.Date registDt;

}
